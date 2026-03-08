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
import org.springframework.web.bind.annotation.RestController;

import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.userentity.CartItem;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.adminservices.AdminServiceInterface;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private AdminServiceInterface adminServiceInterface;
	
	@Autowired
	private UserServiceInterface serviceInterface;
	
	
	@GetMapping
	public String test() {
		return "user";
	}
	
	
	@GetMapping("/veiwallproducts")
	public  ResponseEntity<List<Product>> veiwAllProducts(){
		return new ResponseEntity<>(adminServiceInterface.getAllProducts(),HttpStatus.OK);
	}
	
	@PostMapping("/additemstocart")
	public  ResponseEntity<?> addItemstoCart(@RequestBody CartItem cartitem){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		
		if(serviceInterface.addItemstoCart(u_id, cartitem.getProduct_id(), cartitem.getQuantity())) return new ResponseEntity<>("product added to cart",HttpStatus.CREATED);
		return new ResponseEntity<>("failed add product to cart",HttpStatus.CREATED);
	}
	
	@GetMapping("/getcart")
	public ResponseEntity<Map<Object,Object>> getcart(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		return new ResponseEntity<>(serviceInterface.getCart(u_id),HttpStatus.OK);
	}
	
	
	@PutMapping("/removeitemfromcart/{itemid}")
	public ResponseEntity<?> removeItemFromCart(@PathVariable long itemid){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		if(serviceInterface.removeItem(u_id, itemid)) return new ResponseEntity<>("item removed",HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("failed to remove item",HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/clearcart")
	public ResponseEntity<?> clearCart(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long u_id = serviceInterface.findByUserName(authentication.getName()).getId();
		if(serviceInterface.clearCart(u_id)) return new ResponseEntity<>("cart cleared",HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("failed to clear cart",HttpStatus.NO_CONTENT);
	}
}
