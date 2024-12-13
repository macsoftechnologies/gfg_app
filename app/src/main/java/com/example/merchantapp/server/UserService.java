package com.example.merchantapp.server;

import com.example.merchantapp.Model.AddProductModel.AddProductResponse;
import com.example.merchantapp.Model.AdminProductbyIdModel.ProductResponse;
import com.example.merchantapp.Model.AdvertismentsModel.AdvertisementsResponse;

import com.example.merchantapp.Model.CategoriesModel.CategoriesResponse;
import com.example.merchantapp.Model.CustomerRegistrationModel;
import com.example.merchantapp.Model.CustomerSearchProductsModel.CustomerSearchProductsResponse;
import com.example.merchantapp.Model.DeleteProductModel.DeleteProductResponse;
import com.example.merchantapp.Model.EditProductModel.EditProductResponse;
import com.example.merchantapp.Model.LoginOtpModel.LoginOtpResponse;
import com.example.merchantapp.Model.MerchantRegisterModel.MerchantRegistrationModel;
import com.example.merchantapp.Model.Otpmodel.OtpResponse;
import com.example.merchantapp.Model.ProductbhyIdModel.ProductByIdResponse;
import com.example.merchantapp.Model.Products.ProductsModel;
import com.example.merchantapp.Model.UpadteModels.UpdateModel;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.Model.merchantproductsmodel.MerchantProductsResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UserService {


    @Multipart
    @POST("user/customerregister")
    Call<CustomerRegistrationModel> register(@PartMap Map<String, RequestBody> fields, @Part MultipartBody.Part profileImage);

    @Multipart
    @POST("user/merchantregister")
        Call<MerchantRegistrationModel> merchantregister(@PartMap Map<String,RequestBody> body,@Part MultipartBody.Part profileImage, @Part MultipartBody.Part shopImage);

    @POST("user/loginuser")
    Call<LoginOtpResponse> login(@Body Map<String,String> body);

    @POST("user/sendotp")
    Call<OtpResponse> otplogin(@Body Map<String,String> body);

    @GET("product/getcatogerieslist")

    Call<CategoriesResponse> getCategorys();


    @Multipart
    @POST("user/updateuser")
    Call<UpdateModel> update(@Header("Authorization") String token,
                             @PartMap Map<String, RequestBody> bodyFields,
                             @Part MultipartBody.Part profileImage,@Part MultipartBody.Part shopImage);


//    @POST("user/updateuser")
//    Call<UpdateModel> update(@Header("Authorization") String token,@Body Map<String,String> body);

    @POST("user/getuserbyid")
    Call<UserModel> userid(@Header("Authorization") String token, @Body Map<String,String> body);

    @GET("product/getproductslist")
    Call<ProductsModel> getProducts(@Header("Authorization") String token);

    @POST("product/getproductbyid")
    Call<ProductResponse> getProductById(@Header("Authorization") String token, @Body Map<String,String> body);

    @Multipart
    @POST("user/switchuser")
    Call<ResponseBody>  switchUser(@Header("Authorization") String token ,@PartMap Map<String,RequestBody> body,@Part MultipartBody.Part profileImage);


    @POST("product/getmerchantproducts")
    Call<MerchantProductsResponse> merchantuserId(@Header("Authorization") String token,@Body Map<String,String> body);

     @POST("product/addmerchantproduct")
    Call<AddProductResponse> addproductPrice(@Header("Authorization") String token,@Body Map<String,String> body);

     @POST("advertisements/getadsbylocation")
    Call<AdvertisementsResponse> getAdvertisments(@Header("Authorization") String token, @Body Map<String,String> body);

     @POST("product/searchproducts")
     Call<CustomerSearchProductsResponse> searchProducts(@Header("Authorization") String token, @Body Map<String,String> body);

     @POST("product/editmerchantproduct")
    Call<EditProductResponse> editMerchantProducts(@Header("Authorization") String token ,@Body Map<String,String> body);

     @POST("product/getmerchantproductbyid")
     Call<ProductByIdResponse> productbYId(@Header("Authorization") String token , @Body Map<String,String> body);

     @POST("product/deletemerchantproduct")
    Call<DeleteProductResponse> deleteProduct(@Header("Authorization") String token , @Body Map<String,String> body);

}
