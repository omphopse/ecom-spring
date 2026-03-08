package com.E_CommerceBackendSystem.ecom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserServices.UserServices;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserServiceInterface userServices;
	
	@GetMapping
	public String test() {
		return "public";
	}
	
	@PostMapping("/register")
	public ResponseEntity<Users> registerNewUser(@RequestBody Users user){
		if(userServices.createNewUser(user)) return new ResponseEntity<>(user,HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
