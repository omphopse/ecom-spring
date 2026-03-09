package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserServices;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.adminservices.AdminServiceInterface;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.enums.PaymentMethods;
import com.E_CommerceBackendSystem.ecom.enums.PaymentStatus;
import com.E_CommerceBackendSystem.ecom.repository.OrderItemRepository;
import com.E_CommerceBackendSystem.ecom.repository.OrderRepository;
import com.E_CommerceBackendSystem.ecom.repository.PaymentRepository;
import com.E_CommerceBackendSystem.ecom.repository.UserRepository;
import com.E_CommerceBackendSystem.ecom.repository.adminrepository.ProductRepository;

@Service
public class UserServices implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository itemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	@Override
	public boolean createNewUser(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(userRepository.save(user) != null) return true;
		return false;
	}
	
	@Override
	public List<Users> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public Users findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean addItemstoCart(long u_id, long p_id, int quantity) {
	    String cartKey = "cart:" + u_id;
	    redisTemplate.opsForHash().increment(cartKey, String.valueOf(p_id), quantity);
	    redisTemplate.expire(cartKey, Duration.ofHours(24));
	    return true;
	}
	
	@Override
	public Map<Object,Object> getCart(long userId){
	    return redisTemplate.opsForHash().entries("cart:"+userId);
	}
	
	@Override
	public boolean removeItem(long userId, long productId) {
		redisTemplate.opsForHash().delete("cart:"+userId,String.valueOf(productId));
		return true;
	} 
	
	@Override
	public boolean clearCart(long userId){
	    redisTemplate.delete("cart:"+userId);
	    return true;
	}
	
	@Override
	public boolean checkOut(Payments payment, Users user) {
		Map<Object,Object> cart = getCart(user.getId());
		double total=0;
		
		Order order = new Order();
		order.setUser(user);
		order.setOrderStatus(OrderStatus.PENDING);
		createOrder(order);
		
		for(Map.Entry<Object,Object> entry : cart.entrySet()){

		    Long productId = Long.parseLong(entry.getKey().toString());
		    Integer quantity = Integer.parseInt(entry.getValue().toString());

		    Product product = productRepository.findById(productId).get();

		    OrderItem item = new OrderItem();
		    item.setOrder_id(order);
		    item.setProduct_id(product);
		    item.setQuantity(quantity);
		    item.setPrice(product.getPrice());
		    orderItemRepository.save(item);
		    
		    total += product.getPrice() * quantity;
		}
		
		order.setTotal_price(total);
		orderRepository.save(order);
	
		payment.setOrder(order);
		payment.setPaymentStatus(PaymentStatus.SUCCESS);
		payment.setTransaction_id("TXID000"+order.getO_id());
		
		if(paymentRepository.save(payment) !=null) return true;
		return false;
	}

	@Override
	public boolean createOrder(Order order) {
		if(orderRepository.save(order)!=null) return true;
		return false;
	}

	
	
	
}
