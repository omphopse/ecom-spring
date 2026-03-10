package com.E_CommerceBackendSystem.ecom.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;
import com.E_CommerceBackendSystem.ecom.Services.adminservices.AdminServiceInterface;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminServiceInterface adminServiceInterface;
	
	@GetMapping
	public String test() {
		return "admin";
	}
	
	@PostMapping("/addcategory")
	public ResponseEntity<Category> addCategory(@RequestBody Category category){
		if(adminServiceInterface.addCategory(category)) return new ResponseEntity<>(category,HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getallcategory")
	public ResponseEntity<List<Category>> getAllCategory(){
		return new ResponseEntity<>(adminServiceInterface.getAllCategory(),HttpStatus.OK);
	}
	
	@PutMapping("/updatecategory/{catId}")
	public ResponseEntity<Category> updateCategory(@PathVariable long catId, @RequestBody Category category){
		if(adminServiceInterface.updateCategory(catId, category)) return new ResponseEntity<>(category,HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/addproducts")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		if(adminServiceInterface.addProducts(product)) return new ResponseEntity<>(product,HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getallproducts")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<>(adminServiceInterface.getAllProducts(),HttpStatus.OK);
	}
	
	@PutMapping("/updateproducts/{p_Id}")
	public ResponseEntity<Product> updateCategory(@PathVariable long p_Id, @RequestBody Product product){
		if(adminServiceInterface.updateProducts(p_Id,product)) return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getallusers")
	public ResponseEntity<List<Users>> getAllUsers(){
		return new ResponseEntity<>(adminServiceInterface.getAllUser(),HttpStatus.OK);
	}
	
	@GetMapping("/getallorders")
	public ResponseEntity<List<Order>> getAllOrders(){
		return new ResponseEntity<>(adminServiceInterface.getAllOrder(),HttpStatus.OK);
	}

	
	@GetMapping("/getallpayments")
	public ResponseEntity<List<Payments>> getAllPayments(){
		return new ResponseEntity<>(adminServiceInterface.getAllPayments(),HttpStatus.OK);
	}

}
