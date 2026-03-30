package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.CartServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.OrderServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.PaymentServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ProductServiceInterface;
import com.E_CommerceBackendSystem.ecom.dto.UserCartDto;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.enums.PaymentStatus;
import com.E_CommerceBackendSystem.ecom.repository.OrderItemRepository;
import com.E_CommerceBackendSystem.ecom.repository.PaymentRepository;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class PaymenServices implements PaymentServiceInterface{

	@Autowired 
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderServiceInterface orderServiceInterface;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private KafkaTemplate<String, Payments> kafkaTemplate;
	
	@Override
	public List<Payments> getAllPayments() {
		
		String key = "payments:all";

	    List<Payments> payments = redisService.get(key, new TypeReference<List<Payments>>() {});

	    if (payments != null) return payments;
	    payments = paymentRepository.findAll();
	    redisService.set(key, payments, 300);
	    
		return payments;
	}

	@Override
	public List<Payments> getMypayments(long id) {
		String key = "mypayments:"+id;

		List<Payments> payments = redisService.get(key, new TypeReference<List<Payments>>() {});

		if (payments != null) return payments;
	    payments = paymentRepository.findAllByOrderUserId(id);
	    redisService.set(key, payments, 300);
	    
		return payments;
	}


	@Override
	public boolean savepayment(Payments payment, Order order) {

	    String txId = "TXID000" + order.getId();

	    Optional<Payments> existing =
	        paymentRepository.findByTransactionId(txId);

	    if (existing.isPresent()) {
	        return false; // already processed
	    }

	    payment.setOrder(order);
	    payment.setPaymentStatus(PaymentStatus.SUCCESS);
	    payment.setTransactionId(txId);

	    orderServiceInterface.changeOrderStatus(order.getId(), OrderStatus.PLACED);
	    Payments savedPayment = paymentRepository.save(payment);

	    
	    kafkaTemplate.send("payment", savedPayment);

	    return true;
	}


}
