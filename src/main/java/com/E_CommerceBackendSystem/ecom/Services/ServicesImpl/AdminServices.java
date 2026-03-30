package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Category;
import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.Services.AdminServiceInterface;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.repository.CategoryRepository;
import com.E_CommerceBackendSystem.ecom.repository.OrderRepository;
import com.E_CommerceBackendSystem.ecom.repository.PaymentRepository;
import com.E_CommerceBackendSystem.ecom.repository.ProductRepository;
import com.E_CommerceBackendSystem.ecom.repository.UserRepository;

@Service
public class AdminServices implements AdminServiceInterface{
	
}
