package com.example.mainstreet.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainstreet.Interface.ItemClickListner;
import com.example.mainstreet.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView eachShoeName, eachShoePrice, eachShoeBrand, eachShoeDescription;
    public ImageView eachShoeImage;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        eachShoeImage = itemView.findViewById(R.id.eachShoeImage);
        eachShoeName = itemView.findViewById(R.id.eachShoeTitle);
        eachShoePrice = itemView.findViewById(R.id.eachShoePrice);
        eachShoeBrand = itemView.findViewById(R.id.eachShoeBrand);
        eachShoeDescription = itemView.findViewById(R.id.eachShoeDescription);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}
