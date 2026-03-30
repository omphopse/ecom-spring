package com.E_CommerceBackendSystem.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.E_CommerceBackendSystem.ecom.Enitity.Product;

import io.lettuce.core.dynamic.annotation.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByPriceAsc();

    List<Product> findByCategory_Id(long categoryId);

    List<Product> findByPriceBetween(float minPrice, float maxPrice);

    @Query("SELECT p FROM Product p WHERE LOWER(p.p_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);
}