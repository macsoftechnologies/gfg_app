package com.example.merchantapp.Model.AddProductModel;

import java.util.List;

public class DataItem{
	private String createdAt;
	private int price;
	private int v;
	private String id;
	private List<AdminProductIdItem> adminProductId;
	private String userId;
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setV(int v){
		this.v = v;
	}

	public int getV(){
		return v;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAdminProductId(List<AdminProductIdItem> adminProductId){
		this.adminProductId = adminProductId;
	}

	public List<AdminProductIdItem> getAdminProductId(){
		return adminProductId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}