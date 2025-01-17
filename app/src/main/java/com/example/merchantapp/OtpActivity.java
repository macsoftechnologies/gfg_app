package com.example.merchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
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

public class OtpActivity extends AppCompatActivity {
    private TextInputLayout mobilleno;
    private TextView submitotp,signupp;
    private ImageView cart;
    private String mobilenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mobilleno = findViewById(R.id.number_otp);
        submitotp = findViewById(R.id.otp_submit);
        cart = findViewById(R.id.gfgimg);
        signupp = findViewById(R.id.sign_up);
        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gettext();
            }
        });

        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this,LoginOrRegisterActivity.class);
                startActivity(intent);

            }
        });

        ObjectAnimator moveAnim = ObjectAnimator.ofFloat(cart, "translationX", -50f, 50f);
        moveAnim.setDuration(1000); // Duration of one cycle (1 second)
        moveAnim.setRepeatMode(ObjectAnimator.REVERSE); // Reverse the direction after each cycle
        moveAnim.setRepeatCount(ObjectAnimator.INFINITE); // Repeat indefinitely

        // Start the animation
        moveAnim.start();

    }




    private void Gettext() {
        mobilenumber = mobilleno.getEditText().getText().toString().trim();


        if(mobilenumber.isEmpty()){
            mobilleno.setError("Email  or number cannot be empty");
            return;
        }


        otpinapi();
    }




    private void otpinapi() {
        Map<String,String> body = new HashMap<>();
        body.put("mobileNumber",mobilenumber);

        ApiClient.getService().otplogin(body).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()) {
                    OtpResponse otpResponse = response.body();
                    if (otpResponse.getStatusCode() == 200) {

                        Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                        intent.putExtra("number", mobilenumber);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OtpActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}