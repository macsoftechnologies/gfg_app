package com.example.merchantapp.Fragments;

import static com.example.merchantapp.Utilites.Constants.MEDRCHANT_PRODUCTDETALIS_FRAGMENT_TAG;
import static com.example.merchantapp.Utilites.Constants.ORDERS_FRAGMENT_TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.DeleteProductModel.DeleteProductResponse;
import com.example.merchantapp.Model.EditProductModel.EditProductResponse;
import com.example.merchantapp.Model.ProductbhyIdModel.AdminProductIdItem;
import com.example.merchantapp.Model.ProductbhyIdModel.ProductByIdResponse;
import com.example.merchantapp.Model.ProductbhyIdModel.ProductSpecifications;
import com.example.merchantapp.Model.merchantproductsmodel.DataItem;
import com.example.merchantapp.Model.merchantproductsmodel.MerchantProductsResponse;
import com.example.merchantapp.R;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantProductDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DataItem dataItem;
    private ImageView merchant_product_image;
    private TextView productname,productspecifications;
    private Button submitbtn,deletebtn;
    private TextInputLayout editprice,edeitinprice;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private  String token,ids,price,image;
    private String mParam2;
    private Serializable id;
    private boolean isedit = false;

    public MerchantProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MerchantProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MerchantProductDetailsFragment newInstance(String param1, String param2) {
        MerchantProductDetailsFragment fragment = new MerchantProductDetailsFragment();
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

        if(getArguments() != null){

            ids = getArguments().getString("id");
            price = getArguments().getString("price");
            image = getArguments().getString("image");
           // editprice.getEditText().setText(price);


           // Toast.makeText(getContext(),image+"kk",Toast.LENGTH_SHORT).show();


        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Product");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_product_details, container, false);
        token = UserSessionManagement.getInstance(getContext()).getTokenId();





        Castings(view);

      Merchantproducts();
        return view;

    }



    private void Merchantproducts() {
        Map<String,String> maps = new HashMap<>();
        maps.put("_id",ids);

  ApiClient.getService().productbYId("Bearer "+token,maps).enqueue(new Callback<ProductByIdResponse>() {
      @Override
      public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
          if(response.isSuccessful()){
              ProductByIdResponse productByIdResponse = response.body();
//              AdminProductIdItem adminProductIdItem = (AdminProductIdItem) productByIdResponse.getData().get(0).getAdminProductId();
              com.example.merchantapp.Model.ProductbhyIdModel.DataItem dataItem = productByIdResponse.getData().get(0);
              List<AdminProductIdItem> adminProductIdItems = dataItem.getAdminProductId();
            // Assuming getAdminProductId() returns a list
              AdminProductIdItem adminProductIdItem = adminProductIdItems.get(0);
              String pricee = String.valueOf(dataItem.getPrice());
              editprice.getEditText().setText(pricee);
              // Assuming you want the first item
              String url = "https://api.gfg.org.in/"+dataItem.getAdminProductId().get(0).getProductImage();
              Glide.with(requireContext())
                            //.load(locationbasedCategoriesModel.getCategories().get(0).getImage())
                            .load(url)
                            .centerCrop()
                            .fitCenter()
                            .into(merchant_product_image);

              productname.setText(adminProductIdItem.getProductName());
              String specsString = productSpecificationsToString(adminProductIdItems.get(0).getProductSpecifications());
              productspecifications.setText(specsString);



          }
      }

      @Override
      public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
          Toast.makeText(getContext(), " "+t.getMessage(), Toast.LENGTH_SHORT).show();
      }
  });

    }


    private String productSpecificationsToString(Map<String, String> specs){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : specs.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append(" ").append("\n");
        }

        // Remove trailing comma and space
        if (sb.length() > 0) sb.setLength(sb.length() - 2);

        return sb.toString();
    }


    private void Castings(View view) {

        productname = view.findViewById(R.id.merchant_product_name);
        submitbtn =view.findViewById(R.id.product_submit_btn);
        deletebtn = view.findViewById(R.id.product_delete_btn);
        productspecifications =view.findViewById(R.id.merchant_product_specifications);
        editprice = view.findViewById(R.id.edit_price);
        merchant_product_image = view.findViewById(R.id.merchant_product_image);
        edeitinprice = view.findViewById(R.id.editing_price);



//        editprice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                edeitinprice.setVisibility(View.VISIBLE);
//                editprice.setVisibility(View.GONE);
//
//            }
//        });


       submitbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditApi();
           }
       });

       deletebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteProduct();
           }
       });

    }

    private void deleteProduct() {
        Map<String,String> maps = new HashMap<>();
        maps.put("_id",ids);

        ApiClient.getService().deleteProduct("Bearer "+token,maps).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if(response.isSuccessful()){
                    DeleteProductResponse deleteProductResponse = response.body();
                    Toast.makeText(getContext(), ""+deleteProductResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    OrdersFragment ordersFragment = new OrdersFragment();
                    addToFragmentContainer(ordersFragment, true, ORDERS_FRAGMENT_TAG);

                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void EditApi() {
        Map<String,String> mapout = new HashMap<>();
        mapout.put("_id",ids);
        mapout.put("price",editprice.getEditText().getText().toString());

      ApiClient.getService().editMerchantProducts("Bearer "+token,mapout).enqueue(new Callback<EditProductResponse>() {
          @Override
          public void onResponse(Call<EditProductResponse> call, Response<EditProductResponse> response) {
              if(response.isSuccessful()){
                  EditProductResponse editProductResponse = response.body();
                  Toast.makeText(getContext(), ""+editProductResponse.getMessage(), Toast.LENGTH_SHORT).show();

                  OrdersFragment ordersFragment = new OrdersFragment();
                  addToFragmentContainer(ordersFragment, true, ORDERS_FRAGMENT_TAG);


              }
          }

          @Override
          public void onFailure(Call<EditProductResponse> call, Throwable t) {
              Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

          }
      });


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

}