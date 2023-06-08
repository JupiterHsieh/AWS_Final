package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton grabSnacksBtn =(ImageButton)findViewById(R.id.GrabSnackImageButton);
        ImageButton add = (ImageButton)findViewById(R.id.AddSnacksImageButton);
        ImageButton addSnacksBtn = (ImageButton)findViewById(R.id.AddSnacksImageButton);
        ImageButton findSnacksBtn = (ImageButton)findViewById(R.id.FindSnacksImageButton);
        ImageButton reserveSnacksBtn = (ImageButton)findViewById(R.id.ReserveSnacksImageButton);
        ImageButton userReportBtn = (ImageButton)findViewById(R.id.UsersReportImageButton);
//        ImageButton recBtn = (ImageButton)findViewById(R.id.IdeasSnackBtn);



        grabSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,GrabSnackActivity.class));
            }
        });

        addSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,AddSnackActivity.class));
            }
        });

        findSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FindSnackActivity.class));
            }
        });

        reserveSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ReserveSnackActivity.class));
            }
        });

        userReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,UserReportActivity.class));
            }
        });



    }
}