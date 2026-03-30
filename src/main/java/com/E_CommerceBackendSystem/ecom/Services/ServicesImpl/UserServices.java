package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.AdminServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.enums.PaymentMethods;
import com.E_CommerceBackendSystem.ecom.enums.PaymentStatus;
import com.E_CommerceBackendSystem.ecom.repository.OrderItemRepository;
import com.E_CommerceBackendSystem.ecom.repository.OrderRepository;
import com.E_CommerceBackendSystem.ecom.repository.PaymentRepository;
import com.E_CommerceBackendSystem.ecom.repository.ProductRepository;
import com.E_CommerceBackendSystem.ecom.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Service
public class UserServices implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private KafkaTemplate<String, Users> kafkaTemplate;
	
	@Override
	public boolean createNewUser(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(user);
		if(user != null) {
			kafkaTemplate.send("register", user);
			return true;
		}
			
		return false;
	}
	
	@Override
	public List<Users> findAllUsers() {
		String key="users:all";
		List<Users> users = redisService.get(key, new TypeReference<List<Users>>(){});
		
		if(users!=null) return users;
		
		users = userRepository.findAll();
		redisService.set(key, users, 300);
		return users;
	}
	
	@Override
	public Users findByUserName(String username) {

	    String key = "user:" + username;

	    Users user = redisService.get(key, new TypeReference<Users>() {});

	    if (user != null) {
	        return user;
	    }
	    user = userRepository.findByUsername(username);

	    if (user != null) {
	        redisService.set(key, user, 300);
	    }

	    return user;
	}
	
	@Override
	public Users findById(long id) {
		return userRepository.findById(id).orElse(null);
	}
}
