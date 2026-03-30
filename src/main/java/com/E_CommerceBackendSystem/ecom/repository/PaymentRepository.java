package com.E_CommerceBackendSystem.ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_CommerceBackendSystem.ecom.Enitity.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long>{
	 List<Payments> findAllByOrderUserId(Long userId);
	 Optional<Payments> findByTransactionId(String txId);
	 
}
