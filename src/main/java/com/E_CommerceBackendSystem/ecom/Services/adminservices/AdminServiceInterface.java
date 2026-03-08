package com.E_CommerceBackendSystem.ecom.Services.adminservices;
import java.util.List;
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
}
