package com.example.merchantapp.Fragments;

import static com.example.merchantapp.Utilites.Constants.ADMIN_PRODUCTS;
import static com.example.merchantapp.Utilites.Constants.MEDRCHANT_PRODUCTDETALIS_FRAGMENT_TAG;
import static com.example.merchantapp.Utilites.Constants.SWITCH_TO_MERCHANT;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merchantapp.Adapters.MerchantProductsAdapter;
import com.example.merchantapp.Adapters.SearchAdapter;
import com.example.merchantapp.Model.UserModels.Data;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.Model.merchantproductsmodel.DataItem;
import com.example.merchantapp.Model.merchantproductsmodel.MerchantProductsResponse;
import com.example.merchantapp.R;
import com.example.merchantapp.SwitchToMerchantActivity;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView emptyproducts;
    private EditText Searchview;
    private TextView productstext;
    private RecyclerView searchrecylerview,merchant_products_recylerview;
    private SearchAdapter searchAdapter;
    private ImageView Merchant_details_layout;
    private MerchantProductsAdapter merchantProductsAdapter;
    private  String tokens,id,Userid;
    private TextView tokenid,showproductsbtn,mobileno,shopname;
   private List<DataItem> dataItemList = new ArrayList<>();


    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        View view = inflater.inflate(R.layout.fragment_orders, container, false);



        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Products");
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        castingViews(view);



        tokens = UserSessionManagement.getInstance(getContext()).getTokenId();

        id = UserSessionManagement.getInstance(getContext()).getUserid();
        merchant_products_recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        // tokenid.setText(token);

