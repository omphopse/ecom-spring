package com.E_CommerceBackendSystem.ecom.Services.ServicesImpl;

import java.util.List;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import com.E_CommerceBackendSystem.ecom.Enitity.Order;
import com.E_CommerceBackendSystem.ecom.Enitity.OrderItem;
import com.E_CommerceBackendSystem.ecom.Enitity.Payments;
import com.E_CommerceBackendSystem.ecom.Enitity.Product;
import com.E_CommerceBackendSystem.ecom.Services.OrderServiceInterface;
import com.E_CommerceBackendSystem.ecom.Services.ProductServiceInterface;
import com.E_CommerceBackendSystem.ecom.enums.OrderStatus;
import com.E_CommerceBackendSystem.ecom.enums.PaymentStatus;
import com.E_CommerceBackendSystem.ecom.repository.OrderItemRepository;

@Service
@EnableKafkaStreams
public class StockServices {
	
	@Autowired
	private ProductServiceInterface productServiceInterface;
	
	@Autowired
	private OrderItemRepository itemRepository;
	
	@Autowired
	private OrderServiceInterface orderServiceInterface;
	
	@KafkaListener(topics="payment", groupId = "payment_status")
	public void updateStock(Payments payment) {

	    Order order = payment.getOrder();

	    if (order == null) return;

	    if (payment.getPaymentStatus().equals("SUCCESS")) {

	        List<OrderItem> orderItems = itemRepository.findByOrder_Id(order.getId());

	        orderItems.forEach(item -> 
	            updateStock(item.getProduct(), item.getQuantity())
	        );
	    }
	}
	
	@Bean
	public KStream<String, Order> process(StreamsBuilder builder) {

	    JsonSerde<Payments> paymentSerde = new JsonSerde<>(Payments.class);
	    JsonSerde<Order> orderSerde = new JsonSerde<>(Order.class);

	    KStream<String, Payments> stream =
	        builder.stream("payment", Consumed.with(Serdes.String(), paymentSerde));

	    KStream<String, Order> updatedOrders = stream
	    	    .filter((key, payment) -> 
	    	        payment != null &&
	    	        payment.getPaymentStatus() == PaymentStatus.SUCCESS &&
	    	        payment.getOrder() != null
	    	    )
	    	    .selectKey((key, payment) -> String.valueOf(payment.getOrder().getId()))
	    	    .mapValues(payment -> {
	    	        Order order = payment.getOrder();
	    	        order.setOrderStatus(OrderStatus.PLACED);
	    	        return order;
	    	    });
	    updatedOrders.to("orders", Produced.with(Serdes.String(), orderSerde));

	    return updatedOrders;
	}
	
	
	public boolean updateStock(Product product, int quantity) {
		Product updatedProduct = productServiceInterface.findByProductId(product.getP_id());
		updatedProduct .setStockQuantity(updatedProduct.getStockQuantity()-quantity);
		
		if(productServiceInterface.updateProducts(updatedProduct.getP_id(), updatedProduct )) return true;
		return false;
	}
}
