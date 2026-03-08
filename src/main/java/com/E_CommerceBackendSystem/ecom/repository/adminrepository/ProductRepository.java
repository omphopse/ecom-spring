package com.E_CommerceBackendSystem.ecom.repository.adminrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
