package com.example.merchantapp.Model;

public class CustomerRegistrationModel {


    private UserRegisterData userRegisterData;

    private String message;

    private int statusCode;

    public UserRegisterData getUserRegisterData()
    {
        return userRegisterData;
    }

    public void setUserRegisterData (UserRegisterData userRegisterData)
    {
        this.userRegisterData = userRegisterData;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public int getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (int statusCode)
    {
        this.statusCode = statusCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+userRegisterData+", message = "+message+", statusCode = "+statusCode+"]";
    }
}
		