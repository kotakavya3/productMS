package com.infosys.ProductService.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProductDTO {
	private Integer prodId;
	@Pattern(regexp="([A-Za-z]+([ ][A-Za-z]+)*){1,100}",message="{Validator.VALIDATE_NAME}")
	private String productName;
	@Size(min=200,message="{Validator.VALIDATE_PRICE}")
	private Double price;
	@Size(min=10,message="{Validator.VALIDATE_STOCK}")
	private Integer stock;
	@Pattern(regexp="([A-Za-z]+([ ][A-Za-z]+)*){1,500}",message="{Validator.VALIDATE_DESCRIPTION}")
	private String description;
	@Pattern(regexp="[A-Za-z]+[\\.](png|jpeg)",message="{Validator.VALIDATE_IMAGE}")
	private String image;
	private String category;
	private Integer sellerId;
	private String subCategory;
	@Size(max=5,min=1,message="{Validator.Validate_productRating}")
	private Integer productRating;
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public Integer getProductRating() {
		return productRating;
	}
	public void setProductRating(Integer productRating) {
		this.productRating = productRating;
	}
	
}
