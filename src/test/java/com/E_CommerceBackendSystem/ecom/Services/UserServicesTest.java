package com.E_CommerceBackendSystem.ecom.Services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.RedisService;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserServices;
import com.E_CommerceBackendSystem.ecom.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {
	
	@InjectMocks
	private UserServices userServices;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private KafkaTemplate<String, Users> kafkaTemplate;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private RedisService redisService;
	
	@Test
	public void testCreateUser() {
		Users user = new Users();
		
		user.setId(1);
		user.setEmail("om@mail.com");
		user.setUsername("om");
		user.setPassword("om");
		
		when(userRepository.save(any())).thenReturn(user);
		
		assertTrue(userServices.createNewUser(user));
		
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	public void getAllUser() {
		Users user = new Users();
		
		user.setId(1);
		user.setEmail("om@mail.com");
		user.setUsername("om");
		user.setPassword("om");
		
		List<Users> lu= List.of(user);
		
		when(userRepository.findAll()).thenReturn(lu);
		
		assertNotNull(userServices.findAllUsers());
		
		verify(userRepository,times(1)).findAll();
	}
	
	@Test
	public void testFindByUserName() {
		Users user = new Users();
		
		user.setId(1);
		user.setEmail("om@mail.com");
		user.setUsername("om");
		user.setPassword("om");
		
		when(userRepository.findByUsername(anyString())).thenReturn(user);
		
		assertNotNull(userServices.findByUserName("Om"));
		
		verify(userRepository,times(1)).findByUsername(anyString());
	}
	
	@Test
	public void testFindById() {
		Users user = new Users();
		
		user.setId(1);
		user.setEmail("om@mail.com");
		user.setUsername("om");
		user.setPassword("om");
		
		Optional<Users> opuser= Optional.of(user);
		when(userRepository.findById(anyLong())).thenReturn(opuser);
		
		assertNotNull(userServices.findById(1));
		
		verify(userRepository,times(1)).findById(anyLong());
	}
}
