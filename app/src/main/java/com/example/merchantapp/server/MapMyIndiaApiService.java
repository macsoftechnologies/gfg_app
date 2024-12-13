package com.example.merchantapp.server;

import com.example.merchantapp.Model.GoogleAPiModel.GeocodeResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapMyIndiaApiService {
    @GET("geocode/json")
    Call<GeocodeResponse> getGeocode(
            @Query("latlng") String latlng,
            @Query("key") String apiKey
    );
}
