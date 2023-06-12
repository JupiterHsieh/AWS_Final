package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton grabSnacksBtn =(ImageButton)findViewById(R.id.GrabSnackImageButton);
//        ImageButton add = (ImageButton)findViewById(R.id.AddSnacksImageButton);
        ImageButton addSnacksBtn = (ImageButton)findViewById(R.id.AddSnacksImageButton);
        ImageButton findSnacksBtn = (ImageButton)findViewById(R.id.FindSnacksImageButton);
        ImageButton reserveSnacksBtn = (ImageButton)findViewById(R.id.ReserveSnacksImageButton);
        ImageButton userReportBtn = (ImageButton)findViewById(R.id.UsersReportImageButton);

//        try {
////            Amplify.configure(getApplicationContext());
//
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
//        }

        Amplify.DataStore.start(() -> {
            Log.i("MyAmplifyApp", "DataStore started.");

            Amplify.DataStore.query(
                    User.class,
                    matches -> {
                        if (matches.hasNext()) {
                            User user = matches.next();
                            Log.i("MyAmplifyApp", "User coin: " + user.getCoin());
                            TextView credit_textview = findViewById(R.id.credit_textview);
                            credit_textview.setText(String.valueOf(user.getCoin()));
                        }
                        else
                            Log.i("MyAmplifyApp", "User coin see nothing");
                    },
                    failure -> Log.e("MyAmplifyApp", "Query failed: " + failure.getMessage())
            );
        }, error -> {
            Log.e("MyAmplifyApp", "Error starting DataStore.", error);
        });


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