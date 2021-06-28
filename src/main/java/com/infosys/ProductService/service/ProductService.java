package com.infosys.ProductService.service;

import java.util.List;

import com.infosys.ProductService.dto.ProductDTO;
import com.infosys.ProductService.exception.ProductServiceException;

public interface ProductService {
	public Integer addProduct(ProductDTO productDTO) throws ProductServiceException;
	public void deleteProduct(Integer prodId) throws ProductServiceException;
	public ProductDTO getByProductName(String name) throws ProductServiceException;
	public ProductDTO getByProductId(Integer prodId) throws ProductServiceException;
	public List<ProductDTO> getByProductCategroy(String name) throws ProductServiceException;
	public List<ProductDTO> getAllProduct() throws ProductServiceException;
	public Boolean updateStockOfProd(Integer prodId, Integer quantity) throws ProductServiceException;
}
