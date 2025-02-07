package com.example.mainstreet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainstreet.Model.Cart;
import com.example.mainstreet.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn ,GotoShopping;


    private  int TotalPrice=0;
    public TextView txtTotalAmount,emptyCartText,Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        emptyCartText=findViewById(R.id.emptyCartText);
        Total=findViewById(R.id.Total);

        NextProcessBtn = findViewById(R.id.cartCheckoutBtn);
        GotoShopping = findViewById(R.id.GotoShopping);
        txtTotalAmount = findViewById(R.id.cartTotalPrice);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                TotalPrice=0;
                startActivity(intent);
            }
        });
        GotoShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartActivity.this, ProductList.class);
                TotalPrice=0;
                startActivity(intent);
            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Products"), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductBrand.setText(model.getBrand());
                holder.txtProductPrice.setText("$"+model.getPrice());
                holder.itemQuantity.setText("Quantity: "+model.getQuantity());
                Picasso.get().load(model.getImage()).into(holder.eachCartShoeImage);


                int onetypeProductPrice=((Integer.valueOf(model.getPrice()))*Integer.valueOf(model.getQuantity()));
                TotalPrice=TotalPrice+onetypeProductPrice;
                txtTotalAmount.setText("$"+String.valueOf(TotalPrice));


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit","Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if(i==0) {
                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }

                                if(i==1){
                                    cartListRef.child("User View")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Products")

                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(CartActivity.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                                        TotalPrice=TotalPrice-onetypeProductPrice;
                                                        txtTotalAmount.setText("$"+String.valueOf(TotalPrice));
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_each_cart, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    txtTotalAmount.setVisibility(View.GONE);
                    Total.setVisibility(View.GONE);
                    NextProcessBtn.setVisibility(View.GONE);
                    GotoShopping.setVisibility(View.VISIBLE);
                    emptyCartText.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    txtTotalAmount.setVisibility(View.VISIBLE);
                    Total.setVisibility(View.VISIBLE);
                    NextProcessBtn.setVisibility(View.VISIBLE);
                    GotoShopping.setVisibility(View.GONE);
                    emptyCartText.setVisibility(View.GONE);
                }
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}