package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;
import java.util.Optional;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;

public interface PaymentServiceInterface {
	List<Payments> getAllPayments();
	List<Payments> getMypayments(long id);
	boolean savepayment(Payments payments, Order order);
}
