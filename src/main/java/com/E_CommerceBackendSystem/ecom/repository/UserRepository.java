package com.E_CommerceBackendSystem.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_CommerceBackendSystem.ecom.Enitity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	Users findByUsername(String username);
}
