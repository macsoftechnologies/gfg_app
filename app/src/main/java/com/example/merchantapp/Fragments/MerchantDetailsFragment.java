package com.example.merchantapp.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.ProductbhyIdModel.AdminProductIdItem;
import com.example.merchantapp.Model.ProductbhyIdModel.DataItem;
import com.example.merchantapp.Model.ProductbhyIdModel.ProductByIdResponse;
import com.example.merchantapp.Model.ProductbhyIdModel.UserIdItem;
import com.example.merchantapp.R;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String id,token;
    private TextView productname,productprice,productspecifications,merchantname,merchantnumber,merchantlocation,merchantshop;
    private ImageView product_img;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MerchantDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MerchantDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MerchantDetailsFragment newInstance(String param1, String param2) {
        MerchantDetailsFragment fragment = new MerchantDetailsFragment();
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

        if(getArguments() != null) {

            id = getArguments().getString("id");
           // Toast.makeText(getContext(), ""+id, Toast.LENGTH_SHORT).show();
        //    ids = getArguments().getString("_id");

            // Inflate the layout for this fragment
        }
        View view = inflater.inflate(R.layout.fragment_merchant_details2, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Product Details");

        token = UserSessionManagement.getInstance(getContext()).getTokenId();

        castingViews(view);

       GetMerchantDetails();

        product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageId = id; // Replace with the actual ID

                // Create a new instance of FullImageFragment with the image ID
                FullImageFragment dialog = FullImageFragment.newInstance(imageId);
                dialog.show(getParentFragmentManager(), "full_image_dialog");
            }
        });

        return view;

    }





    private void castingViews(View view) {
        merchantname = view.findViewById(R.id.merchant_name);
        merchantlocation = view.findViewById(R.id.merchant_shop_location);
     //   merchantshop = view.findViewById(R.id.merchant_shop_name);
        merchantnumber = view.findViewById(R.id.merchant_number);
        productspecifications = view.findViewById(R.id.specifications);
        productname =view.findViewById(R.id.product_name);
        productprice = view.findViewById(R.id.product_price);
        product_img = view.findViewById(R.id.product_image);
    }


//      profileImg.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            FullImageFragment dialog = new FullImageFragment();
//            dialog.show(getParentFragmentManager(), "full_image_dialog");
//
//        }
//    });

    private void GetMerchantDetails() {

        Map<String,String> body = new HashMap<>();
        body.put("_id",id);

        ApiClient.getService().productbYId("Bearer "+token,body).enqueue(new Callback<ProductByIdResponse>() {
            @Override
            public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
                if(response.isSuccessful()){
                    ProductByIdResponse productByIdResponse = response.body();

                    DataItem dataItem = productByIdResponse.getData().get(0);



                    AdminProductIdItem adminProductIdItem = productByIdResponse.getData().get(0).getAdminProductId().get(0);
                    productname.setText(adminProductIdItem.getProductName());

                       productprice.setText("₹ "+dataItem.getPrice());

                    UserIdItem userIdItem = dataItem.getUserId().get(0);
                    merchantname.setText(" "+userIdItem.getUserName());
                    merchantnumber.setText(" "+userIdItem.getMobileNumber());

                    merchantlocation.setText(" "+userIdItem.getShopLocation());
                    String url ="https://api.gfg.org.in/"+adminProductIdItem.getProductImage();
                    Glide.with(getContext())
                            //.load(locationbasedCategoriesModel.getCategories().get(0).getImage())
                            .load(url)
                            .centerCrop()
                            .fitCenter()
                            .into(product_img);
                    String specsString = productSpecificationsToString(adminProductIdItem.getProductSpecifications());
                    productspecifications.setText(specsString);
                 //   merchantshop.setText(userIdItem.getShopName());
//                    com.example.merchantapp.Model.ProductbhyIdModel.DataItem dataItem = productByIdResponse.getData().get(0);
//                    List<UserIdItem> UserIdItem = dataItem.getUserId();
//


                    // Assuming getAdminProductId() returns a list
                    // adminProductIdItem = adminProductIdItems.get(0);
                }
            }

            @Override
            public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
                Toast.makeText(getContext(), " "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private String productSpecificationsToString(Map<String, String> productSpecifications) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : productSpecifications.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(" ").append("\n");
        }

        // Remove trailing comma and space
        if (sb.length() > 0) sb.setLength(sb.length() - 2);

        return sb.toString();
    }



}