package com.example.merchantapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantapp.Model.Products.ProductData;
import com.example.merchantapp.Model.Products.ProductsModel;
import com.example.merchantapp.R;

import java.util.List;

import retrofit2.Callback;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewholder> {
    private Context context;
    private ProductData[] products;





    public interface ProductsRecyclerViewItemClickListener {
        void onItemClickListner(ProductData[] products);
    }

    private ProductsRecyclerViewItemClickListener productsRecyclerViewItemClickListener;

    public void setProductsRecyclerViewItemClickListener(ProductsRecyclerViewItemClickListener productsRecyclerViewItemClickListener) {
        this.productsRecyclerViewItemClickListener = productsRecyclerViewItemClickListener;
    }


    public SearchAdapter(Context context, ProductData[] products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewholder holder, int position) {
//        ProductsModel productsModel = new ProductsModel();
//        ProductData[] productData = productsModel.getData();
//        holder.searchItemNameView.setText(g);
        ProductData productData = products[position];
        holder.searchItemNameView.setText(productData.getProductName());

        String url ="https://api.gfg.org.in/"+productData.getProductImage();
        Glide.with(context)
                //.load(locationbasedCategoriesModel.getCategories().get(0).getImage())
                .load(url)
                .centerCrop()
                .fitCenter()
                .into(holder.itemImgView);



    }

    @Override
    public int getItemCount() {
        if (products != null) {
            return products.length;
        } else {
            return 0; // Return 0 if productData is null to avoid NullPointerException
        }


    }
    public class MyViewholder extends RecyclerView.ViewHolder {
        private TextView searchItemNameView;
        private ImageView itemImgView;
        private TextView additem;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            searchItemNameView = itemView.findViewById(R.id.itemTextview);
            itemImgView = itemView.findViewById(R.id.imageView);
            additem = itemView.findViewById(R.id.add_item);

            additem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && productsRecyclerViewItemClickListener != null) {
                        ProductData productData = products[position];
                        productsRecyclerViewItemClickListener.onItemClickListner(new ProductData[]{productData});
                    }
                }
            });

        }


    }

}
