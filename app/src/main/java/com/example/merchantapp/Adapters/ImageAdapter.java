package com.example.merchantapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.merchantapp.Model.CategoriesModel.DataItem;
import com.example.merchantapp.R;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<DataItem> dataItemList;


    public interface ImageAdapterRecyclerViewItemClickListener {
        void onItemClickListner(List<DataItem> dataItemList , int position);
    }

    private  ImageAdapter.ImageAdapterRecyclerViewItemClickListener imageAdapterRecyclerViewItemClickListener;

    public void setImageAdapterRecyclerViewItemClickListener(ImageAdapter.ImageAdapterRecyclerViewItemClickListener imageAdapterRecyclerViewItemClickListener) {
        this.imageAdapterRecyclerViewItemClickListener = imageAdapterRecyclerViewItemClickListener;
    }

    public ImageAdapter(Context context,List<DataItem> dataItemList) {
        this.context = context;
        this.dataItemList = dataItemList;
    }



    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groccery_list, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        DataItem category = dataItemList.get(position);
        holder.categoriesname.setText(category.getCategoryName());
        Glide.with(context)
                .load("https://api.gfg.org.in/" + category.getCategoryImage())
                .centerCrop()// Update BASE_URL accordingly
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView categoriesname;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.customer_service_image);
            categoriesname = itemView.findViewById(R.id.customer_service_name);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && imageAdapterRecyclerViewItemClickListener != null) {
                        //  ProductData productData = products[position];

                        imageAdapterRecyclerViewItemClickListener.onItemClickListner(dataItemList, position);
                    }
                }
            });
        }


    }

}
