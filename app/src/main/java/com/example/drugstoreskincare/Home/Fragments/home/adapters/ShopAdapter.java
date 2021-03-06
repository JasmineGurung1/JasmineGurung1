package com.example.drugstoreskincare.Home.Fragments.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drugstoreskincare.R;
import com.example.drugstoreskincare.api.response.Product;
import com.example.drugstoreskincare.singleProductPage.SingleProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter <ShopAdapter.ShopViewHolder>{
    List<Product> productDataList;
    LayoutInflater layoutInflater;
    Context context;//layout tasney kaam garcha




    public ShopAdapter(List<Product> products, Context context) {
        this.productDataList = products;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopViewHolder(layoutInflater.inflate(R.layout.single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.productNameTV.setText(productDataList.get(position).getName());
        if (productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice()==0){
            holder.productPriceTV.setVisibility(View.GONE);
            holder.discountPriceTV.setText(productDataList.get(position).getPrice() + "");
        }
        else {
            holder.discountPriceTV.setText(productDataList.get(position).getDiscountPrice() + "");
            holder.productPriceTV.setText(productDataList.get(position).getPrice() + "");

            Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.productIV);
            holder.mainLL.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    Intent productPage = new Intent(context, SingleProductActivity.class);
                    //productPage.putExtra(SingleProductActivity, (Parcelable) productDataList.get(holder.getAdapterPosition()));
                    context.startActivity(productPage);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
       return productDataList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView productIV;
        TextView productNameTV, productPriceTV, discountPriceTV, discountPercentTV;
        LinearLayout mainLL;
        public ShopViewHolder( View itemView){
            super(itemView);
            productIV = itemView.findViewById(R.id.productIV);
            productNameTV = itemView.findViewById(R.id.productNameTV);
            productPriceTV = itemView.findViewById(R.id.productPriceTV);
            discountPriceTV = itemView.findViewById(R.id.oldPriceTV);
            mainLL = itemView.findViewById(R.id.mainLL);

        }

    }
}