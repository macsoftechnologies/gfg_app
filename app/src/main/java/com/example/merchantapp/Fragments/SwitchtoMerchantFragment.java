package com.example.merchantapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.CustomerMainActivity;
import com.example.merchantapp.LoginActivity;
import com.example.merchantapp.R;
import com.example.merchantapp.SwitchToMerchantActivity;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwitchtoMerchantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwitchtoMerchantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PROFILE_IMAGE_KEY = "shopImage";
    private Bitmap imageBitmap;
    private static final int REQUEST_IMAGE_PICK = 2;
    private TextView add_shopimg_btn, register_btn;
    private TextInputLayout shopname, shopLocation, mobile_no;
    private ImageView shop_img;
    String token, shop_name_txt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SwitchtoMerchantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SwitchtoMerchantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SwitchtoMerchantFragment newInstance(String param1, String param2) {
        SwitchtoMerchantFragment fragment = new SwitchtoMerchantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_switchto_merchant, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Switch To Merchant");

        castingViews(view);

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
        return view;



    }



    private void castingViews(View view) {
        add_shopimg_btn =view.findViewById(R.id.add_img_btn);
        register_btn = view.findViewById(R.id.merchant_SUBMIT);
        shopname = view.findViewById(R.id.merchant_shopnamedittext);
        shopLocation = view.findViewById(R.id.merchant_location);
        mobile_no = view.findViewById(R.id.mobileno);
        shop_img = view.findViewById(R.id.shop_image);

    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                shop_img.setImageBitmap(imageBitmap);
                //   sendImageToApi(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImageUri);
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
            Toast.makeText(getContext(), "Please add a shop image", Toast.LENGTH_SHORT).show();
            return; // Exit the method if imageBitmap is null
        }
        File imageFile = saveBitmapToFile(imageBitmap);
        token = UserSessionManagement.getInstance(getContext()).getTokenId();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
        MultipartBody.Part profileImagePart = MultipartBody.Part.createFormData("shopImage", imageFile.getName(), requestFile);

        Map<String, RequestBody> fieldMap = new HashMap<>();
        fieldMap.put("mobileNumber", RequestBody.create(UserSessionManagement.getInstance(getContext()).getPhno(), MediaType.parse("text/plain")));
        fieldMap.put("shopName", RequestBody.create(shop_name_txt, MediaType.parse("text/plain")));
        //  fieldMap.put("shopLocation", RequestBody.create(shopLocation.getEditText().getText().toString(), MediaType.parse("text/plain")));

        //   SwitchModelResponse switchModelResponse = new SwitchModelResponse();
        ApiClient.getService().switchUser("Bearer " +token, fieldMap, profileImagePart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"sucess",Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage("You have to logout and login again to continue as merchant \n click yes to logout")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    UserSessionManagement.saveBoolean(getActivity(), "LOGIN", false);
                                    UserSessionManagement.saveBoolean(getActivity(),"MERCHANTLOGIN",false);

                                    UserSessionManagement sessionManagement = UserSessionManagement.getInstance(getContext());

// Call deleteToken to remove the token
                                    sessionManagement.deleteToken();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton("no", null).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    private File saveBitmapToFile (Bitmap bitmap){
        // Create a file to store the bitmap
        File filesDir = getContext().getFilesDir();
        File imageFile = new File(filesDir, "profileImage.jpg");
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }


}