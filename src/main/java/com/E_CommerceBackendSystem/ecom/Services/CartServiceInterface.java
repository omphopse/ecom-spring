package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;
import java.util.Map;

import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;

public interface CartServiceInterface {
	boolean addItemstoCart(long u_id,long p_id,int quantity);
	List<UserCartDto> getCart(long userId);
	boolean removeItem(long userId,long productId);
	boolean clearCart(long userId);
	
	int getCartItemCount(long userId);
	double getCartTotal(long userId);
}
