package com.infy.order.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infy.order.utility.PrimaryKeys;

@Entity
@Table(name = "products_ordered")
public class ProductsOrdered {
	
	@EmbeddedId
	private PrimaryKeys primaryKeys;
	private Integer sellerId;
	private Integer quantity;
	
	public PrimaryKeys getPrimaryKeys() {
		return primaryKeys;
	}
	public void setPrimaryKeys(PrimaryKeys primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
