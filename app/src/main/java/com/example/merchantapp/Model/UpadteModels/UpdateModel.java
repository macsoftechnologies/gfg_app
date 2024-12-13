package com.example.merchantapp.Model.UpadteModels;

public class UpdateModel
{
    private UpdateData data;

    private String message;

    private String statusCode;

    public UpdateData getData ()
    {
        return data;
    }

    public void setData (UpdateData data)
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

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", message = "+message+", statusCode = "+statusCode+"]";
    }
}
		