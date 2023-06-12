package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.amplifyframework.core.Amplify;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get user
        try {
            Amplify.Auth.getCurrentUser(
                    result -> Log.i("AuthQuickstart", "Current user details are:" + result.toString()),
                    error -> Log.e("AuthQuickstart", "getCurrentUser failed with an exception: " + error)
                    );
        } catch (Exception error) {
            Log.e("AuthQuickstart", "unexpected error: " + error);
        }

        ImageButton grabSnacksBtn =(ImageButton)findViewById(R.id.UserDataImageButton);
        ImageButton add = (ImageButton)findViewById(R.id.ReplenishsImageButton);
        ImageButton addSnacksBtn = (ImageButton)findViewById(R.id.ReplenishsImageButton);
        ImageButton findSnacksBtn = (ImageButton)findViewById(R.id.FindSnacksImageButton);
        ImageButton reserveSnacksBtn = (ImageButton)findViewById(R.id.ReserveSnacksImageButton);
        ImageButton userReportBtn = (ImageButton)findViewById(R.id.UsersReportImageButton);
//        ImageButton recBtn = (ImageButton)findViewById(R.id.IdeasSnackBtn);

        grabSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, GrabCheckActivity.class));
            }
        });

        addSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddSnackActivity.class));
            }
        });

        findSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FindSnackActivity.class));
            }
        });

        reserveSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ReserveSnackActivity.class));
            }
        });

        userReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UserReportActivity.class));
            }
        });



    }
}