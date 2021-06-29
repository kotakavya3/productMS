package com.infosys.ProductService.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.ProductService.dto.ProductDTO;
import com.infosys.ProductService.exception.ProductServiceException;
import com.infosys.ProductService.service.ProductService;

@RestController
@RequestMapping(value="/productMS")
public class ProductServiceAPI {
	@Autowired
	private ProductService productServiceImpl;
	
	@Autowired
	Environment environment;
	
	@PostMapping(value="/addProduct")
	public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO product) throws ProductServiceException{
			Integer prodId=productServiceImpl.addProduct(product);
			String message="Product Id is :"+prodId;
			return new ResponseEntity<>(message,HttpStatus.ACCEPTED);			
	}
	
	@DeleteMapping(value="/deleteProduct/{prodId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer prodId) throws ProductServiceException{
			productServiceImpl.deleteProduct(prodId);
			String message=prodId+"Product deleted";
			return new ResponseEntity<>(message,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/getByName/{name}")
	public ResponseEntity<ProductDTO> getByProductName(@PathVariable String name) throws ProductServiceException{
			ProductDTO productDTO=productServiceImpl.getByProductName(name);
			return new ResponseEntity<>(productDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getById/{id}")
	public ResponseEntity<ProductDTO> getByProductId(@PathVariable Integer id) throws ProductServiceException
	{	
			ProductDTO productDTO = productServiceImpl.getByProductId(id);
			return new ResponseEntity<>(productDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getByCategory/{name}")
	public ResponseEntity<List<ProductDTO>> getByProductCategory(@PathVariable String name) throws ProductServiceException
	{
			List<ProductDTO> productDTO = productServiceImpl.getByProductCategroy(name);
			return new ResponseEntity<List<ProductDTO>>(productDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "/viewAllProducts")
	public ResponseEntity<List<ProductDTO>> viewAllProducts() throws ProductServiceException
	{
			List<ProductDTO> productDTO = productServiceImpl.getAllProduct();
			return new ResponseEntity<List<ProductDTO>>(productDTO,HttpStatus.OK);	
	}

	@GetMapping(value = "/updateStock/{prodId}/{quantity}")
	public ResponseEntity<Boolean> updateStock(@PathVariable Integer prodId, @PathVariable Integer quantity) throws ProductServiceException{
			Boolean status = productServiceImpl.updateStockOfProd(prodId, quantity);
			return new ResponseEntity<>(status,HttpStatus.ACCEPTED);
	
	}
	
}
