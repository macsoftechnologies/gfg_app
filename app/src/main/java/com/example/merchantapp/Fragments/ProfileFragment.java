package com.example.merchantapp.Fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.merchantapp.Utilites.Constants.MERCHANT_DETAILS_FRAGMENT;
import static com.example.merchantapp.Utilites.Constants.SWITCH_TO_MERCHANT;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.merchantapp.LoginActivity;
import com.example.merchantapp.MapActivity;
import com.example.merchantapp.Model.LoginModel;
import com.example.merchantapp.Model.UpadteModels.UpdateModel;
import com.example.merchantapp.Model.UserModels.Data;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.OtpActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
     //private static final String BASE_URL = "https://apis.mapmyindia.com/advancedmaps/v1/4b166d0d32f064238cff077e7057d862/";
     private static final int LOCATION_PERMISSION_CODE = 1001;
    private static final int REQUEST_CODE_MAP = 1003;
    int PERMISSION_ID = 44;
    private Bitmap lastUploadedImage = null;
    private static final int REQUEST_IMAGE_CAPTURE_PROFILE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_SHOP = 2;
    private static final int REQUEST_IMAGE_PICK_PROFILE = 3;
    private static final int REQUEST_IMAGE_PICK_SHOP = 4;
    private static final String ARG_PARAM2 = "param2";
    private TextView logout,updatebtn;
    private Bitmap imageBitmap;
    private Bitmap shopImageBitmap,profileImageBitmap;
    private CircleImageView profileImg;
    private  boolean isEdit= false;
    private boolean feildset = false;
    private TextInputLayout name_txt,password,address,mobileno,shopname,shopLocation;
  private LoginModel loginModel;
  private String id ,token,lat,log;
  private ImageView profileimgbtn,shopImage;

  private TextView switch_btn;
  private LinearLayout merchant_profile_layout;
//    private LoginData loginData;
   // private String name_txt,address_txt,mobileno_txt,email_txt;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

         id = UserSessionManagement.getInstance(getContext()).getUserid();
         token = UserSessionManagement.getInstance(getContext()).getTokenId();

//        String password = sharedPreferences.getString("password", "");




// Retrieve other data as needed



        castingViews(view);






        switchText();


        userProfile();


        profileimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog(true);
            }
        });

        shopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog(false);
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullImageFragment dialog = new FullImageFragment();
                dialog.show(getParentFragmentManager(), "full_image_dialog");

            }
        });


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    updateprofile();
                    isEdit= false;

                    updatebtn.setText("EDIT");
                    enbaleUITexts();
                }
                else{
                    isEdit = true;
                    updatebtn.setText("SUBMIT");
                    enbaleUITexts();

                }
            }
        });

        enbaleUITexts();

        address.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (address.getEditText().getRight() -
                            address.getEditText().getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            startActivityForResult(new Intent(getActivity(), MapActivity.class), REQUEST_CODE_MAP);
                        } else {
                            requestLocationPermissions();
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to logout")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                UserSessionManagement.saveBoolean(getActivity(), "LOGIN", false);
                                UserSessionManagement.saveBoolean(getActivity(),"MERCHANTLOGIN",false);

                                UserSessionManagement sessionManagement = UserSessionManagement.getInstance(getContext());

// Call deleteToken to remove the token
                                sessionManagement.deleteToken();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), OtpActivity.class));
                            }
                        })
                        .setNegativeButton("no", null).show();

            }

        });

        switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new AlertDialog.Builder(getContext())
                new AlertDialog.Builder(getContext())
                        .setMessage("Please Register as Merchant")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                             //   startActivity(new Intent(getActivity(), SwitchToMerchantActivity.class));
                                SwitchtoMerchantFragment switchtoMerchantFragment = new SwitchtoMerchantFragment();
                                addToFragmentContainer(switchtoMerchantFragment, true, SWITCH_TO_MERCHANT);
//
                               // getActivity().finish();
                            }
                        })
                        .setNegativeButton("no", null).show();

            }
        });
        return view;


    }


    private void showImagePickerDialog(final boolean isProfileImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an option");

        String[] options = {"Take Photo", "Choose from Gallery"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Take photo from camera
                    dispatchTakePictureIntent(isProfileImage);
                } else if (which == 1) {
                    // Choose photo from gallery
                    selectImageFromGallery(isProfileImage);
                }
            }
        });
        builder.show();
    }
    private void dispatchTakePictureIntent(boolean isProfileImage) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, isProfileImage ? REQUEST_IMAGE_CAPTURE_PROFILE : REQUEST_IMAGE_CAPTURE_SHOP);
        }
    }

    // Launch gallery intent
    private void selectImageFromGallery(boolean isProfileImage) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, isProfileImage ? REQUEST_IMAGE_PICK_PROFILE : REQUEST_IMAGE_PICK_SHOP);
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK) {
//            if (data != null) {
//                String addressLine = data.getStringExtra("addressLine");
//                if (addressLine != null) {
//                    address.getEditText().setText(addressLine);
//                }
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle map result
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK) {
            if (data != null) {
                String addressLine = data.getStringExtra("addressLine");
                if (addressLine != null) {
                    address.getEditText().setText(addressLine);  // Assuming 'address' is a TextInputLayout
                }
            }
        }
