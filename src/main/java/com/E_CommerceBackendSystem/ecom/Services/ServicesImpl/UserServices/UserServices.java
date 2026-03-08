package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.repository.UserRepository;

@Service
public class UserServices implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean createNewUser(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(userRepository.save(user) != null) return true;
		return false;
	}
	
	@Override
	public List<Users> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public Users findByUserName(String username) {
		return userRepository.findByUsername(username);
	}
}
