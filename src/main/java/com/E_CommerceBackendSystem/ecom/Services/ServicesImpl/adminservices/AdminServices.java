package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.adminservices;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;
import com.E_CommerceBackendSystem.ecom.Services.adminservices.AdminServiceInterface;
import com.E_CommerceBackendSystem.ecom.repository.OrderRepository;
import com.E_CommerceBackendSystem.ecom.repository.PaymentRepository;
import com.E_CommerceBackendSystem.ecom.repository.UserRepository;
import com.E_CommerceBackendSystem.ecom.repository.adminrepository.CategoryRepository;
import com.E_CommerceBackendSystem.ecom.repository.adminrepository.ProductRepository;

@Service
public class AdminServices implements AdminServiceInterface{
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired 
	private PaymentRepository paymentRepository;
	
	@Override
	public boolean addProducts(Product product) {
		Long categoryId = product.getCategory().getCat_id();
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
		product.setCategory(category);
		
		if(productRepository.save(product)!=null) return true;
		return false;
	}

	@Override
	public boolean updateProducts(long p_id, Product newProduct) {
		Product product= findByProductId(p_id);
		
		if(product!=null) {
			 Long categoryId = newProduct.getCategory().getCat_id();
			 
			 if(categoryId!=null) {
			 	Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
			 	product.setCategory(category);
			 }
			    
//			product.setCategory(newProduct.getCategory()!=null || !newProduct.getCategory().equals("") ?newProduct.getCategory():product.getCategory());
			product.setP_description(newProduct.getP_description()!=null || !newProduct.getP_description().equals("")?newProduct.getP_description():product.getP_description());
			product.setP_name(newProduct.getP_name()!=null || !newProduct.getP_name().equals("")?newProduct.getP_name():product.getP_name());
			product.setStockQuantity((newProduct.getStockQuantity()!=0)?newProduct.getStockQuantity():product.getStockQuantity());
			product.setPrice(newProduct.getPrice()!=0.0 ?newProduct.getPrice():product.getPrice());
			
			if(productRepository.save(product)!=null) return true;
		}
		return false;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public boolean addCategory(Category category) {
		if(categoryRepository.save(category)!=null) return true;
		return false;
	}

	@Override
	public boolean updateCategory(long cat_id, Category newCategory) {
		Category category = findByCategoryId(cat_id);
		if(category!=null) {
			category.setCat_Name(newCategory.getCat_Name() != null || newCategory.getCat_Name().equals("") ? newCategory.getCat_Name() : category.getCat_Name());
			category.setCat_description(newCategory.getCat_description() != null || newCategory.getCat_description().equals("") ? newCategory.getCat_description() : category.getCat_description());
			if(categoryRepository.save(category)!=null) return true;
		}
		return false;
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Product findByProductId(Long p_id) {
		return productRepository.findById(p_id).orElse(null);
	}

	@Override
	public Category findByCategoryId(Long c_id) {
		return categoryRepository.findById(c_id).orElse(null);
	}

	@Override
	public List<Users> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public List<Order> getAllOrder() {
		return orderRepository.findAll();
	}

	@Override
	public List<Payments> getAllPayments() {
		return paymentRepository.findAll();
	}

	@Override
	public void deleteProductById(long pid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllProduct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCategoryById(long cid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllCategory() {
		// TODO Auto-generated method stub
		
	}
	
}
