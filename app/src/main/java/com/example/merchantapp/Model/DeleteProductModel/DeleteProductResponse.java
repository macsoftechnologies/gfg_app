package com.example.merchantapp.Model.DeleteProductModel;

public class DeleteProductResponse{
	private Data data;
	private String message;
	private int statusCode;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatusCode(){
		return statusCode;
	}
}
