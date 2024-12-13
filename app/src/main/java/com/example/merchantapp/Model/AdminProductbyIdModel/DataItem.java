package com.example.merchantapp.Model.AdminProductbyIdModel;

import java.util.List;
import java.util.Map;

public class DataItem{
	private String createdAt;
	private String productImage;
	private int v;
	private Map<String, String> productSpecifications;
	private String id;
	private String adminProductId;
	private String productName;
	private List<CategoryIdItem> categoryId;
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getProductImage(){
		return productImage;
	}

	public int getV(){
		return v;
	}

	public Map<String, String> getProductSpecifications() { return productSpecifications; }
	public void setProductSpecifications(Map<String, String> productSpecifications) { this.productSpecifications = productSpecifications; }

	public String getId(){
		return id;
	}

	public String getAdminProductId(){
		return adminProductId;
	}

	public String getProductName(){
		return productName;
	}

	public List<CategoryIdItem> getCategoryId(){
		return categoryId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}