//        Searchview.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (searchrecylerview != null) {
//                    // searchAdapter.getFilter().filter(s.toString());
//                }
//            }
//        });



        showbtn();




        merchantProducts();
        boolean isMerchantLogin = UserSessionManagement.gettBoolean(getContext(),"MERCHANTLOGIN");

        if(isMerchantLogin){
            Merchant_details_layout.setVisibility(View.VISIBLE);
        }


        showproductsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isMerchantLoginSucces = UserSessionManagement.gettBoolean(getContext(),"MERCHANTLOGIN");
                boolean isLoginSucces = UserSessionManagement.getBoolean(getContext(), "LOGIN");
                if(isMerchantLoginSucces){
                        //     fetch();

                    AdminProductList adminProductsFragment = new AdminProductList();

                    addToFragmentContainer(adminProductsFragment, true, ADMIN_PRODUCTS);


                    Merchant_details_layout.setVisibility(View.GONE);
                }
                else if(isLoginSucces) {

                    new AlertDialog.Builder(getContext())
                            .setMessage("If you want to see Products Register as Merchant")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

//                                    startActivity(new Intent(getActivity(), SwitchToMerchantActivity.class));
//                                    getActivity().finish();
                                    SwitchtoMerchantFragment switchtoMerchantFragment = new SwitchtoMerchantFragment();
                                    addToFragmentContainer(switchtoMerchantFragment, true, SWITCH_TO_MERCHANT);

                                }
                            })
                            .setNegativeButton("no", null).show();

                }
            }
        });
       return view;
  }



    private void merchantProducts() {

        Map<String,String> map = new HashMap<>();

        map.put("_id",id);

        ApiClient.getService().userid("Bearer "+tokens,map).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
             if(response.isSuccessful()) {
                 UserModel userModel = response.body();
                 Data userData = userModel.getData();

                Userid = userData.getUserId();
             //   Toast.makeText(getContext(),"id"+Userid,Toast.LENGTH_LONG).show();

              //   mobileno.setText("MobileNo: "+userData.getMobileNumber());
               //  shopname.setText(Userid);
                 merchantProductList();
                // shopname.setText(" "+userData.getShopName());


                 for (String role : userData.getRole()) {
                     if (role.equals("customer")) {
                        // Toast.makeText(getContext(),"No products avaliable",Toast.LENGTH_SHORT).show();
                         productstext.setVisibility(View.VISIBLE);
                         Merchant_details_layout.setVisibility(View.GONE);
                     }
                     else if(role.equals("merchant")){
                         productstext.setVisibility(View.GONE);
                         Merchant_details_layout.setVisibility(View.VISIBLE);

                     }
                 }

             }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), " "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void merchantProductList() {

        Map<String,String> hashmap = new HashMap<>();

        String id = Userid;

        hashmap.put("userId",Userid);






//        ApiClient.getService().merchantuserId("Bearer "+tokens,hashmap).enqueue(new Callback<MerchantProductsResponse>() {
//            @Override
//            public void onResponse(Call<MerchantProductsResponse> call, Response<MerchantProductsResponse> response) {
//                if(response.isSuccessful()){
//
//                    Toast.makeText(getContext(),"merchant+products succesfull",Toast.LENGTH_SHORT).show();
////                    MerchantProductsResponse merchantProductsResponse = response.body();
////
////                    merchant_products_recylerview.setHasFixedSize(true);
////                    merchant_products_recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
////                    merchantProductsAdapter = new MerchantProductsAdapter(getContext(), (List<MerchantProductsResponse>) merchantProductsResponse);
////                    merchant_products_recylerview.setAdapter(merchantProductsAdapter);
////                }
//            }
//
//            @Override
//            public void onFailure(Call<MerchantProductsResponse> call, Throwable t) {
//
//            } Toast.makeText(getContext(),"merchant not succesfull",Toast.LENGTH_SHORT).show();
////
//        });
        ApiClient.getService().merchantuserId("Bearer "+tokens,hashmap).enqueue(new Callback<MerchantProductsResponse>() {
            @Override
            public void onResponse(Call<MerchantProductsResponse> call, Response<MerchantProductsResponse> response) {
                if(response.isSuccessful()) {
                    //Toast.makeText(getContext(),"merchant+products succesfull",Toast.LENGTH_SHORT).show();
                    MerchantProductsResponse responseData = response.body();

                    if (responseData.getStatusCode() == 404) {
                        emptyproducts.setVisibility(View.VISIBLE);
                        //Toast.makeText(getContext(), "not found", Toast.LENGTH_SHORT).show();
                    } else {
                        List<DataItem> merchantProducts = responseData.getData();
                        merchantProductsAdapter = new MerchantProductsAdapter(getContext(), merchantProducts);
                        merchant_products_recylerview.setAdapter(merchantProductsAdapter);


                        final ColorDrawable background = new ColorDrawable(Color.RED);
                        final Drawable deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_delete_24);
                        final int iconMargin = 16;

                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                               // Toast.makeText(getContext(), "swipe to delete", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                int position = viewHolder.getAdapterPosition();

                                String itemId = merchantProducts.get(position).getId(); // Assuming `getId()` returns the item ID

                                merchantProductsAdapter.removeItem(itemId, position);

                                // Show a Snackbar with the "Swipe to delete" message

                            }

                            @Override
                            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                                View itemView = viewHolder.itemView;
                                int itemHeight = itemView.getHeight();

                                if (dX > 0) {
                                    background.setBounds(itemView.getLeft(), itemView.getTop(), (int) dX, itemView.getBottom());
                                } else {
                                    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                                }
                                background.draw(c);

                                int iconTop = itemView.getTop() + (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
                                int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

                                if (dX > 0) {
                                    int iconLeft = itemView.getLeft() + iconMargin;
                                    int iconRight = iconLeft + deleteIcon.getIntrinsicWidth();
                                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                                } else if (dX < 0) {
                                    int iconRight = itemView.getRight() - iconMargin;
                                    int iconLeft = iconRight - deleteIcon.getIntrinsicWidth();
                                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                                }

                                deleteIcon.draw(c);
                                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                            }
                        });

                        itemTouchHelper.attachToRecyclerView(merchant_products_recylerview);

                        merchantProductsAdapter.setMerchantProductsRecyclerViewItemClickListener(new MerchantProductsAdapter.MerchantProductsRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClickListner(List<DataItem> dataItems, int position) {

                                MerchantProductDetailsFragment merchantProductDetailsFragment = new MerchantProductDetailsFragment();
                                Bundle bundle = new Bundle();
                                //    Toast.makeText(getContext(), "idkk"+dataItems.get(position).getId(), Toast.LENGTH_SHORT).show();
                                bundle.putString("id", dataItems.get(position).getId());
                                String price = String.valueOf(dataItems.get(position).getPrice());
                                bundle.putString("price", price);
                                bundle.putString("image", dataItems.get(position).getAdminProductId().get(0).getProductImage());
                                merchantProductDetailsFragment.setArguments(bundle);
                                addToFragmentContainer(merchantProductDetailsFragment, true, MEDRCHANT_PRODUCTDETALIS_FRAGMENT_TAG);


                            }


                        });
                        merchantProductsAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<MerchantProductsResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
////
            }
        });

    }






    private void showbtn() {

//        boolean isMerchantLoginSucces = UserSessionManagement.gettBoolean(getContext(),"MERCHANTLOGIN");
//        boolean isLoginSucces = UserSessionManagement.getBoolean(getContext(), "LOGIN");
//        if(isMerchantLoginSucces){
//            showproductsbtn.setVisibility(View.VISIBLE);
//            merchantProducts();
//            searchrecylerview.setVisibility(View.VISIBLE);
//
//        }
//        else if(isLoginSucces){
//            showproductsbtn.setVisibility(View.VISIBLE);
//            searchrecylerview.setVisibility(View.GONE);
//        }
    }


    private void castingViews(View view) {

      //  searchrecylerview = view.findViewById(R.id.searchRecyclerView);
        tokenid = view.findViewById(R.id.token_id);
        showproductsbtn = view.findViewById(R.id.show_productsbtn);
      //  shopname = view.findViewById(R.id.merchant_shop_name);
        productstext = view.findViewById(R.id.productstext);
       mobileno = view.findViewById(R.id.merchant_number);
        merchant_products_recylerview = view.findViewById(R.id.merchant_products_RecyclerView);
        Merchant_details_layout = view. findViewById(R.id.merchantdetails_layout);
        emptyproducts = view.findViewById(R.id.emptyproducts);

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

//        merchant_products_recylerview.setVisibility(View.VISIBLE);
//        searchrecylerview.setVisibility(View.VISIBLE);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}







