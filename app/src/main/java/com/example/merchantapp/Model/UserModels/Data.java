package com.example.merchantapp.Model.UserModels;

import java.util.List;

public class Data{
	private String address;
	private List<String> role;
	private String mobileNumber;
	private String shopImage;
	private Coordinates coordinates;
	private String shopName;
	private String otp;
	private String profileImage;
	private String userName;
	private String userId;
	private String createdAt;
	private int v;
	private String id;
	private String updatedAt;

	public String getAddress(){
		return address;
	}

	public List<String> getRole(){
		return role;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public String getShopImage(){
		return shopImage;
	}

	public Coordinates getCoordinates(){
		return coordinates;
	}

	public String getShopName(){
		return shopName;
	}

	public String getOtp(){
		return otp;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public String getUserName(){
		return userName;
	}

	public String getUserId(){
		return userId;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getV(){
		return v;
	}

	public String getId(){
		return id;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}