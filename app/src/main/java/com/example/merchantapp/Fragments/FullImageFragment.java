package com.example.merchantapp.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.ProductbhyIdModel.AdminProductIdItem;
import com.example.merchantapp.Model.ProductbhyIdModel.DataItem;
import com.example.merchantapp.Model.ProductbhyIdModel.ProductByIdResponse;
import com.example.merchantapp.Model.ProductbhyIdModel.UserIdItem;
import com.example.merchantapp.Model.UserModels.Data;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.R;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullImageFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_IMAGE = "image_bitmap";
   private String id,token;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String imageId;
    private    ImageView fullImageView;
    private ImageView fullProductImage;

    public FullImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FullImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullImageFragment newInstance(String param1,String param2) {
        FullImageFragment fragment = new FullImageFragment();
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

        if (getArguments() != null) {
            imageId = getArguments().getString("image_id");
        }


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_image, container, false);
        id = UserSessionManagement.getInstance(getContext()).getUserid();
         token =UserSessionManagement.getInstance(getContext()).getTokenId();

        fullProductImage = view.findViewById(R.id.full_productImage);
        fullImageView = view.findViewById(R.id.full_image_view);

        Images();
        // Retrieve the image passed from the previous fragment




        // Close dialog when image is clicked
        fullImageView.setOnClickListener(v ->
                dismiss());

        fullProductImage.setOnClickListener(v ->
                dismiss());

        return view;
    }

    private void Images() {
        if(imageId != null){
            productImage();
            fullProductImage.setVisibility(View.VISIBLE);
            fullImageView.setVisibility(View.GONE);
        }
        else{
            userImage();
            fullImageView.setVisibility(View.VISIBLE);
            fullProductImage.setVisibility(View.GONE);
        }
    }


    private void productImage() {

        Map<String,String> body = new HashMap<>();
        body.put("_id",imageId);

        ApiClient.getService().productbYId("Bearer "+token,body).enqueue(new Callback<ProductByIdResponse>() {
            @Override
            public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
                if(response.isSuccessful()){
                    ProductByIdResponse productByIdResponse = response.body();

                    DataItem dataItem = productByIdResponse.getData().get(0);

                    UserIdItem userIdItem = dataItem.getUserId().get(0);

                    AdminProductIdItem adminProductIdItem = productByIdResponse.getData().get(0).getAdminProductId().get(0);

                    String url ="https://api.gfg.org.in/"+adminProductIdItem.getProductImage();
                    Glide.with(getContext())
                            //.load(locationbasedCategoriesModel.getCategories().get(0).getImage())
                            .load(url)
                            .centerCrop()
                            .fitCenter()
                            .into(fullProductImage);

                }
            }

            @Override
            public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
                Toast.makeText(getContext(), " "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void userImage() {

        Map<String,String> body = new HashMap<>();

        body.put("_id",id);

        ApiClient.getService().userid("Bearer "+token,body).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    Data userData = userModel.getData();


                    String imageUrl = "https://api.gfg.org.in/"+userData.getProfileImage();
                    Glide.with(getContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.background_bg)
                            .error(R.drawable.dotted_background)
                            .into(fullImageView);


                  //  Toast.makeText(getContext(), "hhjjjj", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), " "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static FullImageFragment newInstance(String imageId) {
        FullImageFragment fragment = new FullImageFragment();
        Bundle args = new Bundle();
        args.putString("image_id", imageId);
        fragment.setArguments(args);
        return fragment;
    }
}