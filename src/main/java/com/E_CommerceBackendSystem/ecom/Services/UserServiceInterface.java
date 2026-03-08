package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;

import com.E_CommerceBackendSystem.ecom.Enitity.Users;

public interface UserServiceInterface{
	Users findByUserName(String username);
	List<Users> findAllUsers();
	boolean createNewUser(Users user);
}
