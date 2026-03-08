package com.E_CommerceBackendSystem.ecom.Enitity.userentity;

import java.time.Instant;

import lombok.Data;

@Data
public class Cart {
	private long user_id;
	private Instant created_at;
}
