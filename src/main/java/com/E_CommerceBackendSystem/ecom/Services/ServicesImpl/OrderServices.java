package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.CartServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.OrderServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ProductServiceInterface;
import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.repository.OrderItemRepository;
import com.E_CommerceBackendSystem.ecom.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class OrderServices implements OrderServiceInterface{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CartServiceInterface cartServiceInterface;
	
	@Autowired
	private ProductServiceInterface productServiceInterface;
	
	@Autowired
	private OrderItemRepository itemRepository;
	
	@Override
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrder() {

	    String key = "orders:all";

	    List<Order> orders = redisService.get(key, new TypeReference<List<Order>>() {});

	    if (orders != null) return orders;
	    orders = orderRepository.findAll();
	    redisService.set(key, orders, 300);
	    
	    return orders;
	}
	
	@Override
	public boolean changeOrderStatus(long oid, OrderStatus status) {

	    Optional<Order> optionalOrder = orderRepository.findById(oid);

	    if (optionalOrder.isPresent()) {
	        Order order = optionalOrder.get();

	        if (status != null) {
	            order.setOrderStatus(status);
	        }

	        orderRepository.save(order);
	        return true;
	    }

	    return false;
	}

	@Override
	public List<Order> findMyOrdes(long id) {
		String key = "myorders:"+id;

	    List<Order> orders = redisService.get(key, new TypeReference<List<Order>>() {});

	    if (orders != null) return orders;
	    orders = orderRepository.findAllByUserId(id);
	    redisService.set(key, orders, 300);
		return orders;
	}
	
	@Override
	public Order checkOut(Users user) {

	    List<UserCartDto> carts = cartServiceInterface.getCart(user.getId());
	    double total = 0;

	    Order order = new Order();
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order=createOrder(order);

	    for (UserCartDto cart : carts) {

	        Long productId = cart.productId();
	        Integer quantity = cart.qunatity();

	        Product product = productServiceInterface.findByProductId(productId);

	        if (product.getStockQuantity() >= quantity) {

	            OrderItem item = new OrderItem();
	            item.setOrder(order);
	            item.setProduct(product);
	            item.setQuantity(quantity);
	            item.setPrice(product.getPrice());

	            itemRepository.save(item);
	        } else {
	            throw new RuntimeException("Only " + product.getStockQuantity() + " left");
	        }

	        total += product.getPrice() * quantity;
	    }

	    order.setTotal_price(total);

	    return createOrder(order);
	}

	@Override
	public Order findByOrderID(long id) {
		return orderRepository.findById(id).orElse(null);
	}

	@Override
	public List<Order> getOrdersByStatus(String status) {
		return orderRepository.findAllByOrderStatus(status);
	}

	@Override
	public boolean cancelOrder(long orderId) {
		return changeOrderStatus(orderId, OrderStatus.CANCEL);
	}
}
