package com.example.merchantapp.Model;


public class LoginModel
{
    private LoginData data;

    private String message;

    private String statusCode;

    private String token;

    public LoginData getData ()
    {
        return data;
    }

    public void setData (LoginData data)
    {
        this.data = data;
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

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", message = "+message+", statusCode = "+statusCode+", token = "+token+"]";
    }
}
