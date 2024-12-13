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

import com.example.merchantapp.Model.SwitchModel.SwitchModelResponse;
import com.example.merchantapp.Utilites.UserSessionManagement;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchToMerchantActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PROFILE_IMAGE_KEY = "shopImage";
    private Bitmap imageBitmap;
    private static final int REQUEST_IMAGE_PICK = 2;
    private TextView add_shopimg_btn, register_btn;
    private TextInputLayout shopname, shopLocation, mobile_no;
    private ImageView shop_img;
    String token, shop_name_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_to_merchant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_shopimg_btn = findViewById(R.id.add_img_btn);
        register_btn = findViewById(R.id.merchant_SUBMIT);
        shopname = findViewById(R.id.merchant_shopnamedittext);
        shopLocation = findViewById(R.id.merchant_location);
        mobile_no = findViewById(R.id.mobileno);
        shop_img = findViewById(R.id.shop_image);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SwitchToMerchantapi();
                GetTextfromfields();
            }
        });

        add_shopimg_btn.setOnClickListener(new View.OnClickListener() {
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
                imageBitmap = (Bitmap) extras.get("data");
                shop_img.setImageBitmap(imageBitmap);
                //   sendImageToApi(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    shop_img.setImageBitmap(imageBitmap);
                    //  sendImageToApi(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void dispatchPictureIntent() {
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
//            shop_img.setImageBitmap(imageBitmap);
//            //  String base64Image = convertImageToBase64(imageBitmap);
//            // Now you can send the base64Image to your API with the profileImage key
//            // Example: sendToApi(base64Image);
//        }
//    }

    private void GetTextfromfields() {
        shop_name_txt = shopname.getEditText().getText().toString();
        //    email_txt = email.getEditText().getText().toString();



        if (shop_name_txt.isEmpty()) {
            shopname.setError("please enter your name");
            return;
        }
//        if(email_txt.isEmpty()){
//            email.setError("please enter your email");
//            return;
//        }



         SwitchToMerchantapi();

    }

    private void SwitchToMerchantapi() {

        if (imageBitmap == null) {
            Toast.makeText(this, "Please add a shop image", Toast.LENGTH_SHORT).show();
            return; // Exit the method if imageBitmap is null
        }
        File imageFile = saveBitmapToFile(imageBitmap);
        token = UserSessionManagement.getInstance(getApplicationContext()).getTokenId();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
        MultipartBody.Part profileImagePart = MultipartBody.Part.createFormData("shopImage", imageFile.getName(), requestFile);

        Map<String, RequestBody> fieldMap = new HashMap<>();
        fieldMap.put("mobileNumber", RequestBody.create(UserSessionManagement.getInstance(getApplicationContext()).getPhno(), MediaType.parse("text/plain")));
        fieldMap.put("shopName", RequestBody.create(shop_name_txt, MediaType.parse("text/plain")));
      //  fieldMap.put("shopLocation", RequestBody.create(shopLocation.getEditText().getText().toString(), MediaType.parse("text/plain")));

        //   SwitchModelResponse switchModelResponse = new SwitchModelResponse();
        ApiClient.getService().switchUser("Bearer " +token, fieldMap, profileImagePart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.isSuccessful()){
                   Toast.makeText(SwitchToMerchantActivity.this,"sucess",Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SwitchToMerchantActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
        private File saveBitmapToFile (Bitmap bitmap){
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SwitchToMerchantActivity.this,CustomerMainActivity.class);
        startActivity(intent);
    }
}