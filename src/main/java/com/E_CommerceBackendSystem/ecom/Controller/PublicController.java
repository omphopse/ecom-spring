package com.E_CommerceBackendSystem.ecom.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
	@GetMapping
	public String test() {
		return "public";
	}
}
