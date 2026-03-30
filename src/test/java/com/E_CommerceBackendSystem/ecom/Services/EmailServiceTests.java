package com.E_CommerceBackendSystem.ecom.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.EmailService;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {

	@InjectMocks
	private EmailService emailService;
	
	@Mock
	private JavaMailSender javaMailSender;

	@BeforeEach
	void setUp() {
	    MockitoAnnotations.openMocks(this);
	}
	  
	@Disabled
	@Test
	public void test() {
		assertEquals(5, 3+2);
	}
	
	@Test
	public void testEmail() {
		 emailService.sendMail(
		            "test@gmail.com",
		            "Hello",
		            "Test Message"
		        );

		        verify(javaMailSender, times(1))
		                .send(any(SimpleMailMessage.class));
	}
}
