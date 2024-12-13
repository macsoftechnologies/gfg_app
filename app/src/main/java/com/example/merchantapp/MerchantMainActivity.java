//package com.example.merchantapp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//
//import com.example.merchantapp.Fragments.AddProductFragment;
//import com.example.merchantapp.Fragments.HelpFragment;
//import com.example.merchantapp.Fragments.HomeFragment;
//import com.example.merchantapp.Fragments.MerchantHelpFragment;
//import com.example.merchantapp.Fragments.MerchantHomeFragment;
//import com.example.merchantapp.Fragments.OrdersFragment;
//import com.example.merchantapp.Fragments.ProfileFragment;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class MerchantMainActivity extends AppCompatActivity {
//    private BottomNavigationView bottomNavigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_merchant_main);
//
//        bottomNavigationView = findViewById(R.id.merchant_bottom_navigation_view);
//        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
//        bottomNavigationView.setItemIconTintList(null);
//
//
//        if (getIntent() != null && getIntent().hasExtra("openMerchantHomeFragment")) {
//            loadFragment(new MerchantHomeFragment());
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return super.onSupportNavigateUp();
//    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.merchant_home:
//                    loadFragment(new MerchantHomeFragment());
//                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//                    return true;
//                case R.id.add_product:
//                    loadFragment(new AddProductFragment());
//                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//                    return true;
//                case R.id.merchant_help:
//                    loadFragment(new MerchantHelpFragment());
//                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//                    return true;
//                case R.id.merchant_profile:
//                    loadFragment(new ProfileFragment());
//                    bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));
//                    return true;
//                default:
//                    return false;
//            }
//        }
//    };
//
//
//    private ColorStateList createColorStateList(String colorString) {
//        int[][] states = new int[][]{
//                new int[]{android.R.attr.state_checked}, // enabled
//                new int[]{-android.R.attr.state_checked}, // disabled
//        };
//
//        int[] colors = new int[]{
//                Color.parseColor(colorString),
//                Color.GRAY,
//        };
//
//        return new ColorStateList(states, colors);
//    }
//
//    private void clearAllFragmentsFromBackStack() {
//        FragmentManager fm = getSupportFragmentManager();
//        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//            fm.popBackStackImmediate();
//        }
//        Log.d("homeTrig", fm.getBackStackEntryCount() + "");
//    }
//
////    @Override
////    public boolean onSupportNavigateUp() {
////        FragmentManager fragmentManager = getSupportFragmentManager();
////        Fragment currentFragment = fragmentManager.findFragmentById(R.id.customer_fragments_container);
////
////        // Check if the current fragment is one of the menu fragments
////        if (currentFragment instanceof OrdersFragment ||
////                currentFragment instanceof HelpFragment ||
////                currentFragment instanceof ProfileFragment) {
////
////            // Replace the current menu fragment with the HomeFragment
////            loadFragment(new HomeFragment());
////            return true;
////        } else {
////            // Let the default up navigation behavior handle it
////            return super.onSupportNavigateUp();
////        }
////    }
//
//    //    @Override
////    public void onBackPressed() {
////        //  FragmentManager fragmentManager = getSupportFragmentManager();
////     //   Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.customer_fragments_container);
////
////
////        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.customer_fragments_container);
////
////        if(!(currentFragment instanceof  HomeFragment)){
////            Log.d("Debug", "Current fragment is not HomeFragment. Loading HomeFragment.");
////
////            Toast.makeText(CustomerMainActivity.this, "homeefragment", Toast.LENGTH_SHORT).show();
////            loadFragment(new HomeFragment());
////        }
////        else{
////            Log.d("Debug", "Current fragment is HomeFragment. Performing default back button behavior.");
////            Toast.makeText(CustomerMainActivity.this, "finishing fragment", Toast.LENGTH_SHORT).show();
////            super.onBackPressed();
////        }
//////        if (currentFragment instanceof HomeFragment) {
//////            this.finish();
//////        } else if (currentFragment instanceof OrdersFragment) {
//////            clearAllFragmentsFromBackStack();
//////            bottomNavigationView.setSelectedItemId(R.id.cust_home);
//////        } else if (currentFragment instanceof HelpFragment) {
//////            clearAllFragmentsFromBackStack();
//////            bottomNavigationView.setSelectedItemId(R.id.cust_home);
//////        } else if (currentFragment instanceof ProfileFragment) {
//////            clearAllFragmentsFromBackStack();
//////            bottomNavigationView.setSelectedItemId(R.id.cust_home);
//////        }
//////        else{
//////                // Call the default back button behavior
//////                super.onBackPressed();
//////            }
////        }
//    private void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.merchant_fragments_container, fragment);
//        transaction.addToBackStack(null);  // Optional: Add the transaction to the back stack
//        transaction.commit();
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        Fragment curentfragment = getSupportFragmentManager().findFragmentById(R.id.merchant_fragments_container);
//        if (!(curentfragment instanceof MerchantHomeFragment)) {
//            loadFragment(new MerchantHomeFragment());
//        } else {
//            super.onBackPressed();
//        }
//    }
//}
//
//
//
//
