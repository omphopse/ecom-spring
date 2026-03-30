package com.E_CommerceBackendSystem.ecom.Services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.PaymenServices;
import com.E_CommerceBackendSystem.ecom.Services.ServicesImpl.RedisService;
import com.E_CommerceBackendSystem.ecom.enums.PaymentMethods;
import com.E_CommerceBackendSystem.ecom.repository.PaymentRepository;
import com.fasterxml.jackson.core.type.TypeReference;

@ExtendWith(MockitoExtension.class)
public class PaymentServicesTest {

	@InjectMocks
	private PaymenServices paymenServices;
	
	@Mock
	private PaymentRepository paymentRepository;
	
	@Mock
	private RedisService redisService;
	
	@Mock
	private OrderServiceInterface orderServiceInterface;
	
	@Mock
	private KafkaTemplate<String, Payments> kafkaTemplate;
	
	@Test
	public void testGetAllPaymentsRepo() {
		Payments payments = new Payments();
		Order order = new Order();
		payments.setId(1);
		payments.setOrder(order);
		payments.setPaymentMethod(PaymentMethods.CASH);
		payments.setTransactionId("TXID0001");
		
		List<Payments> lp = List.of(payments);
		
		when(paymentRepository.findAll()).thenReturn(lp);
		when(redisService.get(eq("payments:all"), any(TypeReference.class))).thenReturn(null);
		
		List<Payments> list = paymenServices.getAllPayments();
		assertNotNull(list);
		verify(paymentRepository, times(1)).findAll();
		verify(redisService, times(1)).get(eq("payments:all"),any(TypeReference.class));
	}
	
	
	@Test
	public void testGetMyPayments() {
		Payments payments = new Payments();
		Order order = new Order();
		payments.setId(1);
		payments.setOrder(order);
		payments.setPaymentMethod(PaymentMethods.CASH);
		payments.setTransactionId("TXID0001");
		
		List<Payments> lp = List.of(payments);
		
		when(paymentRepository.findAllByOrderUserId(anyLong())).thenReturn(lp);
		
		List<Payments> list = paymenServices.getMypayments(1);
		
		assertNotNull(list);
		verify(paymentRepository, times(1)).findAllByOrderUserId(anyLong());
	}
		
	
	@Test
	public void testSavePayments() {
		Payments payments = new Payments();
		payments.setPaymentMethod(PaymentMethods.CARD);
		
		Order order = new Order();
		order.setId(1);
		
		when(paymentRepository.findByTransactionId(anyString()))
        .thenReturn(Optional.empty());
		assertTrue(paymenServices.savepayment(payments, order));
		
		verify(paymentRepository,times(1)).save(any());
	}
}
