package com.example.merchantapp.Model.merchantproductsmodel;

import java.util.Map;

public class AdminProductIdItem{
	private String createdAt;
	private String productImage;
	private int v;
	private Map<String, String> productSpecifications;
	private String id;
	private String adminProductId;
	private String productName;
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

	public Map<String, String> getProductSpecifications(){
		return productSpecifications;
	}

	public String getId(){
		return id;
	}

	public String getAdminProductId(){
		return adminProductId;
	}

	public String getProductName(){
		return productName;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}
