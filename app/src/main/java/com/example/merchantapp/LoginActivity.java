package com.example.merchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.Model.LoginOtpModel.Coordinates;
import com.example.merchantapp.Model.LoginOtpModel.Data;
import com.example.merchantapp.Model.LoginOtpModel.LoginOtpResponse;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView Login,signup_text;
    String mobilenumber;
    EditText number_otp;
    private ImageView cart;
    String email_text,password_text;
    TextInputLayout email,password;
    private  String token,id,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("OTP");
        Intent intent = getIntent();
        if(intent != null){
            mobilenumber = intent.getStringExtra("number");
        }


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      // cart = findViewById(R.id.gfgimg);
        Login = findViewById(R.id.login);
       // email = findViewById(R.id.email_login);
       number_otp =findViewById(R.id.password_login);
      //  signup_text = findViewById(R.id.sign_up);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              GetTextFromFields();

            }
        });

//        signup_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent intent = new Intent(LoginActivity.this,LoginOrRegisterActivity.class);
//               startActivity(intent);
//
//            }
//        });
//
//        ObjectAnimator moveAnim = ObjectAnimator.ofFloat(cart, "translationX", -50f, 50f);
//        moveAnim.setDuration(1000); // Duration of one cycle (1 second)
//        moveAnim.setRepeatMode(ObjectAnimator.REVERSE); // Reverse the direction after each cycle
//        moveAnim.setRepeatCount(ObjectAnimator.INFINITE); // Repeat indefinitely
//
//        // Start the animation
//        moveAnim.start();

    }

    private void GetTextFromFields() {
        // email_text = email.getEditText().getText().toString().trim();
         password_text =  number_otp.getText().toString();


        if(password_text.isEmpty()){
            password.setError("otp cannot be empty");
            return;
        }

       Loginapi();

    }

    private void Loginapi(){

        Map<String,String> body = new HashMap<>();
        body.put("mobileNumber",mobilenumber);
        body.put("otp",password_text);

        ApiClient.getService().login(body).enqueue(new Callback<LoginOtpResponse>() {
            @Override
            public void onResponse(Call<LoginOtpResponse> call, Response<LoginOtpResponse> response) {
                if(response.isSuccessful()){
                    LoginOtpResponse loginResponse = response.body();
                    Data data = loginResponse.getData();
                    SharedPreferences sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String value = new Gson().toJson(data);
                    editor.putString("value", value);
                    editor.apply();

                    Coordinates coordinates = data.getCoordinates();
                    Double longitude = (Double) coordinates.getCoordinates().get(0);
                    Double latitude = (Double) coordinates.getCoordinates().get(1);

                    // Convert to String
                    String latitudeString = String.valueOf(latitude);
                    String longitudeString = String.valueOf(longitude);

                UserSessionManagement.getInstance(getApplicationContext()).setLatitude(latitudeString);
                    UserSessionManagement.getInstance(getApplicationContext()).setLongitude(longitudeString);

                 //   String lat =    UserSessionManagement.getInstance(getApplicationContext()).getLatitude();

                   // Toast.makeText(LoginActivity.this,"lat"+lat,Toast.LENGTH_LONG).show();

                    token =loginResponse.getToken();
                     id = loginResponse.getData().getId();
                     userid = loginResponse.getData().getUserId();
                     String phno = loginResponse.getData().getMobileNumber();

                     String lg = loginResponse.getData().getCoordinates().getType();
                    UserSessionManagement.getInstance(getApplicationContext()).setUserid(id);
                     UserSessionManagement.getInstance(getApplicationContext()).setUserids(userid);
                    UserSessionManagement.getInstance(getApplicationContext()).setTokenId(token);
                    UserSessionManagement.getInstance(getApplicationContext()).setPhno(phno);

                 //   userProfile();

                    //ld.commit();
                    boolean isCustomer = false;
                    boolean isMerchant = false;
                    for (String role : data.getRole()) {
                        if (role.equals("customer")) {
                            isCustomer = true;

                        }
                        else if(role.equals("merchant")){
                            isMerchant = true;
                        }
                    }

                    Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
                    intent.putExtra("openHomeFragment", true); // Optionally pass a flag to open a specific fragment

                    if (isCustomer && isMerchant) {
                        UserSessionManagement.saveBoolean(LoginActivity.this, "LOGIN", true);
                        UserSessionManagement.saveeBoolean(LoginActivity.this, "MERCHANTLOGIN", true);
                        startActivity(intent);
                        finish(); // Finish the LoginActivity to prevent navigating back to it
                    } else if (isCustomer) {
                        UserSessionManagement.saveBoolean(LoginActivity.this, "LOGIN", true);
                        Toast.makeText(LoginActivity.this, "Logged in as customer", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish(); // Finish the LoginActivity
                    } else if (isMerchant) {
                        UserSessionManagement.saveeBoolean(LoginActivity.this, "MERCHANTLOGIN", true);
                        Toast.makeText(LoginActivity.this, "Logged in as merchant", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish(); // Finish the LoginActivity
                    } else {
                        Toast.makeText(LoginActivity.this, "Password or email is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

//                    if(isCustomer && isMerchant){
//                        UserSessionManagement.saveBoolean(LoginActivity.this, "LOGIN", true);
//                        UserSessionManagement.saveeBoolean(LoginActivity.this, "MERCHANTLOGIN", true);
//                        Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
//                        intent.putExtra("openHomeFragment", true);
//                        startActivity(intent);
//
//                    }
//
//                    else if (isCustomer) {
//                        UserSessionManagement.saveBoolean(LoginActivity.this, "LOGIN", true);
//                        Toast.makeText(LoginActivity.this, "Logged in as customer", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
//                        intent.putExtra("openHomeFragment", true);
//                        startActivity(intent);
//
//
//                    }
//                    else if(isMerchant){
//                        UserSessionManagement.saveeBoolean(LoginActivity.this, "MERCHANTLOGIN", true);
//                        Toast.makeText(LoginActivity.this, "Logged in as mercahnt", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this,CustomerMainActivity.class);
////                        intent.putExtra("openMerchantHomeFragment", true);
//                        intent.putExtra("openHomeFragment", true);
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(LoginActivity.this,"Password or email is wrong",Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//                  //  Toast.makeText(LoginActivity.this,"toekm"+loginData.getMobileNumber(),Toast.LENGTH_SHORT).show();
//
//
//
//////                    LoginModel loginModel = response.body();
////                    String token = loginModel.getToken();
////                    Toast.makeText(LoginActivity.this,"toekm"+token,Toast.LENGTH_SHORT).show();
//
//                    }
//                }






            @Override
            public void onFailure(Call<LoginOtpResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this," "+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
//    private void  Loginapi(){
//        Map<String,String> body = new HashMap<>();
//        body.put("email",email_text);
//        body.put("password",password_text);
//
//
//        ApiClient.getService().login(body).enqueue(new Callback<LoginModel>() {
//            @Override
//            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
//                if (response.isSuccessful()) {
//                    LoginModel loginModel = response.body();
//                   // UserSessionManagement.getInstance(getApplicationContext()).setLoginModel(loginModel);
//
//
//                    LoginData loginData = loginModel.getData();
////                    SharedPreferences sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE);
////                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                    String value = new Gson().toJson(loginData);
////                    editor.putString("value", value);
////                    editor.apply();
//
//
//                    token =loginModel.getToken();
//                     id = loginModel.getData().get_id();
//                     userid = loginModel.getData().getUserId();
//
//                     String phno = loginModel.getData().getMobileNumber();
//                    UserSessionManagement.getInstance(getApplicationContext()).setUserid(id);
//                     UserSessionManagement.getInstance(getApplicationContext()).setUserids(userid);
//                    UserSessionManagement.getInstance(getApplicationContext()).setTokenId(token);
//                    UserSessionManagement.getInstance(getApplicationContext()).setPhno(phno);
//
//                    userProfile();
//
//                    //ld.commit();
//                    boolean isCustomer = false;
//                    boolean isMerchant = false;
//                    for (String role : loginData.getRole()) {
//                        if (role.equals("customer")) {
//                            isCustomer = true;
//
//                        }
//                        else if(role.equals("merchant")){
//                            isMerchant = true;
//                        }
//                    }
//
//                    if(isCustomer && isMerchant){
//                        UserSessionManagement.saveBoolean(LoginActivity.this, "LOGIN", true);
//                        UserSessionManagement.saveeBoolean(LoginActivity.this, "MERCHANTLOGIN", true);
//                        Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
//                        intent.putExtra("openHomeFragment", true);
//                        startActivity(intent);
//
//                    }
//
//                    else if (isCustomer) {
//                        UserSessionManagement.saveBoolean(LoginActivity.this, "LOGIN", true);
//                        Toast.makeText(LoginActivity.this, "Logged in as customer", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
//                        intent.putExtra("openHomeFragment", true);
//                        startActivity(intent);
//
//
//                    }
//                    else if(isMerchant){
//                        UserSessionManagement.saveeBoolean(LoginActivity.this, "MERCHANTLOGIN", true);
//                        Toast.makeText(LoginActivity.this, "Logged in as mercahnt", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this,CustomerMainActivity.class);
////                        intent.putExtra("openMerchantHomeFragment", true);
//                        intent.putExtra("openHomeFragment", true);
//                        startActivity(intent);
//                    }
//
//
//
//                  //  Toast.makeText(LoginActivity.this,"toekm"+loginData.getMobileNumber(),Toast.LENGTH_SHORT).show();
//
//
//
//////                    LoginModel loginModel = response.body();
////                    String token = loginModel.getToken();
////                    Toast.makeText(LoginActivity.this,"toekm"+token,Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//            @Override
//            public void onFailure(Call<LoginModel> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//
//    }
    private void userProfile(){

        Map<String,String> body = new HashMap<>();
        body.put("_id",id);

        ApiClient.getService().userid("Bearer "+token,body).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){


                    UserModel userModel = response.body();
                    com.example.merchantapp.Model.UserModels.Data userData = userModel.getData();
                    SharedPreferences sharedPreferences = getSharedPreferences("mykey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String value = new Gson().toJson(userData);
                    editor.putString("value", value);
                    editor.apply();

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"unscessfull user ",Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();



    }
    @Override
    public void onBackPressed() {
        // Finish the activity when back is pressed
        finish();
    }

}