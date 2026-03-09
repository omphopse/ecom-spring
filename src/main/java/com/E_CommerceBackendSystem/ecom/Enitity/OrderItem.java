package com.E_CommerceBackendSystem.ecom.Enitity;

import org.hibernate.annotations.ManyToAny;

import com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="orderitem")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order_id;
	@ManyToOne
	@JoinColumn(name="p_id")
	private Product product_id;
	@Column(name="quantity", nullable = false)
	private int quantity;
	@Column(name="price", nullable = false)
	private float price;
}
