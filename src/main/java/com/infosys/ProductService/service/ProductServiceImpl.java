package com.infosys.ProductService.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.infosys.ProductService.dto.ProductDTO;
import com.infosys.ProductService.entity.Product;
import com.infosys.ProductService.exception.ProductServiceException;
import com.infosys.ProductService.repository.ProductRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Integer addProduct(ProductDTO productDTO) throws ProductServiceException {
		// TODO Auto-generated method stub
		System.out.println("in service");
		Product prod=productRepository.findByProductName(productDTO.getProductName());
		if(prod!=null) {
			throw new ProductServiceException("Service.PRODUCT_ALREADY_EXISTS");
		}
		Product product=new Product();
		product.setCategory(productDTO.getCategory());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		product.setPrice(productDTO.getPrice());
		product.setProductName(productDTO.getProductName());
		product.setProductRating(productDTO.getProductRating());
		product.setSellerId(productDTO.getSellerId());
		product.setStock(productDTO.getStock());
		product.setSubCategory(productDTO.getSubCategory());
		productRepository.save(product);
		return product.getProdId();
	}

	
	@Override
	public void deleteProduct(Integer prodId) throws ProductServiceException {
		// TODO Auto-generated method stub
		Optional<Product> optional=productRepository.findById(prodId);
		Product product=optional.orElseThrow(()->new ProductServiceException("Service.CANNOT_DELETE_PRODUCT"));
		productRepository.delete(product);
	}


	@Override
	public ProductDTO getByProductName(String name) throws ProductServiceException {
		// TODO Auto-generated method stub
		Product product=productRepository.findByProductName(name);
		if(product==null) {
			throw new ProductServiceException("PRODUCT_NAME_NOT_EXISTED");
		}
		ProductDTO prod=new ProductDTO();
		prod.setProdId(product.getProdId());
		prod.setCategory(product.getCategory());
		prod.setDescription(product.getDescription());
		prod.setImage(product.getImage());
		prod.setPrice(product.getPrice());
		prod.setProductName(product.getProductName());
		prod.setProductRating(product.getProductRating());
		prod.setSellerId(product.getSellerId());
		prod.setStock(product.getStock());
		prod.setSubCategory(product.getSubCategory());
		return prod;
	}


	@Override
	public ProductDTO getByProductId(Integer prodId) throws ProductServiceException {
		Product product=productRepository.findByProdId(prodId);
		if(product==null) {
			throw new ProductServiceException("Service.PRODUCT_DOES_NOT_EXISTS");
		}
		ProductDTO prod=new ProductDTO();
		prod.setProdId(product.getProdId());
		prod.setCategory(product.getCategory());
		prod.setDescription(product.getDescription());
		prod.setImage(product.getImage());
		prod.setPrice(product.getPrice());
		prod.setProductName(product.getProductName());
		prod.setProductRating(product.getProductRating());
		prod.setSellerId(product.getSellerId());
		prod.setStock(product.getStock());
		prod.setSubCategory(product.getSubCategory());
		return prod;
	}


	@Override
	public List<ProductDTO> getByProductCategroy(String category) throws ProductServiceException {
		// TODO Auto-generated method stub
     List<Product> list = productRepository.findByCategory(category);
		
		if(list.isEmpty())
			throw new ProductServiceException("Service.CATEGORY_ERROR");
		
		List<ProductDTO> li = new ArrayList<>();
		
		for(Product product : list)
		{
			ProductDTO productDTO = new ProductDTO();
			
			productDTO.setProdId(product.getProdId());
			productDTO.setCategory(product.getCategory());
			productDTO.setDescription(product.getDescription());
			productDTO.setImage(product.getImage());
			productDTO.setPrice(product.getPrice());
			productDTO.setProductName(product.getProductName());
			productDTO.setProductRating(product.getProductRating());
			productDTO.setSellerId(product.getSellerId());
			productDTO.setStock(product.getStock());
			productDTO.setSubCategory(product.getCategory());
			
			li.add(productDTO);
		}
		
		return li;
	}


	@Override
	public List<ProductDTO> getAllProduct() throws ProductServiceException {
		// TODO Auto-generated method stub
List<Product> list = productRepository.findAll();
		
		if(list.isEmpty())
			throw new ProductServiceException("Service.NO_PRODUCT_EXIST");
		
		List<ProductDTO> li = new ArrayList<>();
		
		list.forEach(l -> {
			ProductDTO prod = new ProductDTO();
			prod.setCategory(l.getCategory());
			prod.setDescription(l.getDescription());
			prod.setImage(l.getImage());
			prod.setPrice(l.getPrice());
			prod.setProdId(l.getProdId());
			prod.setProductName(l.getProductName());
			prod.setProductRating(l.getProductRating());
			prod.setSellerId(l.getSellerId());
			prod.setStock(l.getStock());
			prod.setSubCategory(l.getSubCategory());
			
			li.add(prod);
		});
		
		return li;
	}


	@Override
	public Boolean updateStockOfProd(Integer prodId, Integer quantity) throws ProductServiceException {
		// TODO Auto-generated method stub
		Optional<Product> optProduct = productRepository.findById(prodId);
		Product product = optProduct.orElseThrow(()-> new ProductServiceException("Service.PRODUCT_DOES_NOT_EXISTS"));
		if(product.getStock()>=quantity) {
			product.setStock(product.getStock()-quantity);
			return true;
		}
		return false;	
	}


	

}
