package com.example.merchantapp;

import java.util.Map;

public class Product {
    private String _id;
    private String productName;
    private Map<String, String> productSpecifications;
    private String productImage;
    private String adminProductId;
    private String createdAt;
    private String updatedAt;
    private int __v;

    // Getters and setters
    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Map<String, String> getProductSpecifications() { return productSpecifications; }
    public void setProductSpecifications(Map<String, String> productSpecifications) { this.productSpecifications = productSpecifications; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public String getAdminProductId() { return adminProductId; }
    public void setAdminProductId(String adminProductId) { this.adminProductId = adminProductId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public int getV() { return __v; }
    public void setV(int __v) { this.__v = __v; }
}
