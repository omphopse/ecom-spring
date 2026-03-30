package com.E_CommerceBackendSystem.ecom.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.E_CommerceBackendSystem.ecom.Enitity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.OrderServices;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.RedisService;
import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.repository.OrderItemRepository;
import com.E_CommerceBackendSystem.ecom.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;

@ExtendWith(MockitoExtension.class)
public class OrderServicesTest {

	@InjectMocks
	private OrderServices orderServices;
	
	@Mock
	private OrderRepository orderRepository; 
	
	@Mock
	private RedisService redisService;
	
	@Mock
	private CartServiceInterface cartServiceInterface;
	
	@Mock
	private ProductServiceInterface productServiceInterface;
	
	@Mock
	private OrderItemRepository itemRepository;
	@Test
	public void testOrderCreation() {
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    // Mock repository behavior
        when(orderRepository.save(order)).thenReturn(order);

        // Call actual method
        Order result = orderServices.createOrder(order);

        // Assertions
        assertNotNull(result);
        assertEquals(OrderStatus.PENDING, result.getOrderStatus());
        assertEquals(232, result.getTotal_price());

        // Verify interaction
        verify(orderRepository, times(1)).save(order);
	}
	
	
	@Test
	public void testgetallredis() {
		 // Mock repository behavior
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    List<Order> l= List.of(order);
	    
	    when(redisService.get(eq("test"), any(TypeReference.class)))
        .thenReturn(l);
	    
        List<Order> call =  redisService.get("test", new TypeReference<List<Order>>() {});
        
        assertNotNull(call);
        
        verify(redisService, times(1))
        .get(eq("test"), any(TypeReference.class));
	}
	
	@Test
	public void testgetallRepo() {
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    List<Order> l= List.of(order);
	    

	    when(orderRepository.findAll()).thenReturn(l);
	    
	    List<Order> call= orderServices.getAllOrder();
	    
	    assertNotNull(call);
	    
	    verify(orderRepository, times(1)).findAll();
	}
	
	@Test
	public void changeOrderStatus() {
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    Optional<Order> optionalOrder = Optional.of(order);
	    
	    when(orderRepository.findById(any())).thenReturn(optionalOrder);
	    
	    boolean result = orderServices.changeOrderStatus(1, OrderStatus.CANCEL);
	    
	    assertTrue(result);
	    
	    verify(orderRepository, times(1)).findById(any());
	}
	
	@Test
	public void testfindMyOrdesRepo() {
		 // Mock repository behavior
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    List<Order> l= List.of(order);
	    
	    when(orderRepository.findAllByUserId(any())).thenReturn(l);
	    
        List<Order> call =  orderServices.findMyOrdes(1);
        
        assertNotNull(call);
        
        l.forEach(ls -> 
        assertEquals(OrderStatus.PENDING, order.getOrderStatus()));
        
        verify(orderRepository, times(1)).findAllByUserId(any());
	}
	
	@Test
	public void testFindOrderById() {
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    Optional<Order> optionalOrder = Optional.of(order);
	    when(orderRepository.findById(any())).thenReturn(optionalOrder);
	    
	    Order result =  orderServices.findByOrderID(1);
	    
	    assertNotNull(result);
	    
	    verify(orderRepository,times(1)).findById(any());
	}
	
	@Test
	public void testCancelOrder() {
		
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    Optional<Order> optionalOrder = Optional.of(order);
	    
	    when(orderRepository.findById(any())).thenReturn(optionalOrder);
	    
	    boolean result = orderServices.cancelOrder(1);
	    
	    assertTrue(result);
	    
	    verify(orderRepository, times(1)).findById(any());
	}
	
	
	@Test
	public void testGetOrderByStatus() {
		Order order = new Order();
		Users user = new Users();
		
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setId(1);
	    order.setTotal_price(232);
	    
	    List<Order> l= List.of(order);
	    
	    when(orderRepository.findAllByOrderStatus(any())).thenReturn(l);
	    
	    List<Order> call = orderServices.getOrdersByStatus("PENDING");
	    
	    assertNotNull(call);
	    
	    call.forEach(c -> assertEquals(OrderStatus.PENDING, order.getOrderStatus()));
	    
	    verify(orderRepository, times(1)).findAllByOrderStatus(any());
	}
//	
//	@Test
//	public void testCheckout() {
//		Users user = new Users();
//		user.setId(1);
//		
//		Category category = new Category();
//		category.setId(1);
//		category.setCat_Name("demo");
//		
//		Product product = new Product();
//		product.setCategory(category);
//		product.setP_id(1);
//		product.setP_name("demo");
//		product.setPrice(1243);
//		product.setStockQuantity(10);
//		
//		UserCartDto cart1 = new UserCartDto(product.getCategory().getCat_Name(), product.getP_id(), product.getP_name(), product.getPrice(), 1, 1243);
//		
//		List<UserCartDto> cart =  List.of(cart1);
//		
//		when(cartServiceInterface.getCart(any())).thenReturn(cart);
//		
//		when(productServiceInterface.findByProductId(any()))
//        .thenReturn(product);
//		
//		
//		Order order = orderServices.checkOut(user);
//		
//		assertNotNull(order);
//		
//		verify(orderRepository, times(2)).save(any());
//		verify(cartServiceInterface, atLeastOnce()).getCart(any());
//		verify(productServiceInterface, atLeastOnce()).findByProductId(any());
//		verify(itemRepository,atLeastOnce()).save(any());
//	}
	
	@Test
	public void testCheckout() {

	    Users user = new Users();
	    user.setId(1);

	    Category category = new Category();
	    category.setId(1);
	    category.setCat_Name("demo");

	    Product product = new Product();
	    product.setCategory(category);
	    product.setP_id(1L);
	    product.setP_name("demo");
	    product.setPrice(1243);
	    product.setStockQuantity(10);

	    UserCartDto cart1 = new UserCartDto(
	            category.getCat_Name(),
	            product.getP_id(),
	            product.getP_name(),
	            product.getPrice(),
	            1,
	            1243
	    );

	    List<UserCartDto> cart = List.of(cart1);

	    // ✅ Correct mocks
	    when(cartServiceInterface.getCart(anyLong())).thenReturn(cart);
	    when(productServiceInterface.findByProductId(anyLong()))
	            .thenReturn(product);

	    when(orderRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
	    
	    // Call method
	    Order order = orderServices.checkOut(user);

	    // Assertions
	    assertNotNull(order);
	    assertEquals(1243, order.getTotal_price());

	    // Verifications
	    verify(orderRepository, times(2)).save(any());
	    verify(cartServiceInterface, atLeastOnce()).getCart(anyLong());
	    verify(productServiceInterface, atLeastOnce()).findByProductId(anyLong());
	    verify(itemRepository, atLeastOnce()).save(any());
	}
}
