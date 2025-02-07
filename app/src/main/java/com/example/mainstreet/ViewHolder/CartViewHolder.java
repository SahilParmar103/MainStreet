package com.example.mainstreet.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainstreet.Interface.ItemClickListner;
import com.example.mainstreet.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductPrice, txtProductBrand, itemQuantity, totalPrice;
    public ImageView eachCartShoeImage;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.eachCartShoeName);
        txtProductPrice = itemView.findViewById(R.id.eachCartShoePrice);
        txtProductBrand = itemView.findViewById(R.id.eachCartShoeBrand);
        eachCartShoeImage = itemView.findViewById(R.id.eachCartShoeImage);
        itemQuantity = itemView.findViewById(R.id.eachCartItemQuantity);
        totalPrice = itemView.findViewById(R.id.cartTotalPrice);


    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}