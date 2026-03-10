package com.E_CommerceBackendSystem.ecom.Services.adminservices;
import java.util.List;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;

public interface AdminServiceInterface {
	boolean addProducts(Product product);
	boolean updateProducts(long p_id,Product newProduct);
	List<Product> getAllProducts();
	Product findByProductId(Long p_id);
	
	boolean addCategory(Category category);
	boolean updateCategory(long cat_id,Category newCategory);
	List<Category> getAllCategory();
	Category findByCategoryId(Long c_id);
	
	void deleteProductById(long pid);
	void deleteAllProduct();
	void deleteCategoryById(long cid);
	void deleteAllCategory();
	
	List<Users> getAllUser();
	List<Order> getAllOrder();
	List<Payments> getAllPayments();
}
