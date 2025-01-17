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

import com.example.merchantapp.Model.CustomerRegistrationModel;
import com.example.merchantapp.Model.GoogleAPiModel.GeocodeResponse;
import com.example.merchantapp.Model.GoogleAPiModel.ResultsItem;

import com.example.merchantapp.Utilites.UserSessionManagement;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegisterActivity extends AppCompatActivity {

    private TextInputLayout username, mobileno, alternativeno, password, address, email;
    private String name_txt,mobileno_txt,alternativno_txt,password_txt,addres_txt,email_txt,profile_pic;
    private static final String BASE_URL = "https://apis.mapmyindia.com/advancedmaps/v1/4b166d0d32f064238cff077e7057d862/";

    private static final int REQUEST_IMAGE_PICK = 2;
    private String longitude,latitude,useraddress ;
    private  double lat,lng,lt,lg;
    private TextView submitbtn;
    private ImageView cambtn, profileimage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PROFILE_IMAGE_KEY = "profileImage";
    private Bitmap imageBitmap;
    private  boolean locationaddress = false;
    private boolean location = false;
    FusedLocationProviderClient mFusedLocationClient;


    TextView latitudeTextView;
    TextView longitTextView;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        username = findViewById(R.id.name);
        mobileno = findViewById(R.id.mobile_no);
        alternativeno = findViewById(R.id.alternative_no);
        profileimage = findViewById(R.id.profileImageView);
        password = findViewById(R.id.password);
        cambtn = findViewById(R.id.profileChangeImageBtn);

        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        submitbtn = findViewById(R.id.submit_registration);
        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTextfromfields();
            }


        });


        cambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dispatchTakePictureIntent();
                showImagePickerDialog();

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
                            latitudeTextView.setText("ll"+location.getLatitude() + "");

                            longitTextView.setText("lg"+location.getLongitude() + "");

                             locationaddress = true;
                            lt = location.getLatitude();
                            lg = location.getLongitude();


//
                            LocationAddress();
//
                           longitude = String.valueOf(location.getLongitude());
                            latitude = String.valueOf(location.getLatitude());

                        }
                    }
                });
            } else {
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);

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
            latitudeTextView.setText("Latitude1: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude1: " + mLastLocation.getLongitude() + "");
//            lat = mLastLocation.getLatitude();
//            lng = mLastLocation.getLongitude();

          //  lat = 0.0;
            lat = mLastLocation.getLatitude();
           // lng = 0.0;
            lng = mLastLocation.getLongitude();
            location = true;

            LocationAddress();


////
            longitude = String.valueOf(mLastLocation.getLongitude());
            latitude = String.valueOf(mLastLocation.getLatitude());
