package com.example.merchantapp.server;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    private static final int CONNECT_TIMEOUT = 30; // in seconds
//    private static final int READ_TIMEOUT = 30; // in seconds
//    private static final int WRITE_TIMEOUT = 30; // in seconds
//
//
//    public static Retrofit getRetrofit() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .build();
//
//        // Create a Gson instance with custom configurations
//        Gson gson = new GsonBuilder()
//                // Add custom Gson configurations here if needed
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson)) // Pass the custom Gson instance here
//                .baseUrl("http://52.66.197.248:5000/")
//                .client(okHttpClient)
//                .build();
//
//        return retrofit;
//
//
//
//    }
//
//    public static UserService getService(){
//        UserService userService = getRetrofit().create(UserService.class);
//        return userService;
//    }
private static final int CONNECT_TIMEOUT = 30; // in seconds
    private static final int READ_TIMEOUT = 30; // in seconds
    private static final int WRITE_TIMEOUT = 30; // in seconds

    private static Retrofit retrofit;
    private static Retrofit geocodeRetrofit;

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                // Add custom Gson configurations here if needed
                .create();
    }

    // Retrofit instance for the primary base URL
    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .baseUrl("https://api.gfg.org.in/")
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    // Retrofit instance for the MapmyIndia base URL
    private static Retrofit getGeocodeRetrofit() {
        if (geocodeRetrofit == null) {
            geocodeRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .baseUrl("https://maps.googleapis.com/maps/api/")
                    .client(getOkHttpClient())
                    .build();
        }
        return geocodeRetrofit;
    }

    // Service for the primary base URL
    public static UserService getService(){
        return getRetrofit().create(UserService.class);
    }

    // Service for the MapmyIndia base URL
    public static MapMyIndiaApiService getGeocodeService() {
        return getGeocodeRetrofit().create(MapMyIndiaApiService.class);
    }

}
