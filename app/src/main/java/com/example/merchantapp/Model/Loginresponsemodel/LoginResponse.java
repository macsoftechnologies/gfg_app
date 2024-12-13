package com.example.merchantapp.Model.Loginresponsemodel;

public class LoginResponse{
	private Data data;
	private String message;
	private int statusCode;
	private String token;

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public String getToken(){
		return token;
	}
}
