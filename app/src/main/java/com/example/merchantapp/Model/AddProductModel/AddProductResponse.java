package com.example.merchantapp.Model.AddProductModel;

import java.util.List;

public class AddProductResponse {
	private List<DataItem> data;
	private String message;
	private int statusCode;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}
}