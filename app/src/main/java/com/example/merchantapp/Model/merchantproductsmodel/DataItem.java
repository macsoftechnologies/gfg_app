package com.example.merchantapp.Model.merchantproductsmodel;

import java.util.List;

public class DataItem{
	private String createdAt;
	private int price;
	private int v;
	private String _id;
	private List<AdminProductIdItem> adminProductId;
	private String userId;
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getPrice(){
		return price;
	}

	public int getV(){
		return v;
	}

	public String getId(){
		return _id;
	}

	public List<AdminProductIdItem> getAdminProductId(){
		return adminProductId;
	}

	public String getUserId(){
		return userId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}