package com.example.merchantapp.Fragments;

import static com.android.volley.VolleyLog.TAG;
import static com.example.merchantapp.Utilites.Constants.MERCHANT_DETAILS_FRAGMENT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.Adapters.CustomerSearchProductsAdapter;
import com.example.merchantapp.Model.CustomerSearchProductsModel.AdminProductIdItem;
import com.example.merchantapp.Model.CustomerSearchProductsModel.CustomerSearchProductsResponse;
import com.example.merchantapp.Model.CustomerSearchProductsModel.DataItem;
import com.example.merchantapp.Model.GoogleAPiModel.GeocodeResponse;
import com.example.merchantapp.Model.GoogleAPiModel.ResultsItem;
import com.example.merchantapp.Model.UserModels.Data;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.R;
import com.example.merchantapp.SearchProductsMapActivity;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;
import com.example.merchantapp.server.MapMyIndiaApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1,tokens;
   private TextView addressid;
    private static final int SEARCH_MAP_REQUEST_CODE = 1001;
    private String mParam2;
    private RecyclerView cust_products_recylerview;
    private TextView add_specifications,Submitbtn,show_layout_btn,address,updatedaddress;
    private EditText customsearch_editText;
    private String lat,lng,categoryid;
    private String latitude,longitude;
    private ListView product_list_view;
    private CustomerSearchProductsAdapter customerSearchProductsAdapter;
    private ImageView addimage,search_bar;
    private ImageView search_location;
    private ScrollView products_layout;
    private ProgressBar circularprogressbar;
    private String lastQuery = "";
    private String userid,token;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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
        tokens = UserSessionManagement.getInstance(getContext()).getTokenId();
//        lat = UserSessionManagement.getInstance(getContext()).getLatitude();
//        lng =  UserSessionManagement.getInstance(getContext()).getLongitude();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments() != null){

            categoryid = getArguments().getString("categoryid");
            UserSessionManagement.getInstance(getContext()).setCategoryId(categoryid);
          //  Toast.makeText(getContext(), " categryid"+categoryid, Toast.LENGTH_SHORT).show();

        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        userid =UserSessionManagement.getInstance(getContext()).getUserid();
        token = UserSessionManagement.getInstance(getContext()).getTokenId();
        longitude =UserSessionManagement.getInstance(getContext()).getLongitude();
        latitude =UserSessionManagement.getInstance(getContext()).getLatitude();

        addresprofile();
        searchProducts();

        castingViews(view);





        cust_products_recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));



//         cust_products_recylerview =view.findViewById(R.id.customer_products_recylerview);
//         customsearch_editText = view.findViewById(R.id.cust_home_searchView);

        search_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchProductsMapActivity.class);
                startActivityForResult(intent, SEARCH_MAP_REQUEST_CODE);

            }
        });
