package com.infosys.ProductService.dto;

public class SubscribedProductDTO {
	private Integer prodId;
	private Integer BuyerId;
	private Integer quantity;
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public Integer getBuyerId() {
		return BuyerId;
	}
	public void setBuyerId(Integer buyerId) {
		BuyerId = buyerId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
