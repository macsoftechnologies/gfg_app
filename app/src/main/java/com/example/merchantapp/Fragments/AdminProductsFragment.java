package com.example.merchantapp.Fragments;

import static com.example.merchantapp.Utilites.Constants.PRODUCTDETALIS_FRAGMENT_TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.merchantapp.Adapters.Ad_CarouselPager;
import com.example.merchantapp.Adapters.SearchAdapter;
import com.example.merchantapp.Adapters.ZoomOutPageTransformer;
import com.example.merchantapp.Model.Products.ProductData;
import com.example.merchantapp.Model.Products.ProductsModel;
import com.example.merchantapp.R;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private SearchAdapter searchAdapter;
    private String mParam2;
    private String productsname;
    private RecyclerView searchrecylerview;

    public final static int LOOPS = 1000;
    public Ad_CarouselPager adapter;
    public static ViewPager pager;
    public static int count = 10; //ViewPager items size


    /**
     * You shouldn't define first page = 0.
     * Let define firstpage = 'number viewpager size' to make endless carousel
     */
    public static int FIRST_PAGE = 10;

    private LinearLayout pager_indicator;
    private ProductData[] globalProducts;
    private int dotsCount;
    private ImageView[] dots;

    public AdminProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProductsFragment newInstance(String param1, String param2) {
        AdminProductsFragment fragment = new AdminProductsFragment();
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
        View view= inflater.inflate(R.layout.fragment_admin_products, container, false);


        Casting(view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ADMIN PRODUCTS");




        //set page margin between pages for viewpager



//        adapter = new Ad_CarouselPager(requireContext(), getChildFragmentManager());
//        pager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//
//        pager.addOnPageChangeListener(adapter);


        // Set current item to the middle page so we can fling to both
        // directions left and right



       // pager.setOnPageChangeListener(this);
//        setPageViewIndicator();
        fetch();


       // Slider();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchrecylerview.setLayoutManager(layoutManager);
        searchrecylerview.setHasFixedSize(true);


        return view;
    }




    private void Casting(View view) {

        searchrecylerview = view.findViewById(R.id.searchRecyclerView);

    }


    private void Slider() {

        int[] images = {R.drawable.fruits, R.drawable.dairy, R.drawable.laptop, R.drawable.m};

        // Set up the adapter
   Ad_CarouselPager adapter = new Ad_CarouselPager(getContext(), images);
      //  pager.setAdapter(adapter);

        // Set the custom PageTransformer for zoom effect
       // pager.setPageTransformer(true,new ZoomOutPageTransformer());
    }







        private void fetch() {
            String tok = UserSessionManagement.getInstance(getContext()).getTokenId();

            ApiClient.getService().getProducts("Bearer " + tok).enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                      //  searchrecylerview.setHasFixedSize(true);
                       // searchrecylerview.setLayoutManager(new LinearLayoutManager(getActivity()));

                        ProductsModel productsModel = response.body();
                        ProductData[] productData = productsModel.getData();


//                        Ad_CarouselPager adapter = new Ad_CarouselPager(getContext(), productData);
//                        pager.setAdapter(adapter);
//                        pager.setPageTransformer(true, new ZoomOutPageTransformer());

                        if (productData != null && productData.length > 0) {

                            // Items might vary in size


                            searchAdapter = new SearchAdapter(getContext(), productData);

                            searchrecylerview.setAdapter(searchAdapter);

                            searchAdapter.setProductsRecyclerViewItemClickListener(new SearchAdapter.ProductsRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClickListner(ProductData[] products) {
                                   // OpencustomProductdetailfragment(products);
                                    List<String> productIds = new ArrayList<>();
                                    for (ProductData product : products) {
                                        String id = product.get_id();
                                        productIds.add(id);

                                    }


                                    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("categoryModel", products);
                                    productDetailsFragment.setArguments(bundle);
                                    productDetailsFragment.show(getChildFragmentManager(),"productdetailsfragment");

                                  //  OpencustomProductdetailfragment();
//                                    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("categoryModel", products);
//                                    productDetailsFragment.setArguments(bundle);
//                                    addToFragmentContainer(productDetailsFragment, true, PRODUCTDETALIS_FRAGMENT_TAG);
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "No items found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No items found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductsModel> call, Throwable t) {
                    Toast.makeText(getContext(), "unsuccess" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    private void OpencustomProductdetailfragment(ProductData[] products) {

      //  MyOtpPopUp dialogFragment = new MyOtpPopUp();

        // Show the dialog fragment using FragmentManager
     //   dialogFragment.show(getChildFragmentManager(), "MyOtpPopUp");


        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("categoryModel", productsname);
        productDetailsFragment.setArguments(bundle);
       // productDetailsFragment.show(getChildFragmentManager(),"productdetailsfragment");
       // addToFragmentContainer(productDetailsFragment, true, PRODUCTDETALIS_FRAGMENT_TAG);

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