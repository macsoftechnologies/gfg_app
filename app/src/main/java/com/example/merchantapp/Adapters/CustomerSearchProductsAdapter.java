package com.example.merchantapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.CustomerSearchProductsModel.AdminProductIdItem;
import com.example.merchantapp.Model.CustomerSearchProductsModel.DataItem;
import com.example.merchantapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchProductsAdapter extends RecyclerView.Adapter<CustomerSearchProductsAdapter.MyViewholder>  {
    private Context context;
    private List<AdminProductIdItem> adminProductIdItemList;
    private List<DataItem> dataItemList;
    private List<AdminProductIdItem> fullList;



    //private List<CustomerSearchProductsRespoDataItem> dataItemList;

    public interface CustomerSearchProductsRecyclerViewItemClickListener {
        void onItemClickListner(List<DataItem> dataItemList ,int position);
    }

    private CustomerSearchProductsAdapter.CustomerSearchProductsRecyclerViewItemClickListener customerSearchProductsRecyclerViewItemClickListener;

    public void setCustomerSearchProductsRecyclerViewItemClickListener(CustomerSearchProductsAdapter.CustomerSearchProductsRecyclerViewItemClickListener customerSearchProductsRecyclerViewItemClickListener) {
        this.customerSearchProductsRecyclerViewItemClickListener = customerSearchProductsRecyclerViewItemClickListener;
    }


    public CustomerSearchProductsAdapter(Context context, List<AdminProductIdItem> adminProductIdItemList, List<DataItem> dataItemList) {
      this.context = context;
      //this.adminProductIdItemList= adminProductIdItemList;
      this.dataItemList = dataItemList;
        this.adminProductIdItemList = new ArrayList<>(adminProductIdItemList);
        this.fullList = new ArrayList<>(adminProductIdItemList);
    }


    @NonNull
    @Override
    public CustomerSearchProductsAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_products_items, parent, false);
        return new CustomerSearchProductsAdapter.MyViewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerSearchProductsAdapter.MyViewholder holder, int position) {

        com.example.merchantapp.Model.CustomerSearchProductsModel.AdminProductIdItem adminProductIdItem = adminProductIdItemList.get(position);
        holder.customer_product_txt.setText(adminProductIdItem.getProductName());
        String url = "https://api.gfg.org.in/"+adminProductIdItem.getProductImage();
        Glide.with(context)
             .load(url)
                .centerCrop()
                .fitCenter()
                .into(holder.customer_products_img);


    }

    @Override
    public int getItemCount() {
        return adminProductIdItemList.size();
    }


    public void filter(String query) {
        List<AdminProductIdItem> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(fullList); // Show full list if query is empty
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (AdminProductIdItem item : fullList) {
                if (item.getProductName().toLowerCase().contains(filterPattern)) {
                    filteredList.add(item); // Add matching items to the filtered list
                }
            }
        }

        adminProductIdItemList.clear();
        adminProductIdItemList.addAll(filteredList); // Update the adapter's list
        notifyDataSetChanged(); // Notify the adapter about the data changes
    }


    public void setProductList(List<AdminProductIdItem> adminProductIdItemList) {
        this.adminProductIdItemList = adminProductIdItemList;
        notifyDataSetChanged();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        private ImageView customer_products_img;
        private TextView customer_product_txt;
        private CardView searchProductscard;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            customer_products_img = itemView.findViewById(R.id.customer_products_item_imageView);
            customer_product_txt = itemView.findViewById(R.id.customer_products_itemTextview);
            searchProductscard = itemView.findViewById(R.id.searchitems_card);

            searchProductscard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && customerSearchProductsRecyclerViewItemClickListener != null) {
                      //  ProductData productData = products[position];
                        customerSearchProductsRecyclerViewItemClickListener.onItemClickListner(dataItemList ,position);
                    }
                }
            });
        }


    }


}
