package com.example.merchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.merchantapp.Fragments.TypeOfRegistrationFragment;

public class LoginOrRegisterActivity extends AppCompatActivity {

    private TextView Login,Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

       getSupportActionBar().hide();


        Login = findViewById(R.id.logintype);
        Register = findViewById(R.id.registertype);
        Login.setClickable(true);

//        if(Login.isClickable()){
//            startActivity(new Intent(LoginOrRegisterActivity.this,LoginActivity.class));
//        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginOrRegisterActivity.this,OtpActivity.class));

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showdailog();
              //  startActivity(new Intent(LoginOrRegisterActivity.this,RegisterActivity.class));

            }
        });

    }
    private void showdailog(){

        TypeOfRegistrationFragment dailogFragment = new TypeOfRegistrationFragment();
        dailogFragment.show(getSupportFragmentManager(),"typesofRegistration");
    }

//    @Override
//    public void onBackPressed() {
//        // Finish the activity when back is pressed
//        finish();
//    }
}