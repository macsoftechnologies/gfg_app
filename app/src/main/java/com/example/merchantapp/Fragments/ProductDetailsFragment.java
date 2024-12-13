package com.example.merchantapp.Fragments;

import static com.example.merchantapp.Utilites.Constants.ORDERS_FRAGMENT_TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.AddProductModel.AddProductResponse;
import com.example.merchantapp.Model.AdminProductbyIdModel.DataItem;
import com.example.merchantapp.Model.AdminProductbyIdModel.ProductResponse;
import com.example.merchantapp.Model.Products.ProductData;
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
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment  extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  //  private OnFragmentInteractionListener mListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProductData[] products;
    private ImageView productimg;
    private Button product_price_btn,close_btn;
   private EditText price;
   private TextView product_name;
    private String id, url,name,userid,adminid,token;
    private TextView productname,brand,mobileno,merchantname,tiitle,shopname,shoplocation;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
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

                products = (ProductData[]) getArguments().getSerializable("categoryModel");

                for (ProductData ids : products) {
                    id = ids.get_id();
                    name = ids.getProductName();
                    //  Toast.makeText(getContext(), "id"+id, Toast.LENGTH_SHORT).show();
                    ;
                }
               // ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add product");
                // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);


        // productData =(ProductData[]) getArguments().getSerializable("categoryModel");


        productid();

        casting(view);

        userid = UserSessionManagement.getInstance(getContext()).getUserids();
        token =UserSessionManagement.getInstance(getContext()).getTokenId();
        product_price_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addprice();
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        // Set the width of the DialogFragment to match the screen width
        if (getDialog() != null && getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes(params);
        }
    }




    private void productid(){
        Map<String,String> body = new HashMap<>();
        body.put("_id",id);
         String token = UserSessionManagement.getInstance(getContext()).getTokenId();
        ApiClient.getService().getProductById("Bearer "+ token,body).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(response.isSuccessful()){
                    ProductResponse productResponse = response.body();
                    List<DataItem> dataItems = productResponse.getData();
                    String specsString = productSpecificationsToString(dataItems.get(0).getProductSpecifications());

                            url ="https://api.gfg.org.in/"+dataItems.get(0).getProductImage();
                           adminid = dataItems.get(0).getAdminProductId();
                           product_name.setText(dataItems.get(0).getProductName());
                            brand.setText(specsString);
                            setTextfields();


                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

//        ApiClient.getService().getProductById("Bearer "+token,body).enqueue(new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//
//
//
//
//
//
//                if (response.isSuccessful() && response.body() != null) {
//                    ProductResponse apiResponse = response.body();
//
//                    if (apiResponse.getStatusCode() == 200) {
//                     //  Product product = apiResponse.getData();
//                       List<DataItem> dataItems = apiResponse.getData();
//                        if (dataItems != null) {
//                            String specsString = productSpecificationsToString(dataItems.get(0).getProductSpecifications());
//
//                            url ="https://gfg.org.in/"+dataItems.get(0).getProductImage();
//                           adminid = dataItems.get(0).getAdminProductId();
//
//                            brand.setText(specsString);
//                            setTextfields();
//                            Toast.makeText(getContext(), "Product Specifications: " + specsString, Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getContext(), "Product not found.", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Response unsuccessful.", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_LONG).show();
//                }
//
//
//
//                    }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                price.setText(" "+t.getMessage());
//
//
//            }
//        });

    }
    private String productSpecificationsToString(Map<String, String> specs) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : specs.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ").append("\n");
        }

        // Remove trailing comma and space
        if (sb.length() > 0) sb.setLength(sb.length() - 2);

        return sb.toString();
    }

//    private void displaySpecifications(ProductSpecifications productSpecifications) {
//        StringBuilder formattedSpecifications = new StringBuilder();
//
//        // Iterate through the key-value pairs and format them
//        for (Map.Entry<String, String> entry : productSpecifications.entrySet()) {
//            formattedSpecifications.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
//        }
//
//        // Set the formatted specifications to the TextView
//        textView.setText(formattedSpecifications.toString());
//    }

    private void casting(View view) {


        brand = view .findViewById(R.id.brand);
        product_price_btn = view.findViewById(R.id.product_price_submit_btn);
        mobileno =view. findViewById(R.id.mobile_no);
        productimg= view.findViewById(R.id.product_image);
        price = view.findViewById(R.id.product_price);
        close_btn = view.findViewById(R.id.close_btn);
        product_name = view.findViewById(R.id.prduct_name);


    }




    private void setTextfields(){

//        tiitle.setText(name);
      //  shopname.setText(shop_name);
        Glide.with(getContext())
                //.load(locationbasedCategoriesModel.getCategories().get(0).getImage())
                .load(url)
                .centerCrop()
                .fitCenter()
                .into(productimg);

    }

    private void addprice(){

        Map<String,String> maps = new HashMap<>();

        maps.put("userId",userid);
        maps.put( "adminProductId",adminid);
        maps.put("price",price.getText().toString());

      ApiClient.getService().addproductPrice("Bearer "+token,maps).enqueue(new Callback<AddProductResponse>() {
          @Override
          public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
              if(response.isSuccessful()){
                  Toast.makeText(getContext(),"succesfull",Toast.LENGTH_SHORT).show();

                  OrdersFragment ordersFragment = new OrdersFragment();
                  addToFragmentContainer(ordersFragment, true, ORDERS_FRAGMENT_TAG);

              }
          }

          @Override
          public void onFailure(Call<AddProductResponse> call, Throwable t) {
              Toast.makeText(getContext(),"unsuccesfull",Toast.LENGTH_SHORT).show();

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


//    private void addToFragmentContainer(Fragment fragment, boolean addbackToStack, String tag) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if (addbackToStack) {
//            fragmentTransaction.addToBackStack(tag);
//        }
//        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        fragmentTransaction.replace(R.id.customer_fragments_container, fragment, tag);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // Handle the back button press here
//                if (getFragmentManager() != null) {
//                    getFragmentManager().popBackStack(); // Navigate back to the previous fragment
//                    return true;
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        /*if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }*/
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
