package com.E_CommerceBackendSystem.ecom.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_CommerceBackendSystem.ecom.Enitity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.OrderServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.PaymentServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ProductServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private ProductServiceInterface productServiceInterface;
	
	@Autowired
	private OrderServiceInterface orderServiceInterface;
	
	@Autowired
	private PaymentServiceInterface paymentServiceInterface;
	
	@Autowired
	private UserServiceInterface userServiceInterface;
	
	@GetMapping
	public String test() {
		return "admin";
	}
	
	/* Category section starts */
	@PostMapping("/addcategory")
	public ResponseEntity<Category> addCategory(@RequestBody Category category){
		if(productServiceInterface.addCategory(category)) return new ResponseEntity<>(category,HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getallcategory")
	public ResponseEntity<List<Category>> getAllCategory(){
		return new ResponseEntity<>(productServiceInterface.getAllCategory(),HttpStatus.OK);
	}
	
	@PutMapping("/updatecategory/{catId}")
	public ResponseEntity<Category> updateCategory(@PathVariable long catId, @RequestBody Category category){
		if(productServiceInterface.updateCategory(catId, category)) return new ResponseEntity<>(category,HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/deleteallcategory")
	public ResponseEntity<?> deleteAllCategory(){
		productServiceInterface.deleteAllCategory();
		return new ResponseEntity<>("all category deleted",HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/deletecategorybyid/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable long id){
		productServiceInterface.deleteCategoryById(id);
		return new ResponseEntity<>("deleted from db",HttpStatus.NO_CONTENT);
	}
	/* Category section ends */
	
	
	/*Product Sections starts*/
	@PostMapping("/addproducts")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		if(productServiceInterface.addProducts(product)) return new ResponseEntity<>(product,HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/addallproducts")
	public ResponseEntity<?> addProduct(@RequestBody List<Product> product){
		if(productServiceInterface.addmultipleproducts(product)) return new ResponseEntity<>("products added successfully",HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getallproducts")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<>(productServiceInterface.getAllProducts(),HttpStatus.OK);
	}
	
	@PutMapping("/updateproducts/{p_Id}")
	public ResponseEntity<Product> updateCategory(@PathVariable long p_Id, @RequestBody Product product){
		if(productServiceInterface.updateProducts(p_Id,product)) return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/deleteallproduct")
	public ResponseEntity<?> deleteAllProduct(){
		productServiceInterface.deleteAllProduct();
		return new ResponseEntity<>("all products deleted",HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/deleteproductbyid/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable long id){
		productServiceInterface.deleteProductById(id);
		return new ResponseEntity<>(" eleted from db",HttpStatus.NO_CONTENT);
	}
	/*Product Sections ends*/
	
	/*get all users from db*/
	@GetMapping("/getallusers")
	public ResponseEntity<List<Users>> getAllUsers(){
		return new ResponseEntity<>(userServiceInterface.findAllUsers(),HttpStatus.OK);
	}
	
	/*get all orders from db*/
	@GetMapping("/getallorders")
	public ResponseEntity<List<Order>> getAllOrders(){
		return new ResponseEntity<>(orderServiceInterface.getAllOrder(),HttpStatus.OK);
	}

	/*get all payments from db*/
	@GetMapping("/getallpayments")
	public ResponseEntity<List<Payments>> getAllPayments(){
		return new ResponseEntity<>(paymentServiceInterface.getAllPayments(),HttpStatus.OK);
	}
	
	/*change oder status*/
	@PutMapping("/changeorderstatus/{oid}")
	public ResponseEntity<Boolean> changeOrderStatus(@PathVariable long oid, @RequestParam OrderStatus status){
		if(orderServiceInterface.changeOrderStatus(oid, status))
		return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
