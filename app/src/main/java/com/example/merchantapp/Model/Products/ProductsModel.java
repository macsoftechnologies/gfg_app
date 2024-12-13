package com.example.merchantapp.Model.Products;

public class ProductsModel
{
    private ProductData[] data;

    private String message;

    private String statusCode;

    public ProductData[] getData ()
    {
        return data;
    }

    public void setData (ProductData[] data)
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
		