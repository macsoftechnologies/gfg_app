package com.example.merchantapp.Model.LoginOtpModel;

import java.util.List;

public class Data{
	private String shopLicense;
	private String address;
	private String shopLocation;
	private List<String> role;
	private String mobileNumber;
	private String shopImage;
	private Coordinates coordinates;
	private String shopName;
	private String profileImage;
	private String userName;
	private String userId;
	private String createdAt;
	//private String password;
	private int v;
	//private String altMobileNumber;
	private String _id;
//	private String email;
	private String updatedAt;

	public String getShopLicense(){
		return shopLicense;
	}

	public String getAddress(){
		return address;
	}

	public String getShopLocation(){
		return shopLocation;
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

//	public String getPassword(){
//		return password;
//	}

	public int getV(){
		return v;
	}

//	//public String getAltMobileNumber(){
//		return altMobileNumber;
//	}

	public String getId(){
		return _id;
	}

//	public String getEmail(){
//		return email;
//	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}