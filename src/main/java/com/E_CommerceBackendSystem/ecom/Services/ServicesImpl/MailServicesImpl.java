package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.OrderServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.UserServiceInterface;

@Service
public class MailServicesImpl {
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OrderServiceInterface orderServiceInterface;
	
	
	@Autowired
	private UserServiceInterface userServiceInterface;
	
	@KafkaListener(topics="orders", groupId = "order_group")
	public void OrderEmail(Order order) {
		Users users = userServiceInterface.findById(order.getUser().getId());
		emailService.sendMail(users.getEmail(), "Your order has been "+order.getOrderStatus(), "Order details : "+order.getTotal_price());
	}
	
	@KafkaListener(topics="register", groupId = "new_users")
	public void welcome(Users users) {
		emailService.sendMail(users.getEmail(), "Welecome to ecom api", "visit ecom-api.com for more details");
	}
}
