package com.E_CommerceBackendSystem.ecom.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationObservationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_CommerceBackendSystem.ecom.Enitity.CartItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.AdminServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.CartServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.OrderServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.PaymentServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ProductServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;

@RestController
@RequestMapping("/user")
public class UserController {
	
//	@Autowired
//	private AdminServiceInterface adminServiceInterface;
	
	@Autowired
	private UserServiceInterface serviceInterface;
	
	@Autowired
	private  ProductServiceInterface productServiceInterface;
	
	@Autowired
	private CartServiceInterface cartServiceInterface;
	
	@Autowired
	private PaymentServiceInterface paymentServiceInterface;
	
	@Autowired
	private OrderServiceInterface orderServiceInterface;
	
	@GetMapping
	public String test() {
		return "user";
	}
	
	
	@GetMapping("/veiwallproducts")
	public  ResponseEntity<List<Product>> veiwAllProducts(){
		return new ResponseEntity<>(productServiceInterface.getAllProducts(),HttpStatus.OK);
	}
	
	@PostMapping("/additemstocart")
	public  ResponseEntity<?> addItemstoCart(@RequestBody CartItem cartitem){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		
		if(cartServiceInterface.addItemstoCart(u_id, cartitem.getProduct_id(), cartitem.getQuantity())) return new ResponseEntity<>("product added to cart",HttpStatus.CREATED);
		return new ResponseEntity<>("failed add product to cart",HttpStatus.CREATED);
	}
	
	@GetMapping("/getcart")
	public ResponseEntity<List<UserCartDto>> getcart(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		return new ResponseEntity<>(cartServiceInterface.getCart(u_id),HttpStatus.OK);
	}
	
	
	@PutMapping("/removeitemfromcart/{itemid}")
	public ResponseEntity<?> removeItemFromCart(@PathVariable long itemid){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		if(cartServiceInterface.removeItem(u_id, itemid)) return new ResponseEntity<>("item removed",HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("failed to remove item",HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/clearcart")
	public ResponseEntity<?> clearCart(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		if(cartServiceInterface.clearCart(u_id)) return new ResponseEntity<>("cart cleared",HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("failed to clear cart",HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<?> checkout() {

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Users user = serviceInterface.findByUserName(authentication.getName());

	    Order order = orderServiceInterface.checkOut(user);

	    if (order != null) {
	        return ResponseEntity.ok(order); // ✅ send order with ID
	    }

	    return ResponseEntity.badRequest().body("failed to place order");
	}
	
	@PostMapping("/payments")
	public ResponseEntity<?> payments(@RequestParam Long orderId) {

	    Order order = orderServiceInterface.findByOrderID(orderId);

	    Payments payment = new Payments();
	    
	    if(order!=null) {
	    	boolean status = paymentServiceInterface.savepayment(payment, order);
	    	
	    	if (status) {
		        return ResponseEntity.ok("Payment successful");
		    }
	    }
	    else {
	    	return ResponseEntity.badRequest().body("Order Not found");
	    }
	    return ResponseEntity.badRequest().body("Payment failed");
	}
	
	@GetMapping("/myorders")
	public ResponseEntity<List<Order>> myOrders(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
	    return new ResponseEntity<>(orderServiceInterface.findMyOrdes(u_id),HttpStatus.OK);
	}
}
