package com.E_CommerceBackendSystem.ecom.Enitity.AdminEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cat_id")
	private long cat_id;
	@Column(name="cat_Name",nullable=false,unique=true)
	private String cat_Name;
	@Column(name="cat_description")
	private String cat_description;
}
