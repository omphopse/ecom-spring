package com.E_CommerceBackendSystem.ecom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserDetailsServiceImpl;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.UserServices;
import com.E_CommerceBackendSystem.ecom.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserServiceInterface userServices;
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
    private UserDetailsServiceImpl userDetailService;

    @Autowired
    private JwtUtil jwtUtil;
    
	@GetMapping
	public String test() {
		return "public";
	}
	
	@PostMapping("/register")
	public ResponseEntity<Users> registerNewUser(@RequestBody Users user){
		if(userServices.createNewUser(user)) return new ResponseEntity<>(user,HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
	        String jwt = jwtUtil.generateToken(userDetails.getUsername());
	        return new ResponseEntity<>(jwt, HttpStatus.OK);
		}
		catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
	}
}
