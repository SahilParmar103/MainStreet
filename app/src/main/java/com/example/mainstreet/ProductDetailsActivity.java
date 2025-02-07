package com.example.mainstreet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mainstreet.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productBrand, productDescription, productQuantity;
    private Button addToCartButton, addQuantity, removeQuantity;
    Products products;
    private int finalQuantity = 1;
    private String productID = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        productImage = findViewById(R.id.detailShoeImg);
        productName = findViewById(R.id.detailShoeName);
        productPrice = findViewById(R.id.detailShoePrice);
        productBrand = findViewById(R.id.detailShoeBrand);
        productDescription = findViewById(R.id.detailShoeDesc);
        addToCartButton = findViewById(R.id.detailAddToCart);
        productQuantity = findViewById(R.id.itemQuantity);
        addQuantity = findViewById(R.id.addQuantity);
        removeQuantity = findViewById(R.id.removeQuantity);

        getProductDetails(productID);

        Log.d("quantity", String.valueOf(finalQuantity));
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartList();
            }
        });
        productQuantity.setText(String.valueOf(finalQuantity));

    }

    private void addToCartList() {
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("brand", productBrand.getText().toString());
        cartMap.put("quantity", String.valueOf(finalQuantity));
        cartMap.put("image", products.getImage());

        cartListRef.child("User View").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ProductDetailsActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProductDetailsActivity.this, ProductList.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    products = snapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productBrand.setText(products.getBrand());
                    productDescription.setText(products.getDescription());
                    productQuantity.setText(String.valueOf(finalQuantity));
                    Picasso.get().load(products.getImage()).into(productImage);


                    addQuantity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finalQuantity++;
                            productQuantity.setText(String.valueOf(finalQuantity));
                        }
                    });

                    removeQuantity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(finalQuantity == 1){
                                Log.d("final", String.valueOf(finalQuantity));
                                return;
                            }
                            finalQuantity--;
                            productQuantity.setText(String.valueOf(finalQuantity));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}