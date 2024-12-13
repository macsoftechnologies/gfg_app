package com.example.merchantapp;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.Model.GoogleAPiModel.GeocodeResponse;
import com.example.merchantapp.Model.GoogleAPiModel.ResultsItem;

import com.example.merchantapp.server.ApiClient;
import com.example.merchantapp.server.MapMyIndiaApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantActivity extends AppCompatActivity {

    private TextInputLayout name,mobile,alteranativeno,password,address,email;
    private  String name_txt,mobile_txt,altno_txt,password_txt,addres_txt,email_txt,latitude,longitude,useraddress ;
    private TextView register;
    private ImageView profilepic,cambtn;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private String img;

    private  double lat,lng,lt,lg;
    private boolean locationaddres = false;
    private  boolean locations = false;
    //private static final String PROFILE_IMAGE_KEY = "profileImage";
    private Bitmap imageBitmap;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        name= findViewById(R.id.merchantnameedittext);

        profilepic = findViewById(R.id.profileImageView);
        cambtn = findViewById(R.id.profileChangeImageBtn);
        mobile = findViewById(R.id.mobilenoedittext);
        alteranativeno = findViewById(R.id.alernativenoedittext);
        password = findViewById(R.id.passwordedittext);
        address = findViewById(R.id.addressedittext);
        register = findViewById(R.id.merchantregister);
        email =findViewById(R.id.merchantemailedittext);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dispatchTakePictureIntent();
                showImagePickerDialog();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GettextfromFields();
            }


        });




    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            locations = true;
                            lg = location.getLongitude();
                            lt = location.getLatitude();
                            LocationAddress();
//                            latitudeTextView.setText(location.getLatitude() + "");
//                            longitTextView.setText(location.getLongitude() + "");
                            longitude = String.valueOf(location.getLongitude());
                            latitude = String.valueOf(location.getLatitude());
                        }
                    }
                });
            } else {
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(MerchantActivity.this);

                // Set dialog title, message, and button
                builder.setTitle("ENABLE LOCATION")
                        .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                                "use this app")
                        .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // OK button clicked
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancel button clicked
                                dialog.dismiss(); // Dismiss the dialog
                            }
                        });

                // Create AlertDialog object and show it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        } else {
            // if permissions aren't available,
            // request for permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions();
            }
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
//            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
//            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");

//            longitude = String.valueOf(mLastLocation.getLongitude());
//            latitude = String.valueOf(mLastLocation.getLatitude());

            lat = mLastLocation.getLatitude();

            lng = mLastLocation.getLongitude();
            locationaddres = true;
            LocationAddress();


////
            longitude = String.valueOf(mLastLocation.getLongitude());
            latitude = String.valueOf(mLastLocation.getLatitude());



        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }


//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//           // img = String.valueOf(imageBitmap);
//            profilepic.setImageBitmap(imageBitmap);
//            //  String base64Image = convertImageToBase64(imageBitmap);
//            // Now you can send the base64Image to your API with the profileImage key
//            // Example: sendToApi(base64Image);
//        }
//
//
//    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        String[] options = {"Take Photo", "Choose from Gallery"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Take photo from camera
                    dispatchTakePictureIntent();
                } else if (which == 1) {
                    // Choose photo from gallery
                    selectImageFromGallery();
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                profilepic.setImageBitmap(imageBitmap);
                //   sendImageToApi(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    profilepic.setImageBitmap(imageBitmap);
                    //  sendImageToApi(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void LocationAddress() {


          String latlng ;
          if(locationaddres){
              latlng = lat+","+lng;

          }
          else{
              latlng = lt+","+lg;
          }
        MapMyIndiaApiService geocodeService = ApiClient.getGeocodeService();
        Call<GeocodeResponse> call = geocodeService.getGeocode(latlng,"AIzaSyCiUU7Q5X1hTMRAJr0YJZPOxw40FfZcZp0");

        call.enqueue(new Callback<GeocodeResponse>() {
            @Override
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    GeocodeResponse geocodeResponse = response.body();
                    List<ResultsItem> resultsItems = geocodeResponse.getResults();


                    String addres = resultsItems.get(0).getFormattedAddress();
                   // Toast.makeText(MerchantActivity.this," jhnbgv"+addres,Toast.LENGTH_SHORT).show();
                    address.getEditText().setText(addres);

                } else {
                    Log.e(TAG, "Geocode API call failed with response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {

            }



        });

    }
    private void GettextfromFields(){

        name_txt = name.getEditText().getText().toString();
        email_txt =email.getEditText().getText().toString();
        mobile_txt = mobile.getEditText().getText().toString().trim();
        altno_txt =alteranativeno.getEditText().getText().toString().trim();
        password_txt= password.getEditText().getText().toString();
        addres_txt = address.getEditText().getText().toString();

        if(name_txt.isEmpty()){
            name.setError("please enter your name");
            return;
        }
//        if(email_txt.isEmpty()){
//            email.setError("please enter your email");
//            return;
//        }
        if(mobile_txt.isEmpty()){
            mobile.setError("please enter your name");
            return;
        }
//        if(altno_txt.isEmpty()){
//            alteranativeno.setError("please enter your name");
//            return;
//        }
//        if(password_txt.isEmpty()){
//            password.setError("please enter your name");
//            return;
//        }
        if(addres_txt.isEmpty()){
            address.setError("Please Enter your address");
        }



       if(imageBitmap == null){

               Toast.makeText(MerchantActivity.this,"please upload your profile pic",Toast.LENGTH_SHORT).show();
               return;
       }
        naviagtetActivity();
    }

    private void naviagtetActivity() {
        Intent intent = new Intent(MerchantActivity.this, MerchantRegisterActivity.class);


        if (imageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("image", byteArray);

        }


      //  intent.putExtra("email",email_txt);
        intent.putExtra("name", name_txt);
        intent.putExtra("mobileno", mobile_txt);
       // intent.putExtra("alternativeno", altno_txt);
      //  intent.putExtra("password", password_txt);
        intent.putExtra("address", useraddress);
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);
        intent.putExtra("address",addres_txt);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
    @Override
    public void onBackPressed() {
        // Finish the activity when back is pressed
        Intent intent = new Intent(MerchantActivity.this,LoginOrRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}