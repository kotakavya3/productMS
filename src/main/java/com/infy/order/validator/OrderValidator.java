package com.infy.order.validator;

import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.ProductDTO;
import com.infy.order.exception.OrderMsException;

public class OrderValidator {
	
	public static void validateOrder(OrderDTO order) throws OrderMsException {
		if(!validateAddress(order.getAddress()))
			throw new OrderMsException("Validator.INVALID_ADDRESS");		
		
	}
	
	public static void validateStock(CartDTO cart, ProductDTO product) throws OrderMsException {
		if(!validateStock(product.getStock(),cart.getQuantity()))
			throw new OrderMsException("Validator.NO_STOCK");	
	}
	
	
	private static boolean validateAddress(String address) {		
		return (address.length()>0 &&address.length()<100);		
	}
	
	private static boolean validateStock(Integer stock, Integer quantity) {		
		return stock>=quantity;		
	}
}
