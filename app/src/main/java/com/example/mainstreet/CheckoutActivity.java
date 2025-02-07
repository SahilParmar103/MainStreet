package com.example.mainstreet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    EditText sd_name,sd_email, sd_address, sd_postal, sd_phone, sd_cardnumber, sd_valid, sd_cvv, sd_holdername;
    TextView txt_Shipping;
    Button checkout_btn,Goback_btn;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        sd_name = (EditText) findViewById(R.id.sd_name);
        sd_email = (EditText) findViewById(R.id.sd_email);
        sd_address = (EditText) findViewById(R.id.sd_address);
        sd_postal = (EditText) findViewById(R.id.sd_postal);
        sd_phone = (EditText) findViewById(R.id.sd_phone);
        sd_cardnumber = (EditText) findViewById(R.id.sd_cardnumber) ;
        sd_valid = (EditText) findViewById(R.id.sd_valid);
        sd_cvv = (EditText) findViewById(R.id.sd_cvv);
        sd_holdername = (EditText) findViewById(R.id.sd_holdername);
        checkout_btn = (Button) findViewById(R.id.checkout_btn);
        Goback_btn = (Button) findViewById(R.id.Goback_btn);
        cardView = findViewById(R.id.cartActivityCardView);
        txt_Shipping = (TextView) findViewById(R.id.txt_Shipping);


        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validField = validateForm();
                if(validField)
                {
                    sd_name.setVisibility(View.INVISIBLE);
                    sd_email.setVisibility(View.INVISIBLE);
                    sd_address.setVisibility(View.INVISIBLE);
                    sd_postal.setVisibility(View.INVISIBLE);
                    sd_phone.setVisibility(View.INVISIBLE);
                    sd_cardnumber.setVisibility(View.INVISIBLE);
                    sd_valid.setVisibility(View.INVISIBLE);
                    sd_cvv.setVisibility(View.INVISIBLE);
                    txt_Shipping.setVisibility(View.INVISIBLE);
                    sd_holdername.setVisibility(View.INVISIBLE);
                    checkout_btn.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                    Goback_btn.setVisibility(View.VISIBLE);

                    Goback_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
                            cartListRef.removeValue();
                            Intent intent = new Intent(getApplicationContext(), ProductList.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }

    private boolean validateForm() {
        if (sd_name.length()  == 0 || sd_name.getText().toString().equals(" ") || sd_name.getText().toString().equals("  ") || sd_name.getText().toString().equals("   ")){
            sd_name.setError("Name is required");
            return false;
        }
        String email = sd_email.getText().toString().trim();
        if (email.isEmpty()) {
            sd_email.setError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sd_email.setError("Invalid email format");
            return false;
        }
        if (sd_address.length()  == 0 || sd_address.getText().toString().equals(" ") || sd_address.getText().toString().equals("  ") || sd_address.getText().toString().equals("   ")){
            sd_address.setError("Address is required");
            return false;
        }
        String phoneNumber = sd_phone.getText().toString().trim();
        String phonePattern = "\\d{10}";
        if (phoneNumber.length() == 0) {
            sd_phone.setError("Phone number is required");
            return false;
        } else if (!phoneNumber.matches(phonePattern)) {
            sd_phone.setError("Invalid phone number");
            return false;
        }
        if (sd_postal.length() < 6 || sd_postal.getText().toString().equals(" ") || sd_postal.getText().toString().equals("  ") || sd_postal.getText().toString().equals("   ")){
            sd_postal.setError("Enter Valid Postal Code");
        }

        if (sd_holdername.length() == 0 || sd_holdername.getText().toString().equals(" ") || sd_holdername.getText().toString().equals("  ") || sd_holdername.getText().toString().equals("   ")) {
            sd_holdername.setError("Cardholder Name is required");
            return false;
        }
        if (sd_cardnumber.length() == 0 || sd_cardnumber.getText().toString().equals(" ") || sd_cardnumber.getText().toString().equals("  ") || sd_cardnumber.getText().toString().equals("   ")) {
            sd_cardnumber.setError("Card Number is required");
            return false;
        }
        if (sd_cardnumber.length() < 16) {
            sd_cardnumber.setError("Enter 16 Digit Card Number");
            return false;
        }
        if (sd_valid.length() == 0 || sd_valid.getText().toString().equals(" ") || sd_valid.getText().toString().equals("  ") || sd_valid.getText().toString().equals("   ")) {
            sd_valid.setError("Exp Date is required");
            return false;
        }
        String expiry = sd_valid.getText().toString().trim();
        if (!isValidExpiryDate(expiry)){
            sd_valid.setError("Expire date is not valid");
            return false;
        }

        if (sd_cvv.length() == 0 || sd_cvv.getText().toString().equals(" ") || sd_cvv.getText().toString().equals("  ") || sd_cvv.getText().toString().equals("   ")) {
            sd_cvv.setError("CVV is required");
            return false;
        }
        if (sd_cvv.length() < 3) {
            sd_cvv.setError("Cvv is Not valid");
            return false;
        }
        return true;
    }
    private boolean isValidExpiryDate(String expiryDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(  "MMYY", Locale.US);
        dateFormat. setLenient (false);
        try {
            Date currentDate = Calendar. getInstance(). getTime();
            Date inputDate = dateFormat. parse (expiryDate);
            return inputDate. after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }
}