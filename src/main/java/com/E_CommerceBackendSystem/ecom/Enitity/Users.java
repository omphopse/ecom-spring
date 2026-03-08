package com.E_CommerceBackendSystem.ecom.Enitity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.E_CommerceBackendSystem.ecom.enums.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="username",nullable = false, unique = true)
	private String username;
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="email",nullable = false, unique = true)
	private String email;
	@Enumerated(EnumType.STRING)
	private UserRoles userroles;
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;
}
