package com.example.merchantapp.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.merchantapp.R;

public class ImageElectricAdapter extends RecyclerView.Adapter<ImageElectricAdapter.ImageElectricViewHolder> {


    private int[] image;

    public ImageElectricAdapter(int[] images) {
        this.image = images;
    }

    @NonNull
    @Override
    public ImageElectricAdapter.ImageElectricViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.electric_list_item, parent, false);
        return new ImageElectricAdapter.ImageElectricViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageElectricAdapter.ImageElectricViewHolder holder, int position) {
        holder.image.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public static class ImageElectricViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        public ImageElectricViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.customer_electric_image);
        }
    }
}
