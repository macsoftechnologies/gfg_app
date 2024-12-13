package com.example.merchantapp.Model.AddProductModel;

public class AdminProductIdItem{
	private String createdAt;
	private String productImage;
	private int v;
	private ProductSpecifications productSpecifications;
	private String id;
	private String adminProductId;
	private String productName;
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
	}

	public void setV(int v){
		this.v = v;
	}

	public int getV(){
		return v;
	}

	public void setProductSpecifications(ProductSpecifications productSpecifications){
		this.productSpecifications = productSpecifications;
	}

	public ProductSpecifications getProductSpecifications(){
		return productSpecifications;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAdminProductId(String adminProductId){
		this.adminProductId = adminProductId;
	}

	public String getAdminProductId(){
		return adminProductId;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}
