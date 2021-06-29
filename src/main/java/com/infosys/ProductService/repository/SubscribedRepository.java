package com.infosys.ProductService.repository;

import org.springframework.data.repository.CrudRepository;

import com.infosys.ProductService.entity.SubscribedProduct;


public interface SubscribedRepository extends CrudRepository<SubscribedProduct,Integer> {

}