//            UserSessionManagement.getInstance(getApplicationContext()).setLatitude(latitude);
//            UserSessionManagement.getInstance(getApplicationContext()).setLongitude(longitude);


        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
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





    private void GetTextfromfields() {
        name_txt = username.getEditText().getText().toString();
     //    email_txt = email.getEditText().getText().toString();
        mobileno_txt = mobileno.getEditText().getText().toString().trim();
      //  alternativno_txt = alternativeno.getEditText().getText().toString().trim();
       // password_txt = password.getEditText().getText().toString();
        useraddress = address.getEditText().getText().toString();


        if(name_txt.isEmpty()){
            username.setError("please enter your name");
            return;
        }
//        if(email_txt.isEmpty()){
//            email.setError("please enter your email");
//            return;
//        }
        if(mobileno_txt.isEmpty()){
            mobileno.setError("please enter your phone number");
            return;

        }
        else if(!mobileno_txt.matches("\\+?\\d{10}")){
            mobileno.setError("please enter a valid phone number");
            return;
        }
//        if(alternativno_txt.isEmpty()){
//            alternativeno.setError("please enter your phone number");
//            return;
//        }
//        else if(!alternativno_txt.matches("\\+?\\d{10}")){
//            alternativeno.setError("please enter a valid phone number");
//            return;
//        }
//
//        if(password_txt.isEmpty()){
//            password.setError("please enter your password");
//            return;
//        }
//        if(addres_txt.isEmpty()){
//            address.setError("please enter your address");
//            return;
//        }


        UserRegistrationapi();

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
//            profileimage.setImageBitmap(imageBitmap);
//          //  String base64Image = convertImageToBase64(imageBitmap);
//            // Now you can send the base64Image to your API with the profileImage key
//            // Example: sendToApi(base64Image);
//        }
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
                profileimage.setImageBitmap(imageBitmap);
             //   sendImageToApi(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    profileimage.setImageBitmap(imageBitmap);
                  //  sendImageToApi(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void LocationAddress() {
//        double latitude = 17.7422;
//        double longitude = 83.3091;


            String latlng;

        if(locationaddress){
            latlng = lt+","+lg;

        }
        else{
            latlng = lat+","+lng;
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
                 //    Toast.makeText(UserRegisterActivity.this," jhnbgv"+addres,Toast.LENGTH_SHORT).show();
                        address.getEditText().setText(addres);

                } else {
                    Log.e(TAG, "Geocode API call failed with response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GeocodeResponse> call, Throwable t) {
                Log.e(TAG, "Geocode API call failed", t);
            }
        });

    }




    private void UserRegistrationapi() {

        String lats = "83.2185";
        String lngs ="71.6889";

        if (imageBitmap == null) {
            Toast.makeText(UserRegisterActivity.this, "Please capture a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }
        File imageFile = saveBitmapToFile(imageBitmap);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
        MultipartBody.Part profileImagePart = MultipartBody.Part.createFormData("profileImage", imageFile.getName(), requestFile);

        Map<String, RequestBody> fieldMap = new HashMap<>();
        fieldMap.put("userName", RequestBody.create(name_txt, MediaType.parse("text/plain")));
      //  fieldMap.put("password", RequestBody.create(password_txt, MediaType.parse("text/plain")));
        fieldMap.put("mobileNumber", RequestBody.create(mobileno_txt, MediaType.parse("text/plain")));
    //    fieldMap.put("email", RequestBody.create(email_txt, MediaType.parse("text/plain")));
     //   fieldMap.put("altMobileNumber", RequestBody.create(alternativno_txt, MediaType.parse("text/plain")));
        fieldMap.put("address", RequestBody.create(useraddress, MediaType.parse("text/plain")));
       fieldMap.put("longitude", RequestBody.create(longitude, MediaType.parse("text/plain")));
       fieldMap.put("latitude", RequestBody.create(latitude, MediaType.parse("text/plain")));

        ApiClient.getService().register(fieldMap,profileImagePart).enqueue(new Callback<CustomerRegistrationModel>() {
            @Override
            public void onResponse(Call<CustomerRegistrationModel> call, Response<CustomerRegistrationModel> response) {
                if (response.isSuccessful()) {

                    CustomerRegistrationModel customerRegistrationModel = response.body();
                    if(customerRegistrationModel.getStatusCode() == 200) {

                        Toast.makeText(UserRegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(UserRegisterActivity.this,"kk"+longitude,Toast.LENGTH_SHORT).show();


                        startActivity(new Intent(UserRegisterActivity.this, OtpActivity.class));


                        //  String cust = String.valueOf(customerRegistrationModel.getUserRegisterData().getLatitude());
                    }
                    else{
                        Toast.makeText(UserRegisterActivity.this,customerRegistrationModel.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(UserRegisterActivity.this, "Please enter correct details", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CustomerRegistrationModel> call, Throwable t) {
                Toast.makeText(UserRegisterActivity.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
              //  latitudeTextView.setText("error"+t.getMessage());
            }
        });
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        // Create a file to store the bitmap
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, "profileImage.jpg");
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
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
        Intent intent = new Intent(UserRegisterActivity.this,LoginOrRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}