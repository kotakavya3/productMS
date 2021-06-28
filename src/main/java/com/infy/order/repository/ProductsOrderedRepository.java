package com.infy.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.order.entity.ProductsOrdered;
import com.infy.order.utility.PrimaryKeys;

public interface ProductsOrderedRepository extends JpaRepository<ProductsOrdered, PrimaryKeys>{
	
	
	
}
