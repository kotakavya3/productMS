package com.infy.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.PlacedOrderDTO;
import com.infy.order.dto.ProductDTO;
import com.infy.order.exception.OrderMsException;
import com.infy.order.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping(value="/orderMS")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	RestTemplate template;
	
	@Autowired
	Environment environment;
	
	@PostMapping(value = "/placeOrder/{buyerId}")
	public ResponseEntity<String> placeOrder(@PathVariable Integer buyerId, @RequestBody OrderDTO order) throws OrderMsException{
		
		try {			
		ObjectMapper mapper = new ObjectMapper();
		List<ProductDTO> productList = new ArrayList<>();
		List<CartDTO> cartList = mapper.convertValue(
				template.getForObject("http://USERSERVICEMS"+"/userMS/buyer/cart/get/" + buyerId, List.class), 
			    new TypeReference<List<CartDTO>>() {}
			);
			System.out.println(cartList);

			System.out.println("before product");
			cartList.forEach(item ->{
				ProductDTO prod = template.getForObject("http://PRODUCTSERVICE"+"/productMS/getById/" +item.getProdId(),ProductDTO.class) ; //getByProdId/{productId}
				System.out.println(prod.getDescription());
				productList.add(prod);
			});
			
			PlacedOrderDTO orderPlaced = orderService.placeOrder(productList,cartList,order);
			System.out.println("before for loop");
			cartList.forEach(item->{
				template.getForObject("http://PRODUCTSERVICE"+"/productMS/updateStock/" +item.getProdId()+"/"+item.getQuantity(), boolean.class) ;
				template.postForObject("http://USERSERVICEMS"+"/userMS/buyer/cart/remove/"+buyerId+"/"+item.getProdId(),null, String.class);
			});			
			System.out.println("before final");
			template.getForObject("http://USERSERVICEMS"+"/userMS/updateRewardPoints/"+buyerId+"/"+orderPlaced.getRewardPoints() , String.class);
			
			return new ResponseEntity<>("Your order placed with order Id: "+orderPlaced.getOrderId(),HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String message = e.getMessage();
			if(e.getMessage().equals("404 null"))
				return new ResponseEntity<>("Error while placing the order",HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(message,HttpStatus.UNAUTHORIZED);
		}		
		
	}
	
	@GetMapping(value = "/viewAll")
	public ResponseEntity<List<OrderDTO>> viewAllOrder(){		
		try {
			List<OrderDTO> allOrders = orderService.viewAllOrders();
			return new ResponseEntity<>(allOrders,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}		
	}
	
	@GetMapping(value = "/viewOrders/{buyerId}")
	public ResponseEntity<List<OrderDTO>> viewsOrdersByBuyerId(@PathVariable Integer buyerId){		
		try {
			List<OrderDTO> allOrders = orderService.viewOrdersByBuyer(buyerId);
			return new ResponseEntity<>(allOrders,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}		
	}
	
	@GetMapping(value = "/viewOrder/{orderId}")
	public ResponseEntity<OrderDTO> viewsOrderByOrderId(@PathVariable Integer orderId){		
		try {
			OrderDTO allOrders = orderService.viewOrder(orderId);
			return new ResponseEntity<>(allOrders,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}		
	}
	
	
	@PostMapping(value = "/reOrder/{buyerId}/{orderId}")
	public ResponseEntity<String> reOrder(@PathVariable Integer buyerId, @PathVariable Integer orderId){
		
		try {
			
			Integer id = orderService.reOrder(buyerId,orderId);
			return new ResponseEntity<>("Order ID: "+id,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@PostMapping(value = "/addToCart/{buyerId}/{prodId}/{quantity}")
	public ResponseEntity<String> addToCart(@PathVariable String buyerId, @PathVariable String prodId,@PathVariable Integer quantity){
		
		try {
			String successMsg = template.postForObject("http://USERSERVICEMS"+"/userMS/buyer/cart/add/"+buyerId+"/"+prodId+"/"+quantity, null, String.class);

			return new ResponseEntity<>(successMsg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = e.getMessage();
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product ID";
			}
			return new ResponseEntity<>(newMsg,HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@PostMapping(value = "/removeFromCart/{buyerId}/{prodId}")
	public ResponseEntity<String> removeFromCart(@PathVariable String buyerId, @PathVariable String prodId){
		
		try {		
			String successMsg = template.postForObject("http://USERSERVICEMS"+"/userMS/buyer/cart/remove/"+buyerId+"/"+prodId, null, String.class);
			
			return new ResponseEntity<>(successMsg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product ID";
			}
			return new ResponseEntity<>(newMsg,HttpStatus.UNAUTHORIZED);
		}		
	}
	
}
	
