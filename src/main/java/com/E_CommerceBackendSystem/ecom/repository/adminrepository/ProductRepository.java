package com.E_CommerceBackendSystem.ecom.repository.adminrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByOrderByPriceDesc();
	List<Product> findAllByOrderByPriceAsc();
}
