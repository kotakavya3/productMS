package com.infosys.ProductService.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infosys.ProductService.entity.Product;

public interface ProductRepository extends CrudRepository<Product,Integer>{
	
	public Product findByProdId(Integer id);
	
	public Product findByProductName(String name);
	
	public List<Product> findByCategory(String category);
	
	public List<Product> findAll();

	
}
