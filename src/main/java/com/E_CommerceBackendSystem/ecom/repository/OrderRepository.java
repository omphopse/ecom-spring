package com.E_CommerceBackendSystem.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
