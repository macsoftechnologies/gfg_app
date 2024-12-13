package com.example.merchantapp.Model.MerchantRegisterModel;

public class MerchantRegistrationModel {
    private long statusCode;
    private String message;
    private MerchantData data;

    public long getStatusCode() { return statusCode; }
    public void setStatusCode(long value) { this.statusCode = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public MerchantData getData() { return data; }
    public void setData(MerchantData value) { this.data = value; }
}