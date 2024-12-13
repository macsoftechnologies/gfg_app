package com.example.merchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.Model.Otpmodel.OtpResponse;
import com.example.merchantapp.server.ApiClient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOtpActivity extends AppCompatActivity {
    private TextView loginn,signup_text;
    private ImageView cart;
    String email_text;
   private TextInputLayout email;
    private  String token,id,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("LOGIN");


//        email = findViewById(R.id.email_login_number_otp);
//       loginn  = findViewById(R.id.login_submit);
//       // signup_text = findViewById(R.id.sign_up);
//
////        loginn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//
//
//
//
//    }
//
//    private void GetTextfromFields() {
//
//        email_text = email.getEditText().getText().toString().trim();
//
//
//        if(email_text.isEmpty()){
//            email.setError("Email  or number cannot be empty");
//            return;
//        }
//
//
//        otpinapi();
//    }
//
//    private void otpinapi() {
//        Map<String,String> body = new HashMap<>();
//        body.put("mobileNumber",email_text);
//
//        ApiClient.getService().otplogin(body).enqueue(new Callback<OtpResponse>() {
//            @Override
//            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
//                if(response.isSuccessful()){
//                   Intent intent = new Intent(SendOtpActivity.this,LoginActivity.class);
//                   intent.putExtra("number",email_text);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OtpResponse> call, Throwable t) {
//                Toast.makeText(SendOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
    }
}