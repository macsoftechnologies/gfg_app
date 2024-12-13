package com.example.merchantapp.Model.ProductDetailsModel;

public class ProductData
{
    private String createdAt;

    private String productImage;

    private String __v;

    private ProductSpecifications productSpecifications;

    private String _id;

    private UserId[] userId;

    private String productName;

    private String updatedAt;

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getProductImage ()
    {
        return productImage;
    }

    public void setProductImage (String productImage)
    {
        this.productImage = productImage;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public ProductSpecifications getProductSpecifications ()
    {
        return productSpecifications;
    }

    public void setProductSpecifications (ProductSpecifications productSpecifications)
    {
        this.productSpecifications = productSpecifications;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public UserId[] getUserId ()
    {
        return userId;
    }

    public void setUserId (UserId[] userId)
    {
        this.userId = userId;
    }

    public String getProductName ()
    {
        return productName;
    }

    public void setProductName (String productName)
    {
        this.productName = productName;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [createdAt = "+createdAt+", productImage = "+productImage+", __v = "+__v+", productSpecifications = "+productSpecifications+", _id = "+_id+", userId = "+userId+", productName = "+productName+", updatedAt = "+updatedAt+"]";
    }
}
			