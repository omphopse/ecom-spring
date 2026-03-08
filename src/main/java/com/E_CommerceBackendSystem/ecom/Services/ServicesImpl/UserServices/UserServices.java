package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserServices;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
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

	@Override
	public boolean addItemstoCart(long u_id, long p_id, int quantity) {
	    String cartKey = "cart:" + u_id;
	    redisTemplate.opsForHash().increment(cartKey, String.valueOf(p_id), quantity);
	    redisTemplate.expire(cartKey, Duration.ofHours(24));
	    return true;
	}
	
	@Override
	public Map<Object,Object> getCart(long userId){
	    return redisTemplate.opsForHash().entries("cart:"+userId);
	}
	
	@Override
	public boolean removeItem(long userId, long productId) {
		redisTemplate.opsForHash().delete("cart:"+userId,String.valueOf(productId));
		return true;
	} 
	
	@Override
	public boolean clearCart(long userId){
	    redisTemplate.delete("cart:"+userId);
	    return true;
	}
	
	
}
