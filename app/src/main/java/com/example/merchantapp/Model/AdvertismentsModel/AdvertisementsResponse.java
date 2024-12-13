package com.example.merchantapp.Model.AdvertismentsModel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AdvertisementsResponse {
    @SerializedName("statusCode")
    private int statusCode;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<String> images;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getImages() {
        return images;
    }
}
