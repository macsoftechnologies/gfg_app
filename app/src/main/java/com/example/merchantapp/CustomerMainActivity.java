package com.example.merchantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.merchantapp.Fragments.AddProductFragment;
import com.example.merchantapp.Fragments.HelpFragment;
import com.example.merchantapp.Fragments.HomeFragment;
import com.example.merchantapp.Fragments.OrdersFragment;
import com.example.merchantapp.Fragments.ProfileFragment;
import com.example.merchantapp.server.OnBackPressedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerMainActivity extends AppCompatActivity   {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.customer_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        bottomNavigationView.setItemIconTintList(null);


//        if (getIntent() != null && getIntent().hasExtra("openHomeFragment")) {
//            loadFragment(new HomeFragment());
//
//
//
//            bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//        }

        if (getIntent() != null && getIntent().hasExtra("openHomeFragment")) {
            // Only load the HomeFragment if it's not already loaded
             Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.customer_fragments_container);
//
          if (!(currentFragment instanceof HomeFragment)) {
                loadFragment(new HomeFragment());
                bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
            }
        }

    }

    public void setHomeIconColor(String colorHex){
        BottomNavigationView bottomNavigationView = findViewById(R.id.customer_bottom_navigation_view);
        if (bottomNavigationView != null) {
            bottomNavigationView.setItemIconTintList(createColorStateList(colorHex));
            bottomNavigationView.setSelectedItemId(R.id.cust_home); // Select the profile item
        }

    }
    public void setOrderIconColor(String colorHex){
        BottomNavigationView bottomNavigationView = findViewById(R.id.customer_bottom_navigation_view);
        if (bottomNavigationView != null) {
            bottomNavigationView.setItemIconTintList(createColorStateList(colorHex));
            bottomNavigationView.setSelectedItemId(R.id.cust_orders); // Select the profile item
        }

    }

    public void setProfileIconColor(String colorHex) {
        // Access the BottomNavigationView and set the profile icon color
        BottomNavigationView bottomNavigationView = findViewById(R.id.customer_bottom_navigation_view);
        if (bottomNavigationView != null) {
            bottomNavigationView.setItemIconTintList(createColorStateList(colorHex));
            bottomNavigationView.setSelectedItemId(R.id.cust_profile); // Select the profile item
        }
    }

    // Method to create a ColorStateList for selected and default state colors




    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cust_home:
                    loadFragment(new HomeFragment());
                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
                 //   bottomNavigationView.getMenu().findItem(R.id.cust_home).setIcon(R.drawable.ic_baseline_home_24);  // Change 'ic_home' to your icon


                    return true;
                case R.id.cust_orders:
                    loadFragment(new OrdersFragment());
                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
                    return true;

//                case R.id.Add_products:
//                    loadFragment(new AddProductFragment());
//                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//                    return true;
//                case R.id.cust_help:
//                    loadFragment(new HelpFragment());
//                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//                    return true;

                case R.id.cust_profile:
                    loadFragment(new ProfileFragment());
                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
                    return true;
                default:
                    return false;
            }
        }
    };






    private ColorStateList createColorStateList(String colorString) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked}, // enabled
                new int[]{-android.R.attr.state_checked}, // disabled
        };

        int[] colors = new int[]{
                Color.parseColor(colorString),
                Color.GRAY,
        };

        return new ColorStateList(states, colors);
    }


    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.customer_fragments_container);

        if (currentFragment instanceof HomeFragment) {
            // If already on HomeFragment, close the app
            finishAffinity();  // Closes the app completely
        } else {
            // Clear the back stack and navigate back to HomeFragment
         //  bottomNavigationView.setSelectedItemId(R.id.cust_home);
//            bottomNavigationView.getMenu().findItem(R.id.cust_home).setIcon(R.drawable.ic_baseline_home_24);  // Change 'ic_home' to your icon
         //   Toast.makeText(this, "activate", Toast.LENGTH_SHORT).show();

            //  clearAllFragmentsFromBackStack();// Set Home as selected
            loadHomeFragment(); // Load HomeFragment

            // Make sure to not call super.onBackPressed() here, as it would finish the activity
        }
    }







//    }
private void loadFragment(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.customer_fragments_container, fragment);

    // Only add to the back stack if it's NOT HomeFragment
    if (!(fragment instanceof HomeFragment)) {
        clearAllFragmentsFromBackStack();

        transaction.addToBackStack(null);
    }

    transaction.commit();
  }


    private void loadHomeFragment() {
        // Clear the back stack to remove all fragments
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//        bottomNavigationView.getMenu().findItem(R.id.cust_home).setIcon(R.drawable.ic_baseline_home_24);  // Change 'ic_home' to your icon
        bottomNavigationView.setSelectedItemId(R.id.cust_home);
        // Load the HomeFragment without adding it to the back stack
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.customer_fragments_container, new HomeFragment());
        transaction.commit();
    }


    private void clearAllFragmentsFromBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStackImmediate();
        }
        Log.d("homeTrig", fm.getBackStackEntryCount() + "");
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Handle the up button in the action bar
        return true; // Returning true indicates that you've handled the event
    }




}