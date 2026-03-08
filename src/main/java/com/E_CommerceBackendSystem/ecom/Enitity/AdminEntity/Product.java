package com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name="Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="p_id")
	private long p_id;
	@Column(name="p_name",nullable = false)
	private String p_name;
	@Column(name="p_description")
	private String p_description;
	@Column(name="p_price",nullable=false)
	private float price;
	@Column(name="p_stock_quantity",nullable=false)
	private int p_stock_quantity;
	@ManyToOne
	@JoinColumn(name="cat_id")
	private Category category;
	@CreationTimestamp
	@Column(name="created_at",nullable=false)
	private Instant created_at;
	@UpdateTimestamp
	@Column(name="updated_at",nullable=false)
	private Instant updated_at;
}
