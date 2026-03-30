package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Services.ProductServiceInterface;
import com.E_CommerceBackendSystem.ecom.repository.CategoryRepository;
import com.E_CommerceBackendSystem.ecom.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ProductServices implements ProductServiceInterface {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public boolean addProducts(Product product) {
		Long categoryId = product.getCategory().getId();
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
		product.setCategory(category);
		
		if(productRepository.save(product)!=null) return true;
		return false;
	}
	
	@Override
	public boolean addmultipleproducts(List<Product> products) {
		if(productRepository.saveAll(products)!=null) return true;
		return false;
	}

	@Override
	public boolean updateProducts(long p_id, Product newProduct) {
		Product product= findByProductId(p_id);
		
		if(product!=null) {
//			 Long categoryId = newProduct.getCategory().getCat_id();
//			 
//			 if(categoryId!=null) {
//			 	Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
//			 	product.setCategory(category);
//			 }
			
			  // Category update
	        if (newProduct.getCategory() != null ) {
	            Long categoryId = newProduct.getCategory().getId();

	            if(categoryId!=null) {
				 	Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
				 	product.setCategory(category);
				 }
	        }

			    
	        // Update fields safely
	        if (newProduct.getP_description() != null && !newProduct.getP_description().isEmpty()) {
	            product.setP_description(newProduct.getP_description());
	        }

	        if (newProduct.getP_name() != null && !newProduct.getP_name().isEmpty()) {
	            product.setP_name(newProduct.getP_name());
	        }

	        if (newProduct.getStockQuantity() != 0) {
	            product.setStockQuantity(newProduct.getStockQuantity());
	        }

	        if (newProduct.getPrice() != 0.0) {
	            product.setPrice(newProduct.getPrice());
	        }

	        productRepository.save(product);
	        return true;
		}
		return false;
	}
	

	@Override
	public List<Product> getAllProducts() {
		String key="products:all";
		List<Product> products = redisService.get(key, new TypeReference<List<Product>>() {});
		
		if(products != null) return products;
		products=productRepository.findAll();
		redisService.set(key, products, 500);
		
		return products;
	}
	
	@Override
	public Product findByProductId(Long p_id) {
		
		String Key="product:"+p_id;
		
		Product product = redisService.get(Key, new TypeReference<Product>() {});

		
		if(product!=null) {
			return product;
		}

		product= productRepository.findById(p_id).orElse(null);
		redisService.set(Key, product, 500);
		return product;
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
		String key="category:all";
		
		List<Category> categories=redisService.get(key, new TypeReference<List<Category>>() {});
		
		if(categories!=null) return categories;
		categories = categoryRepository.findAll();
		redisService.set(key, categories, 900);
		return categoryRepository.findAll();
	}

	@Override
	public Category findByCategoryId(Long c_id) {
		
		String key="category:"+c_id;
		
		Category category = redisService.get(key, new TypeReference<Category>() {});
		List<Category> categories=redisService.get("category:all", new TypeReference<List<Category>>() {});

		if(category!=null) {
			return category;
		}
		
		category = categories.stream()
        .filter(c -> c.getId()==c_id)
        .findFirst()
        .orElse(null);
		
		if (category!= null) {
		    return category;
		}
		else {
			category= categoryRepository.findById(c_id).orElse(null);
			redisService.set(key, categories, 500);
			return category;
		}
	}

	@Override
	public void deleteProductById(long pid) {
		productRepository.deleteById(pid);
	}

	@Override
	public void deleteAllProduct() {
		productRepository.deleteAll();
	}

	@Override
	public void deleteCategoryById(long cid) {
		categoryRepository.deleteById(cid);
	}
	
	@Override
	public void deleteAllCategory() {
		categoryRepository.deleteAll();
	}

//	Need controller method
	@Override
	public List<Product> getAllProductsAsc() {
		return productRepository.findAllByOrderByPriceAsc();
	}

	@Override
	public List<Product> getAllProductsDsc() {
		return productRepository.findAllByOrderByPriceDesc();
	}

	@Override
	public List<Product> getAllProductsByCatId(long categoryId) {
		return productRepository.findByCategory_Id(categoryId);
	}

	@Override
	public List<Product> search(String keyword) {
		return productRepository.searchProducts(keyword);
	}

	@Override
	public List<Product> inRangeOf(float min, float max) {
		return productRepository.findByPriceBetween(min, max);
	}

}
