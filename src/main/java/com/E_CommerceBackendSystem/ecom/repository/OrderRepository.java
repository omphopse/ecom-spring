package com.E_CommerceBackendSystem.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByUserId(Long userId);
	List<Order> findAllByOrderStatus(String status);
}
