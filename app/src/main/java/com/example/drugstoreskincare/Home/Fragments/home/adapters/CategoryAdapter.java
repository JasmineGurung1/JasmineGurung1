package com.example.drugstoreskincare.Home.Fragments.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.response.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    LayoutInflater inflater;
    Context context;
    Boolean showImage;

    public CategoryAdapter(List<Category> categories, Context context, boolean showImage) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.showImage = showImage;


    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(showImage)
            return new CategoryViewHolder(inflater.inflate(R.layout.item_category,parent,false));
        else
            return new CategoryViewHolder(inflater.inflate(R.layout.item_categoryimage,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.catNameTV.setText(categories.get(position).getName());
       // Picasso.get().load(categories.get(position).getCategoryImage()).into(holder.catIV);
        holder.categoryItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }

    @Override
    public int getItemCount() {

        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView catIV;
        TextView catNameTV;
        LinearLayout categoryItemLL;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catIV = itemView.findViewById(R.id.catIV);
            catNameTV = itemView.findViewById(R.id.catNameTV);
            categoryItemLL = itemView.findViewById(R.id.categoryItemLL);
        }
    }
}




