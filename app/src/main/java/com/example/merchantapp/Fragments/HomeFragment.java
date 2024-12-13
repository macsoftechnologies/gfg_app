package com.example.merchantapp.Fragments;

import static com.example.merchantapp.Utilites.Constants.ADDPRODUCT_FRAGMENT;
import static com.example.merchantapp.Utilites.Constants.PROGFILE_FRAGMENT;
import static com.example.merchantapp.Utilites.Constants.SWITCH_TO_MERCHANT;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Adapters.ImageAdapter;
import com.example.merchantapp.Adapters.ImageElectricAdapter;
import com.example.merchantapp.Adapters.ImagePagerAdapter;
import com.example.merchantapp.CustomerMainActivity;
import com.example.merchantapp.Model.AdvertismentsModel.AdvertisementsResponse;

import com.example.merchantapp.Model.CategoriesModel.CategoriesResponse;
import com.example.merchantapp.Model.CategoriesModel.DataItem;
import com.example.merchantapp.Model.UserModels.Data;
import com.example.merchantapp.Model.UserModels.UserModel;
import com.example.merchantapp.R;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;
import com.example.merchantapp.server.OnBackPressedListener;
import com.google.android.material.slider.Slider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     private RecyclerView recyclerView,electricrecyclerView;
     private ImageElectricAdapter adapters;
    private ImageAdapter adapter;
    private Slider slider;
    private TextView username;
    private TextView productname;
    private Context context;
    private String lng,lat,token;
    private ViewPager viewPager;
    private ImagePagerAdapter imagePagerAdapter;
    private String name;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 3000; // Delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 3000;
    private EditText serachview;
  //  private Context context;
    private ImageView cart,user_image,search_bar,carts_vechile;
    private String id;

   private View productline;
    // Array of image resources
    private int[] imageIds = {R.drawable.fruits, R.drawable.vegetables1, R.drawable.dairyproducts,R.drawable.pulses1};
    private int[] electricimgs ={R.drawable.fanimg,R.drawable.fridgeimg,R.drawable.spects,R.drawable.hometheatre};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    // Instance variable to hold reference to the listener
//    private OnBackPressedListener onBackPressedListener;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();



        id = UserSessionManagement.getInstance(getContext()).getUserid();
        recyclerView = view.findViewById(R.id.grocery_recyclerview);
         productline = view.findViewById(R.id.productline);
        electricrecyclerView = view.findViewById(R.id.electric_recyclerview);
        viewPager = view.findViewById(R.id.viewPager);
        search_bar = view.findViewById(R.id.toolsearchBarIcon);
         carts_vechile = view.findViewById(R.id.carts);
      //  username = view.findViewById(R.id.user_name);
        user_image = view.findViewById(R.id.user_image);
        name = UserSessionManagement.getInstance(getContext()).getName();
      //  serachview = view.findViewById(R.id.cust_home_searchView);
        cart = view.findViewById(R.id.cart_vechile);
        productname = view.findViewById(R.id.productsname);

       token= UserSessionManagement.getInstance(getContext()).getTokenId();
       lat = UserSessionManagement.getInstance(getContext()).getLatitude();
       lng =  UserSessionManagement.getInstance(getContext()).getLongitude();



//       System.out.println("lat"+lng);
//       Toast.makeText(getContext(),"latyu"+lng,Toast.LENGTH_LONG).show();
//       Toast.makeText(getContext(),"lang"+lat,Toast.LENGTH_LONG).show();


        float distance = 150f; // Equivalent to about 2 meters in terms of screen pixels

        // Create the left-to-right animation
        ObjectAnimator moveRight = ObjectAnimator.ofFloat(productline, "translationX", distance);
        moveRight.setDuration(1000);
        // 1 second duration


        // Create the right-to-left animation (return to start)
        ObjectAnimator moveLeft = ObjectAnimator.ofFloat(productline, "translationX", -distance);
        moveLeft.setDuration(1000);  // 1 second duration
        ObjectAnimator moveBackToCenter = ObjectAnimator.ofFloat(productline, "translationX", 0f);
        moveBackToCenter.setDuration(1000);

        ObjectAnimator moveLeftt = ObjectAnimator.ofFloat(productname, "translationX", -distance);
        moveLeftt.setDuration(1000);
        ObjectAnimator moveRightt = ObjectAnimator.ofFloat(productname, "translationX", distance);
        moveRightt.setDuration(1000);
        ObjectAnimator Center = ObjectAnimator.ofFloat(productname, "translationX", 0f);
        Center.setDuration(1000);  // 1 se

        // Combine the animations
       // 1 second duration

        // Combine the animations
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet ani = new AnimatorSet();
        animatorSet.playSequentially(moveRight, moveLeft, moveBackToCenter);
        ani.playSequentially(moveLeftt,moveRightt,Center);

        ani.start();
        // Start the animation
        animatorSet.start();

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileFragment profileFragment = new ProfileFragment();
                addToFragmentContainer(profileFragment, true, PROGFILE_FRAGMENT);
                ((CustomerMainActivity) getActivity()).setProfileIconColor("#0a7094");  // Replace with your activity's name

                //  bottomNavigationView.setItemIconTintList(createColorStateList("#0a7094"));

            }
        });


