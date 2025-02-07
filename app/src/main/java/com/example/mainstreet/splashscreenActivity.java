package com.example.mainstreet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class splashscreenActivity extends AppCompatActivity {
    private Button letsgobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        letsgobutton = (Button) findViewById(R.id.Letsgo_btn);

        letsgobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splashscreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}