package com.example.merchantapp.Model.MerchantRegisterModel;

public class MerchantData {
    private String userName;
    private String email;
    private String password;
    private String address;
    private String mobileNumber;
    private String altMobileNumber;
    private String shopName;
    private String shopLocation;
    private String[] role;
    private String id;


    public String getUserName() { return userName; }
    public void setUserName(String value) { this.userName = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String value) { this.mobileNumber = value; }

    public String getAltMobileNumber() { return altMobileNumber; }
    public void setAltMobileNumber(String value) { this.altMobileNumber = value; }

    public String getShopName() { return shopName; }
    public void setShopName(String value) { this.shopName = value; }

    public String getShopLocation() { return shopLocation; }
    public void setShopLocation(String value) { this.shopLocation = value; }

    public String[] getRole() { return role; }
    public void setRole(String[] value) { this.role = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }


}