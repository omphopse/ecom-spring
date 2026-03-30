package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;
import com.E_CommerceBackendSystem.ecom.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Services.CartServiceInterface;

@Slf4j
@Service
public class CartServices implements CartServiceInterface{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public boolean addItemstoCart(long u_id, long p_id, int quantity) {
		try {
			String cartKey="cart:"+u_id;
			Product product = productRepository.findById(p_id).get();
			
			if(product.getStockQuantity()>=quantity) {
				redisTemplate.opsForHash().increment(cartKey, String.valueOf(p_id), quantity);
				redisTemplate.expire(cartKey, Duration.ofHours(24));
				return true;
			}
			else {
				throw new RuntimeException(product.getP_name()+" is low stock "+product.getStockQuantity()+" items left");
			}
			
		}
		catch(RuntimeException e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public List<UserCartDto> getCart(long userId) {
		
		Map<Object,Object> cart = redisTemplate.opsForHash().entries("cart:"+userId);
		
		List<UserCartDto> myCart= new ArrayList<>();
		
		for(Map.Entry<Object,Object> entry : cart.entrySet()){

		    Long productId = Long.parseLong(entry.getKey().toString());
		    Integer quantity = Integer.parseInt(entry.getValue().toString());

		    Product product = productRepository.findById(productId).get();
	
		    myCart.add(transFromToDto(product, quantity));
		}
		
		return myCart;
	}

	@Override
	public boolean removeItem(long userId, long productId) {
		redisTemplate.opsForHash().delete("cart:"+userId,String.valueOf(productId));
		return true;
	}

	@Override
	public boolean clearCart(long userId) {
		redisTemplate.delete("cart:"+userId);
		return true;
	}

	public UserCartDto transFromToDto(Product product, int quantity) {
		return new UserCartDto(product.getCategory().getCat_Name(),product.getP_id(),product.getP_name(),product.getPrice(),quantity,quantity*product.getPrice());
	}

	@Override
	public int getCartItemCount(long userId) {
	    return redisTemplate.opsForHash().size("cart:" + userId).intValue();
	}

	@Override
	public double getCartTotal(long userId) {

	    Map<Object, Object> cart = redisTemplate.opsForHash().entries("cart:" + userId);

	    double total = 0;

	    List<Long> productIds = cart.keySet()
	            .stream()
	            .map(k -> Long.parseLong(k.toString()))
	            .toList();

	    List<Product> products = productRepository.findAllById(productIds);

	    Map<Long, Product> productMap = products.stream()
	            .collect(Collectors.toMap(Product::getP_id, p -> p));

	    for (Map.Entry<Object, Object> entry : cart.entrySet()) {

	        Long productId = Long.parseLong(entry.getKey().toString());
	        Integer quantity = Integer.parseInt(entry.getValue().toString());

	        Product product = productMap.get(productId);

	        if (product != null) {
	            total += product.getPrice() * quantity;
	        }
	    }

	    return total;
	}
}
