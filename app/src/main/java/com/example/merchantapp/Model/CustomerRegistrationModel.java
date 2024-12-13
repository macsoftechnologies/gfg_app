package com.example.merchantapp.Model;

public class CustomerRegistrationModel {


    private UserRegisterData userRegisterData;

    private String message;

    private String statusCode;

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

    public String getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (String statusCode)
    {
        this.statusCode = statusCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+userRegisterData+", message = "+message+", statusCode = "+statusCode+"]";
    }
}
		