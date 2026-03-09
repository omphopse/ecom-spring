package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;
import java.util.Map;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;

public interface UserServiceInterface{
	Users findByUserName(String username);
	List<Users> findAllUsers();
	boolean createNewUser(Users user);
	boolean addItemstoCart(long u_id,long p_id,int quantity);
	Map<Object,Object> getCart(long userId);
	boolean removeItem(long userId,long productId);
	boolean clearCart(long userId);
	boolean createOrder(Order order);
	boolean checkOut(Payments payment, Users user);
}
