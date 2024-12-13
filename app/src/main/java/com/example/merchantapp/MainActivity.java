package com.example.merchantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.merchantapp.Utilites.UserSessionManagement;

public class MainActivity extends AppCompatActivity {

    private ImageView splashimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        splashimg = findViewById(R.id.splashimg);

     //   splashimg = findViewById(R.id.splash_image);

        // Start the animation to fade out the image after 1000 milliseconds (1 second)
        splashimg.animate()
                .alpha(0.0f)
                .setDuration(1000)
                .withEndAction(runnable)
                .start();
    }

    // This Runnable is used to control the sequence of animations
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            splashimg.setVisibility(View.VISIBLE);
            // After the image fades out, start the animation to fade it back in
            splashimg.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .withEndAction(new Runnable()

                    {
                        @Override
                        public void run() {
                            // After the image fades in, start the animation to shrink it
                            splashimg.animate()
                                    .scaleX(0.1f)
                                    .scaleY(0.1f)
                                    .setDuration(1000)
                                    .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            // After the image shrinks, start the new activity
//                                            Intent intent = new Intent(MainActivity.this,LoginOrRegisterActivity.class);
//                                            startActivity(intent);
//                                            // Finish the current activity to prevent going back to it
//                                            finish();
                                            movetonext();
                                        }
                                    })
                                    .start();
                        }
                    })
                    .start();
        }
    };

    private void movetonext() {
        boolean isLoginSucces = UserSessionManagement.getBoolean(this, "LOGIN");
        boolean isMerchantLoginSucces = UserSessionManagement.gettBoolean(this,"MERCHANTLOGIN");
        if (isLoginSucces) {
            Intent intent = new Intent(MainActivity.this, CustomerMainActivity.class);
            intent.putExtra("openHomeFragment", true);
//            Intent intent = new Intent(SplashScreenActivity.this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();

        }
        else if(isMerchantLoginSucces) {
            Intent intent = new Intent(MainActivity.this, CustomerMainActivity.class);
            intent.putExtra("openHomeFragment", true);
//            Intent intent = new Intent(SplashScreenActivity.this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();

        }
        else {
            Intent intent = new Intent(MainActivity.this, LoginOrRegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    }

