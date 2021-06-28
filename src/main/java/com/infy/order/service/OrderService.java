package com.infy.order.service;

import java.util.List;

import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.PlacedOrderDTO;
import com.infy.order.dto.ProductDTO;
import com.infy.order.exception.OrderMsException;

public interface OrderService {
	
	public PlacedOrderDTO placeOrder(List<ProductDTO> prodList, List<CartDTO> cartList, OrderDTO orderDto) throws OrderMsException;
	
	public OrderDTO viewOrder(Integer orderId) throws OrderMsException;
	
	public List<OrderDTO> viewAllOrders() throws OrderMsException;
	
	public List<OrderDTO> viewOrdersByBuyer(Integer buyerId) throws OrderMsException;
	
	public Integer reOrder(Integer buyerId, Integer orderId) throws OrderMsException;

}
