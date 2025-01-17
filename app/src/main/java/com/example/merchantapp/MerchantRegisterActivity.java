package com.example.merchantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.Model.MerchantRegisterModel.MerchantRegistrationModel;
import com.example.merchantapp.server.ApiClient;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantRegisterActivity extends AppCompatActivity {

    private String name,password,mobileno,altenativeno,address,email,shoplocation_txt,shop_name,longitude,latitude;
    private TextInputLayout location,shopname;
    private ImageView shop_image;
    private TextView add_img_btn;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView submit;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Bitmap shopimageBitmap;
  //  private Bitmap image;
    private byte[] image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_register);
        Intent intent = getIntent();
        if(intent != null){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
            mobileno = intent.getStringExtra("mobileno");
            altenativeno = intent.getStringExtra("alternativeno");
            address = intent.getStringExtra("address");
            image = intent.getByteArrayExtra("image");
            email = intent.getStringExtra("email");
            longitude = intent.getStringExtra("longitude");
            latitude = intent.getStringExtra("latitude");




        }


        location = findViewById(R.id.location);
        shopname = findViewById(R.id.merchantshopnamedittext);
        submit = findViewById(R.id.merchantSUBMIT);
         shop_image =findViewById(R.id.shop_image_view);
         add_img_btn = findViewById(R.id.add_img_btn);

         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                  GetTextforFields();

//                 Merchantregisterapi();
             }


         });


        add_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

    }

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
                shopimageBitmap = (Bitmap) extras.get("data");
           shop_image.setImageBitmap(shopimageBitmap);
                //   sendImageToApi(imageBitmap);
            }
            else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    shopimageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    shop_image.setImageBitmap(shopimageBitmap);
                    //  sendImageToApi(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void takePictureIntent() {
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
//            shopimageBitmap = (Bitmap) extras.get("data");
//            shop_image.setImageBitmap(shopimageBitmap);
//            //  String base64Image = convertImageToBase64(imageBitmap);
//            // Now you can send the base64Image to your API with the profileImage key
//            // Example: sendToApi(base64Image);
//        }
//    }

    private void GetTextforFields() {

        shoplocation_txt = location.getEditText().getText().toString();
        shop_name =  shopname.getEditText().getText().toString();
//        if(shoplocation_txt.isEmpty()){
//            location.setError("please enter your shop location");
//            return;
//           }
           if(shop_name.isEmpty()){
               shopname.setError("please enter your shop name");
               return;
           }
           Merchantregisterapi();
    }

    private void Merchantregisterapi() {

        if (image != null) {
            if (shopimageBitmap == null) {
                Toast.makeText(MerchantRegisterActivity.this, "Please capture your shop picture", Toast.LENGTH_SHORT).show();
                return;
            }
            File imageFile = saveBitmapToFile(shopimageBitmap);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
            MultipartBody.Part shopImagePart = MultipartBody.Part.createFormData("shopImage", imageFile.getName(), requestFile1);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image);
            MultipartBody.Part profileImagePart = MultipartBody.Part.createFormData("profileImage", "profileImage.jpg", requestFile);
            Map<String, RequestBody> fieldMap = new HashMap<>();
            fieldMap.put("userName", RequestBody.create(name,MediaType.parse("text/plain")));
         //   fieldMap.put("email",RequestBody.create(email,MediaType.parse("text/plain")));
           // fieldMap.put("password", RequestBody.create(password,MediaType.parse("text/plain")));
            fieldMap.put("mobileNumber", RequestBody.create(mobileno,MediaType.parse("text/plain")));
           // fieldMap.put("email", RequestBody.create(name,MediaType.parse("text/plain")));
          //  fieldMap.put("altMobileNumber", RequestBody.create(altenativeno,MediaType.parse("text/plain")));
            fieldMap.put("address", RequestBody.create(address,MediaType.parse("text/plain")));
            fieldMap.put("shopName",RequestBody.create(shop_name,MediaType.parse("text/plain")));
         //    fieldMap.put("shopLocation",RequestBody.create(shoplocation_txt,MediaType.parse("text/plain")));
            fieldMap.put("longitude", RequestBody.create(longitude,MediaType.parse("text/plain")));
            fieldMap.put("latitude", RequestBody.create(latitude,MediaType.parse("text/plain")));

            ApiClient.getService().merchantregister(fieldMap, profileImagePart,shopImagePart).enqueue(new Callback<MerchantRegistrationModel>() {
                @Override
                public void onResponse(Call<MerchantRegistrationModel> call, Response<MerchantRegistrationModel> response) {

                    if (response.isSuccessful()) {
                        MerchantRegistrationModel merchantRegistrationModel = response.body();
                          if(merchantRegistrationModel.getStatusCode() == 200){

                             Intent intent = new Intent(MerchantRegisterActivity.this,OtpActivity.class);
                             startActivity(intent);
                          }
                          else{
                              Toast.makeText(MerchantRegisterActivity.this,merchantRegistrationModel.getMessage(), Toast.LENGTH_SHORT).show();

                          }

                          }

                    else{
                        Toast.makeText(MerchantRegisterActivity.this, "Please enter correct details", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<MerchantRegistrationModel> call, Throwable t) {
                    Toast.makeText(MerchantRegisterActivity.this, "fail"+t.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });


        }

    }
    private File saveBitmapToFile(Bitmap bitmap) {
        // Create a file to store the bitmap
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, "shopImage.jpg");
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

//    private File saveBitmapToFile(String image) {
//        File filesDir = getApplicationContext().getFilesDir();
//        File imageFile = new File(filesDir, "profileImage.jpg");
//        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageFile;
//    }
}