//        ObjectAnimator moveForwardAnimator = ObjectAnimator.ofFloat(cart, "translationX", 1000f);
//        moveForwardAnimator.setDuration(1000); // Animation duration in milliseconds
//
//// Create the ObjectAnimator for moving the ImageView back to its original position.
//        ObjectAnimator moveBackAnimator = ObjectAnimator.ofFloat(cart, "translationX", 500f);
//        moveBackAnimator.setDuration(1000); // Animation duration in milliseconds
//
//// Create an AnimatorSet to play the animations sequentially.
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(moveForwardAnimator, moveBackAnimator);
//
//// Add an AnimatorListener to the AnimatorSet to set the visibility after the animation ends.
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // Set the ImageView to be visible after the animation ends.
//                cart.setVisibility(View.GONE);
//
//                search_bar.setVisibility(View.VISIBLE);
//                carts_vechile.setVisibility(View.VISIBLE);
//            }
//        });
//
//// Start the animation.
//        animatorSet.start();
////

      sliders();

      UserProfile();
//        serachview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrdersFragment ordersFragment = new OrdersFragment();
////                Bundle bundle = new Bundle();
////
////                bundle.putSerializable("categoryModel", products);
////                productDetailsFragment.setArguments(bundle);
//                addToFragmentContainer(ordersFragment, true, ORDERS_FRAGMENT_TAG);
//
//            }
//        });



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mykey", Context.MODE_PRIVATE);
            String value = sharedPreferences.getString("value", "");




            recyclerView.setLayoutManager(new  GridLayoutManager(getContext(), 3));
//
//        // Initialize adapter and set it to RecyclerView
//        adapter = new ImageAdapter(imageIds);
//        recyclerView.setAdapter(adapter);
        fetch();



        electricrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapters = new ImageElectricAdapter(electricimgs);
        electricrecyclerView.setAdapter(adapters);




        return view;
    }

    private void UserProfile() {


            Map<String,String> maps = new HashMap<>();
            maps.put("_id",id);


            ApiClient.getService().userid("Bearer "+token,maps).enqueue(new Callback<UserModel>() {
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
                                .into(user_image);


                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

                }
            });

    }

    private void fetch() {
       ApiClient.getService().getCategorys().enqueue(new Callback<CategoriesResponse>() {
           @Override
           public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {

               if(response.isSuccessful()){

                   CategoriesResponse categoriesResponse = response.body();
                   List<DataItem> categoryList = categoriesResponse.getData();
                   adapter = new ImageAdapter(getContext(), categoryList);

                   adapter.setImageAdapterRecyclerViewItemClickListener(new ImageAdapter.ImageAdapterRecyclerViewItemClickListener() {
                       @Override
                       public void onItemClickListner(List<DataItem> dataItemList, int position) {
                           AddProductFragment addProductFragment = new AddProductFragment();

                           Bundle bundle = new Bundle();
                           bundle.putString("categoryid",dataItemList.get(position).getCategoryId());
                           addProductFragment.setArguments(bundle);
                           addToFragmentContainer(addProductFragment,true,ADDPRODUCT_FRAGMENT);



                       }
                   });

                   recyclerView.setAdapter(adapter);
               } else {
                   Toast.makeText(getContext(), "Failed to retrieve categories", Toast.LENGTH_SHORT).show();
               }


           }

           @Override
           public void onFailure(Call<CategoriesResponse> call, Throwable t) {
               Toast.makeText(getContext(), "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();

           }
       });

    }



    private void sliders() {
        Map<String,String> maps = new HashMap<>();

        maps.put("longitude",UserSessionManagement.getInstance(getContext()).getLongitude());
        maps.put("latitude",UserSessionManagement.getInstance(getContext()).getLatitude());


        ApiClient.getService().getAdvertisments("Bearer "+token,maps).enqueue(new Callback<AdvertisementsResponse>() {
            @Override
            public void onResponse(Call<AdvertisementsResponse> call, Response<AdvertisementsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> images = response.body().getImages();
                    ImagePagerAdapter adapter = new ImagePagerAdapter(getContext(), images);
                    viewPager.setAdapter(adapter);
                  //  Toast.makeText(getActivity(,"sucee",Toast.LENGTH_LONG).show();
                    startAutoSlide();
                    user_image.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AdvertisementsResponse> call, Throwable t) {
                Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop automatic sliding when the fragment's view is destroyed
        //stopAutoSlide();
    }

    private void startAutoSlide() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == adapter.getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        // Initialize TimerTask to run slideshow
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void stopAutoSlide() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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


//    @Override
//    public void onBackPressed() {
//       Toast.makeText(getContext(),":jj",Toast.LENGTH_LONG).show();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            Fragment currentFragment = fragmentManager.findFragmentById(R.id.customer_fragments_container);
//
//            if (currentFragment instanceof HomeFragment) {
//                // If the current fragment is HomeFragment, close the app
//                 getActivity().finish();
//            }
//
//        }

    }