//        Bundle extras = data.getExtras();
//        imageBitmap = (Bitmap) extras.get("data");
//        profileImg.setImageBitmap(imageBitmap);
        // Handle image capture result

        else if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE_PROFILE || requestCode == REQUEST_IMAGE_CAPTURE_SHOP) {
                // Handle camera result
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (requestCode == REQUEST_IMAGE_CAPTURE_PROFILE) {
                    profileImageBitmap = imageBitmap;
                    profileImg.setImageBitmap(profileImageBitmap);
                } else {
                    shopImageBitmap = imageBitmap;
                    shopImage.setImageBitmap(shopImageBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK_PROFILE || requestCode == REQUEST_IMAGE_PICK_SHOP) {
                // Handle gallery result
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                    if (requestCode == REQUEST_IMAGE_PICK_PROFILE) {
                        profileImageBitmap = imageBitmap;
                        profileImg.setImageBitmap(profileImageBitmap);
                    } else {
                        shopImageBitmap = imageBitmap;
                        shopImage.setImageBitmap(shopImageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }



    private void requestLocationPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                    .setTitle("Permission Info")
                    .setMessage("Location Permissions are needed to Show the NearBy Services to You")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    private void switchText() {
        boolean isMerchantLoginSucces = UserSessionManagement.gettBoolean(getContext(),"MERCHANTLOGIN");
        boolean isLoginSucces = UserSessionManagement.getBoolean(getContext(), "LOGIN");
       if(isMerchantLoginSucces){
          switch_btn.setVisibility(View.GONE);

          merchant_profile_layout.setVisibility(View.VISIBLE);

       }
       else if(isLoginSucces){
           switch_btn.setVisibility(View.VISIBLE);
           switch_btn.setText("SWITCH TO MERCHANT");

           merchant_profile_layout.setVisibility(View.GONE);
       }
       else if(isLoginSucces && isMerchantLoginSucces){
           switch_btn.setVisibility(View.GONE);
           merchant_profile_layout.setVisibility(View.VISIBLE);
        //   shopname.setVisibility(View.VISIBLE);


       }


    }


    private void userProfile() {
        Map<String,String> body = new HashMap<>();

        body.put("_id",id);

        ApiClient.getService().userid("Bearer "+token,body).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    Data userData = userModel.getData();
                    name_txt.getEditText().setText(userData.getUserName());
                    mobileno.getEditText().setText(userData.getMobileNumber());
                    address.getEditText().setText(userData.getAddress());
                    shopname.getEditText().setText(userData.getShopName());
                  //  shopLocation.getEditText().setText(userData.getShopLocation());

                    String imageUrl = "https://api.gfg.org.in/"+userData.getProfileImage();
                    String shopurl ="https://api.gfg.org.in/" + userData.getShopImage();
                    Glide.with(getContext())
                            .load(shopurl)
                            .placeholder(R.drawable.background_bg)
                            .error(R.drawable.dotted_background)
                            .into(shopImage);

                    Glide.with(getContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.background_bg)
                            .error(R.drawable.dotted_background)
                            .into(profileImg);




                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }


    private void enbaleUITexts() {
        name_txt.getEditText().setEnabled(isEdit);
        mobileno.getEditText().setEnabled(isEdit);
        address.getEditText().setEnabled(isEdit);
        shopname.getEditText().setEnabled(isEdit);
        shopLocation.getEditText().setEnabled(isEdit);



    }




//    private void setTextFeilds() {
//
//        if(!feildset) {
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mykey", Context.MODE_PRIVATE);
//            String value = sharedPreferences.getString("value", "");
//
//
//            //name_txt.getEditText().setText();
//            if (!TextUtils.isEmpty(value)) {
//                UserData lr = new Gson().fromJson(value, UserData.class);
//                //  Log.d("SharedPrefs", "Last Name: " + lr.getUserName()); // Add this line to check last name value
//                name_txt.getEditText().setText(lr.getUserName());
//                mobileno.getEditText().setText(lr.getMobileNumber());
//                address.getEditText().setText(lr.getAddress());
//                password.getEditText().setText(lr.getPassword());
//
//               // Toast.makeText(getContext(), "msg is succesful" + name_txt, Toast.LENGTH_SHORT).show();
//                feildset = true;
//            }
//        }
//
//    }

    private void castingViews(View view) {
        profileImg = view.findViewById(R.id.profile_ImageView);
        logout = view.findViewById(R.id.tvlogout);
        name_txt = view.findViewById(R.id.nameedittext);
        password = view.findViewById(R.id.passwordedittext);
        mobileno = view.findViewById(R.id.numberedittext);
        address = view.findViewById(R.id.addressedittext);
        shopname = view.findViewById(R.id.shopname_edittext);
        shopLocation =view.findViewById(R.id.shopLocation_edittext);
        updatebtn = view.findViewById(R.id.update_btn);
        switch_btn = view.findViewById(R.id.switcb_btn);
        profileimgbtn = view.findViewById(R.id.profileImageBtn);
        merchant_profile_layout = view.findViewById(R.id.merchant_profile_layout);
        shopImage = view.findViewById(R.id.shop_merchant_image);


    }

    private void updateprofile() {

//        Map<String,String> body= new HashMap<>();
//
//      // name_txt.getEditText().setText(id);
//        body.put("_id",id);
//        body.put("userName",name_txt.getEditText().getText().toString());
        File profileImageFile = null;
        File shopImageFile = null;

        // Convert the profile image bitmap to a file
        if (profileImageBitmap != null) {
            profileImageFile = saveBitmapToFile(profileImageBitmap);
        }

        // Convert the shop image bitmap to a file
        if (shopImageBitmap != null) {
            shopImageFile = saveBitmapToFile(shopImageBitmap);
        }

        // Create the RequestBody for the profile image file if it exists
        MultipartBody.Part profileImagePart = null;
        if (profileImageFile != null) {
            RequestBody profileRequestFile = RequestBody.create(MediaType.parse("image/jpeg"), profileImageFile);
            profileImagePart = MultipartBody.Part.createFormData("profileImage", profileImageFile.getName(), profileRequestFile);
        }

        // Create the RequestBody for the shop image file if it exists
        MultipartBody.Part shopImagePart = null;
        if (shopImageFile != null) {
            RequestBody shopRequestFile = RequestBody.create(MediaType.parse("image/jpeg"), shopImageFile);
            shopImagePart = MultipartBody.Part.createFormData("shopImage", shopImageFile.getName(), shopRequestFile);
        }


//
//        File imageFile = saveBitmapToFile(imageBitmap);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
//        MultipartBody.Part profileImagePart = MultipartBody.Part.createFormData("profileImage", imageFile.getName(), requestFile);

        Map<String, RequestBody> fieldMap = new HashMap<>();
        fieldMap.put("_id",RequestBody.create(id,MediaType.parse("text/plain")));
        fieldMap.put("userName", RequestBody.create(name_txt.getEditText().getText().toString(), MediaType.parse("text/plain")));
        //  fieldMap.put("password", RequestBody.create(password_txt, MediaType.parse("text/plain")));
        fieldMap.put("mobileNumber", RequestBody.create(mobileno.getEditText().getText().toString(), MediaType.parse("text/plain")));
        //    fieldMap.put("email", RequestBody.create(email_txt, MediaType.parse("text/plain")));
        //   fieldMap.put("altMobileNumber", RequestBody.create(alternativno_txt, MediaType.parse("text/plain")));
        fieldMap.put("address", RequestBody.create(address.getEditText().getText().toString(), MediaType.parse("text/plain")));
        fieldMap.put("shopName",RequestBody.create(shopname.getEditText().getText().toString(),MediaType.parse("text/plain")));





        ApiClient.getService().update("Bearer "+token,fieldMap,profileImagePart,shopImagePart).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                if (response.isSuccessful()) {
                   // userIdprofile();
                    Toast.makeText(getContext(), "succesfull", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {

                Toast.makeText(getContext(), "unsuccesfull", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private File saveBitmapToFile(Bitmap bitmap) {
        // Create a file to store the bitmap


        if (bitmap == null) {
            // Return null or handle the case where bitmap is null
            return null;
        }

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


    private void addToFragmentContainer(Fragment fragment, boolean addbackToStack, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addbackToStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.customer_fragments_container, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

//        File filesDir = getContext().getFilesDir();
//        File imageFile = new File(filesDir, "profileImage.jpg");
//        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageFile;
//    }


//    private void userIdprofile() {
//        Map<String,String> body = new HashMap<>();
//
//        body.put("_id",id);
//
//
//        ApiClient.getService().userid("Bearer "+token,body).enqueue(new Callback<UserModel>() {
//            @Override
//            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                if (response.isSuccessful()){
//                    UserModel userModel = response.body();
//                    UserData userData = userModel.getData();
//
//                    name_txt.getEditText().setText(userData.getUserName());
//
//                    Toast.makeText(getContext(),"name"+userData.getUserName(),Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<UserModel> call, Throwable t) {
//
//            }
//        });
//    }


}