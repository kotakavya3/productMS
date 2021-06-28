package com.infy.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.PlacedOrderDTO;
import com.infy.order.dto.ProductDTO;
import com.infy.order.entity.Order;
import com.infy.order.entity.ProductsOrdered;
import com.infy.order.exception.OrderMsException;
import com.infy.order.repository.OrderRepository;
import com.infy.order.repository.ProductsOrderedRepository;
import com.infy.order.utility.PrimaryKeys;
import com.infy.order.utility.Status;
import com.infy.order.validator.OrderValidator;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductsOrderedRepository prodsOrderedRepo;

	@Override
	public PlacedOrderDTO placeOrder(List<ProductDTO> prodList, List<CartDTO> cartList, OrderDTO orderDto)
			throws OrderMsException {
		Order order = new Order();
		OrderValidator.validateOrder(orderDto);
		order.setAddress(orderDto.getAddress());
		order.setBuyerId(cartList.get(0).getBuyerId());
		order.setDate(LocalDate.now());
		order.setStatus(Status.ORDER_PLACED.toString());
		List<ProductsOrdered> productsOrdered = new ArrayList<>();
		for(int i = 0; i<cartList.size();i++) {
			OrderValidator.validateStock(cartList.get(i), prodList.get(i));			
			order.setAmount((cartList.get(i).getQuantity()*prodList.get(i).getPrice()));
			
			ProductsOrdered product = new ProductsOrdered();
			product.setSellerId(prodList.get(i).getSellerId());
			product.setPrimaryKeys(new PrimaryKeys(cartList.get(i).getBuyerId(),prodList.get(i).getProdId()));
			product.setQuantity(cartList.get(i).getQuantity());
			productsOrdered.add(product);				
		}		
		prodsOrderedRepo.saveAll(productsOrdered);
		orderRepo.save(order);
		Integer rewardPts = (int)(order.getAmount()/100);
		PlacedOrderDTO placedOrder = new PlacedOrderDTO();
		placedOrder.setBuyerId(order.getBuyerId());
		placedOrder.setOrderId(order.getOrderId());
		placedOrder.setRewardPoints(rewardPts);
		
		return placedOrder;
	}

	@Override
	public OrderDTO viewOrder(Integer orderId) throws OrderMsException {
		Optional<Order> optional = orderRepo.findByOrderId(orderId);
		Order order = optional.orElseThrow(()->new OrderMsException("Service.NO_ORDER_EXISTS"));
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setBuyerId(order.getBuyerId());
		orderDTO.setAmount(order.getAmount());
		orderDTO.setAddress(order.getAddress());
		orderDTO.setDate(order.getDate());
		orderDTO.setStatus(order.getStatus());		
		return orderDTO;
	}

	@Override
	public List<OrderDTO> viewAllOrders() throws OrderMsException {
		Iterable<Order> orders = orderRepo.findAll();
		List<OrderDTO> dtoList = new ArrayList<>();
		orders.forEach(order -> {
			OrderDTO orderDto = new OrderDTO();
			orderDto.setOrderId(order.getOrderId());
			orderDto.setBuyerId(order.getBuyerId());
			orderDto.setAmount(order.getAmount());
			orderDto.setAddress(order.getAddress());
			orderDto.setDate(order.getDate());
			orderDto.setStatus(order.getStatus());
			dtoList.add(orderDto);			
		});
		if(dtoList.isEmpty()) throw new OrderMsException("Service.NO_ORDERS");
		return dtoList;
	}

	@Override
	public List<OrderDTO> viewOrdersByBuyer(Integer buyerId) throws OrderMsException {
		List<Order> orders = orderRepo.findByBuyerId(buyerId);
		if(orders.isEmpty()) throw new OrderMsException("Service.NO_ORDER_FOR_BUYER");
		List<OrderDTO> dtoList = new ArrayList<>();
		orders.forEach(order->{
			OrderDTO orderDto = new OrderDTO();
			orderDto.setOrderId(order.getOrderId());
			orderDto.setBuyerId(order.getBuyerId());
			orderDto.setAmount(order.getAmount());
			orderDto.setAddress(order.getAddress());
			orderDto.setDate(order.getDate());
			orderDto.setStatus(order.getStatus());
			dtoList.add(orderDto);
		});
		return dtoList;
	}

	@Override
	public Integer reOrder(Integer buyerId, Integer orderId) throws OrderMsException {
		Optional<Order> optional = orderRepo.findByOrderId(orderId);
		Order order = optional.orElseThrow(()->new OrderMsException("Service.NO_PREVIOUS_ORDERS"));
		Order reorder = new Order();
		reorder.setBuyerId(order.getBuyerId());
		reorder.setAmount(order.getAmount());
		reorder.setAddress(order.getAddress());
		reorder.setDate(LocalDate.now());
		reorder.setStatus(Status.ORDER_PLACED.toString());
		
		orderRepo.save(reorder);		
		return reorder.getOrderId();
	}

}
