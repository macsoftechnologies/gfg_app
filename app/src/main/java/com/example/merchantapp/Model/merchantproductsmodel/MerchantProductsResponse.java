package com.example.merchantapp.Model.merchantproductsmodel;

import java.util.List;

public class MerchantProductsResponse{
	private List<DataItem> data;
	private String message;
	private int statusCode;

	public List<DataItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatusCode(){
		return statusCode;
	}
}