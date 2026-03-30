package com.E_CommerceBackendSystem.ecom.Services;

import java.util.List;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Users;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;

public interface OrderServiceInterface {
	List<Order> getAllOrder();
	boolean changeOrderStatus(long oid,OrderStatus status);
	Order createOrder(Order order);
	List<Order> findMyOrdes(long id);
	Order checkOut(Users user);
	Order findByOrderID(long id);
	List<Order> getOrdersByStatus(String status);
	boolean cancelOrder(long orderId);
}
