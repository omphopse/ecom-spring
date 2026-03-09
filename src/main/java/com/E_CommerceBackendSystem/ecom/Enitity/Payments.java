package com.E_CommerceBackendSystem.ecom.Enitity;

import com.E_CommerceBackendSystem.ecom.enums.PaymentMethods;
import com.E_CommerceBackendSystem.ecom.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="payments")
public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@OneToOne
	@JoinColumn(name="o_id")
	private Order order;
	@Enumerated(EnumType.STRING)
	private PaymentMethods paymentMethod;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	@Column(name="transaction_id",nullable = false, unique = true)
	private String transaction_id;
}
