package com.example.merchantapp.Model.ProductbhyIdModel;

import java.util.List;

public class DataItem{
	private String createdAt;
	private int price;
	private int v;
	private String id;
	private List<AdminProductIdItem> adminProductId;
	private List<UserIdItem> userId;
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
		return id;
	}

	public List<AdminProductIdItem> getAdminProductId(){
		return adminProductId;
	}

	public List<UserIdItem> getUserId(){
		return userId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}