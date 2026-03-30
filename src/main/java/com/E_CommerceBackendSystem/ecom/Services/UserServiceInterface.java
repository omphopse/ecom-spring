package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;
import java.util.Map;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;

public interface UserServiceInterface{
	Users findByUserName(String username);
	List<Users> findAllUsers();
	boolean createNewUser(Users user);
	Users findById(long id);
}
