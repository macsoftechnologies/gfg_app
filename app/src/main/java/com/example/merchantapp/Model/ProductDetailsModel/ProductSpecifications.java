package com.example.merchantapp.Model.ProductDetailsModel;

import java.util.HashMap;
import java.util.Map;

public class ProductSpecifications
{
    private String Brand;


    public String getBrand ()
    {
        return Brand;
    }

    public void setBrand (String Brand)
    {
        this.Brand = Brand;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [Brand = "+Brand+"]";
    }



}