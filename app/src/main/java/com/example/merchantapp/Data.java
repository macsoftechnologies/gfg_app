package com.example.merchantapp;

public class Data{
	private String createdAt;
	private String productImage;
	private int v;
	private ProductSpecifications productSpecifications;
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

	public ProductSpecifications getProductSpecifications(){
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