//
//

        customsearch_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customerSearchProductsAdapter.filter(s.toString());
                cust_products_recylerview.scrollToPosition(0); // Scroll to the top after filtering
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void addresprofile() {
        Map<String,String> body = new HashMap<>();

        body.put("_id",userid);

        ApiClient.getService().userid("Bearer "+token,body).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    Data userData = userModel.getData();

                    String add = userData.getAddress(); // Original address

// Split the address by comma
                    String[] addressParts = add.split(",");

// Concatenate parts of the address up to "Visakhapatnam"
                    StringBuilder extractedAddress = new StringBuilder();
                    for (String part : addressParts) {
                        extractedAddress.append(part.trim());
                        if (part.trim().equalsIgnoreCase("Visakhapatnam")) {
                            break; // Stop appending after reaching "Visakhapatnam"
                        }
                        extractedAddress.append(", "); // Add a comma between parts
                    }

// Set the extracted address
                    String finalAddress = extractedAddress.toString();


                    address.setText(finalAddress);







                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_MAP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {


                lat = String.valueOf(data.getDoubleExtra("latitude", 0.0)); // Get latitude as double
                lng = String.valueOf(data.getDoubleExtra("longitude", 0.0));
                updatedaddres();
                searchProducts();
              //  Toast.makeText(getContext(), "Selected Location: " + lat + ", " + lng, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updatedaddres() {

        String latlng = lat+","+lng;

        MapMyIndiaApiService geocodeService = ApiClient.getGeocodeService();
        Call<GeocodeResponse> call = geocodeService.getGeocode(latlng,"AIzaSyCiUU7Q5X1hTMRAJr0YJZPOxw40FfZcZp0");

        call.enqueue(new Callback<GeocodeResponse>() {
            @Override
            public void onResponse(Call<GeocodeResponse> call, Response<GeocodeResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    GeocodeResponse geocodeResponse = response.body();
                    List<ResultsItem> resultsItems = geocodeResponse.getResults();

                    String add = resultsItems.get(0).getFormattedAddress(); // Original address
                    Toast.makeText(getContext(), "add: " + add, Toast.LENGTH_SHORT).show();

// Split the address by comma
                    String[] addressParts = add.split(",");

// Concatenate parts of the address up to "Visakhapatnam"
                    StringBuilder extractedAddress = new StringBuilder();
                    for (String part : addressParts) {
                        extractedAddress.append(part.trim());
                        if (part.trim().equalsIgnoreCase("Visakhapatnam")) {
                            break; // Stop appending after reaching "Visakhapatnam"
                        }
                        extractedAddress.append(", "); // Add a comma between parts
                    }

// Set the extracted address
                    String finalAddress = extractedAddress.toString();
                    updatedaddress.setVisibility(View.VISIBLE);
                    address.setVisibility(View.GONE);
                    updatedaddress.setText(finalAddress);

                  //  searchProducts();

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

    private void searchProducts() {
        Map<String, String> params = new HashMap<>();

         if(lat!=null){
             params.put("categoryId", UserSessionManagement.getInstance(getContext()).getCategoryId());
            // Toast.makeText(getContext(), "latt", Toast.LENGTH_SHORT).show();
             params.put("longitude",lng);
             params.put("latitude", lat);


         }
         else{
             params.put("categoryId", UserSessionManagement.getInstance(getContext()).getCategoryId());
            // Toast.makeText(getContext(), "lang", Toast.LENGTH_SHORT).show();

             params.put("longitude",longitude);
             params.put("latitude", latitude);
         }


       // }

        ApiClient.getService().searchProducts("Bearer "+tokens,params).enqueue(new Callback<CustomerSearchProductsResponse>() {
            @Override
            public void onResponse(Call<CustomerSearchProductsResponse> call, Response<CustomerSearchProductsResponse> response) {
                if(response.isSuccessful()){
                    CustomerSearchProductsResponse customerSearchProductsResponse = response.body();

                    if(customerSearchProductsResponse.getStatusCode() == 404){
                        Toast.makeText(getContext(),"No products found",Toast.LENGTH_SHORT).show();
                        cust_products_recylerview.setVisibility(View.GONE);

                    }
                    else {
                        cust_products_recylerview.setVisibility(View.VISIBLE);
                        circularprogressbar.setVisibility(View.GONE);
                        if (customerSearchProductsResponse != null && customerSearchProductsResponse.getData() != null) {

                            List<AdminProductIdItem> adminProductIdItemList = new ArrayList<>();
                            List<DataItem> dataItems = customerSearchProductsResponse.getData();
                            for (DataItem dataItem : customerSearchProductsResponse.getData()) {
                                adminProductIdItemList.addAll(dataItem.getAdminProductId());
                            }


                            customerSearchProductsAdapter = new CustomerSearchProductsAdapter(getContext(), adminProductIdItemList, dataItems);
                            cust_products_recylerview.setAdapter(customerSearchProductsAdapter);

                            customerSearchProductsAdapter.setCustomerSearchProductsRecyclerViewItemClickListener(new CustomerSearchProductsAdapter.CustomerSearchProductsRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClickListner(List<DataItem> dataItemList, int position) {

                                    MerchantDetailsFragment merchantDetailsFragment = new MerchantDetailsFragment();
                                    Bundle bundle = new Bundle();

                                    bundle.putString("id", dataItemList.get(position).get_id());
                                    merchantDetailsFragment.setArguments(bundle);
                                    addToFragmentContainer(merchantDetailsFragment, true, MERCHANT_DETAILS_FRAGMENT);
//                                    MerchantProductDetailsFragment merchantProductDetailsFragment = new MerchantProductDetailsFragment();
//
//                                    addToFragmentContainer(merchantProductDetailsFragment, true, MEDRCHANT_PRODUCTDETALIS_FRAGMENT_TAG);
                                }
                            });
                        }
                    }


                } else {
                    Toast.makeText(getContext(), "Error occured", Toast.LENGTH_SHORT).show();
                  //  cust_products_recylerview.setVisibility(View.GONE);
                }
            }


            @Override
            public void onFailure(Call<CustomerSearchProductsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Errorrr"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void castingViews(View view) {

        cust_products_recylerview =view.findViewById(R.id.customer_products_recylerview);
       customsearch_editText = view.findViewById(R.id.cust_home_searchView);
       search_location = view.findViewById(R.id.productionLocationMap);
       search_bar = view.findViewById(R.id.search_bar);
       address = view.findViewById(R.id.addressid);
       updatedaddress = view.findViewById(R.id.updateaddress);
       circularprogressbar = view.findViewById(R.id.circularProgressBar);

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

    @Override
    public void onResume() {
        super.onResume();
        Log.d("AddProductFragment", "onResume called");
        updatedaddres();
        //searchProducts();
    }


}