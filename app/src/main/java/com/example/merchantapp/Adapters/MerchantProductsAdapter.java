package com.example.merchantapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.DeleteProductModel.DeleteProductResponse;
import com.example.merchantapp.Model.merchantproductsmodel.AdminProductIdItem;
import com.example.merchantapp.Model.merchantproductsmodel.DataItem;
import com.example.merchantapp.R;
import com.example.merchantapp.Utilites.UserSessionManagement;
import com.example.merchantapp.server.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantProductsAdapter extends RecyclerView.Adapter<MerchantProductsAdapter.MyViewholder> {
    private Context context;
   private List<DataItem> dataItems;
 // String token = UserSessionManagement.getInstance(getApplicationContext()).getTokenId();
//    private AdminProductIdItem[] products;
//    private DataItem[] dataItem;

    public interface MerchantProductsRecyclerViewItemClickListener {
        void onItemClickListner(List<DataItem> dataItems,int position);
    }

    private MerchantProductsAdapter.MerchantProductsRecyclerViewItemClickListener merchantProductsRecyclerViewItemClickListener;

    public void setMerchantProductsRecyclerViewItemClickListener(MerchantProductsAdapter.MerchantProductsRecyclerViewItemClickListener merchantproductsRecyclerViewItemClickListener) {
        this.merchantProductsRecyclerViewItemClickListener = merchantproductsRecyclerViewItemClickListener;
    }


    public MerchantProductsAdapter(Context context,List<DataItem> dataItems ) {
        this.context = context;
        this.dataItems = dataItems != null ? dataItems : new ArrayList<>();

    }

    @NonNull
    @Override
    public MerchantProductsAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.merchant_products_item, parent, false);
        return new MerchantProductsAdapter.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantProductsAdapter.MyViewholder holder, int position) {
//        AdminProductIdItem adminProductIdItem = products[position];
//        DataItem dataItem1 = dataItem[position];
        DataItem dataItem = dataItems.get(position);
        AdminProductIdItem adminProduct = dataItem.getAdminProductId().get(0);


        holder.merchant_item_txt.setText(adminProduct.getProductName().trim());
        holder.price_txt.setText(String.valueOf("₹ "+dataItem.getPrice()));

        // Load product image using Glide or any other image loading library
        String imageUrl = "https://api.gfg.org.in/" + adminProduct.getProductImage();
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.merchant_img);
    }



    @Override
    public int getItemCount() {
        return dataItems.size();
    }

//    public void removeItem(String itemId, int position) {
//        dataItems.remove(position);
//        notifyItemRemoved(position);
//    }

    public void removeItem(String itemId, int position) {
        // Call the delete API
        deleteProduct(itemId, position);
    }

    // Method to call the deleteProduct API
    private void deleteProduct(String itemId, int position) {
        String token = UserSessionManagement.getInstance(context).getTokenId();
        Map<String, String> maps = new HashMap<>();
        maps.put("_id", itemId);

        ApiClient.getService().deleteProduct("Bearer " + token, maps).enqueue(new Callback<DeleteProductResponse>() {
            @Override
            public void onResponse(Call<DeleteProductResponse> call, Response<DeleteProductResponse> response) {
                if (response.isSuccessful()) {
                    DeleteProductResponse deleteProductResponse = response.body();
                    Toast.makeText(context, deleteProductResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Remove item from the adapter's data list after successful deletion
                    dataItems.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Failed to delete the item.", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position); // Reset the item position if deletion fails
                }
            }

            @Override
            public void onFailure(Call<DeleteProductResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                notifyItemChanged(position); // Reset the item position if deletion fails
            }
        });
    }


    public class MyViewholder extends RecyclerView.ViewHolder {
        private ImageView merchant_img;
        private CardView merchantitems;
        private TextView price_txt,merchant_item_txt;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            merchant_img = itemView.findViewById(R.id.merchant_item_imageView);
            price_txt = itemView.findViewById(R.id.merchant_item_price);
            merchantitems = itemView.findViewById(R.id.merchantitemscard);
            merchant_item_txt =itemView.findViewById(R.id.merchant_itemTextview);

            merchantitems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && merchantProductsRecyclerViewItemClickListener != null) {
//                        DataItem dataItem = dataItems.get(position);
                        merchantProductsRecyclerViewItemClickListener.onItemClickListner(dataItems,position);
                    }
                }
            });

        }
    }

}
