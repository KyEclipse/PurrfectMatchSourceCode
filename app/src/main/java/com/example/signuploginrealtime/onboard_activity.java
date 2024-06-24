package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class onboard_activity extends AppCompatActivity {

    Button findafriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboard);

        findafriend = findViewById(R.id.findafriend);

        findafriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(onboard_activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}