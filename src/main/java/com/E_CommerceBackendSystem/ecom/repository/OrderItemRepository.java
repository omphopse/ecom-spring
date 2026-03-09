package com.E_CommerceBackendSystem.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
