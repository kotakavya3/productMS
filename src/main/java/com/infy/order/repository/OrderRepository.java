package com.infy.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	public List<Order> findByBuyerId(Integer buyerId);
	
	public Optional<Order> findByOrderId(Integer orderId);
	
}
