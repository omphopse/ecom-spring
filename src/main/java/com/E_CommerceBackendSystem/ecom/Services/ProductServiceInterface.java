package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;

import com.E_CommerceBackendSystem.ecom.Enitity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;

public interface ProductServiceInterface {
	boolean addProducts(Product product);
	boolean updateProducts(long p_id,Product newProduct);
	List<Product> getAllProducts();
	Product findByProductId(Long p_id);
	boolean addmultipleproducts(List<Product> products);
	
	void deleteProductById(long pid);
	void deleteAllProduct();
	
	boolean addCategory(Category category);
	boolean updateCategory(long cat_id,Category newCategory);
	List<Category> getAllCategory();
	Category findByCategoryId(Long c_id);
	
	void deleteCategoryById(long cid);
	void deleteAllCategory();
	
	//Methods
	List<Product> getAllProductsAsc();
	List<Product> getAllProductsDsc();
	
	List<Product> getAllProductsByCatId(long categoryId);
	List<Product> search(String keyword);
	List<Product> inRangeOf(float min, float max);
	
}
