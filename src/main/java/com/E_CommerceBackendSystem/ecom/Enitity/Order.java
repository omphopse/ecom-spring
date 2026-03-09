package com.E_CommerceBackendSystem.ecom.Enitity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="o_id")
	private long o_id;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@Column(name="total_price",nullable = true)
	private double total_price;
	@CreationTimestamp
	@Column(name="created_at")
	private Instant created_at;
	@ManyToOne
	@JoinColumn(name="id")
	private Users user;
}
