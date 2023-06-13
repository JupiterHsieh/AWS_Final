package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.User;

import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreException;
import com.amplifyframework.datastore.generated.model.Items;
import com.amplifyframework.datastore.generated.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class HomeActivity extends AppCompatActivity {
    public static Integer usercoin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton grabSnacksBtn =(ImageButton)findViewById(R.id.UserDataImageButton);
//        ImageButton add = (ImageButton)findViewById(R.id.AddSnacksImageButton);
        ImageButton addSnacksBtn = (ImageButton)findViewById(R.id.ReplenishImageButton);
        ImageButton findSnacksBtn = (ImageButton)findViewById(R.id.PermissionImageButton);
        ImageButton reserveSnacksBtn = (ImageButton)findViewById(R.id.ReserveSnacksImageButton);
        ImageButton userReportBtn = (ImageButton)findViewById(R.id.UsersReportImageButton);

        grabSnacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,GrabCheckActivity.class));
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

    @Override
    protected void onStart() {
        super.onStart();

        Amplify.DataStore.start(() -> {
            Log.i("MyAmplifyApp", "DataStore started.");

            Amplify.DataStore.query(
                User.class,
                Where.matches(User.USERNAME.eq("tool")),
                matches -> {
                    if (matches.hasNext()) {
                        User _user = matches.next();
                        Log.i("MyAmplifyApp", "User coin: " + _user.getCoin());
                        usercoin = _user.getCoin();
                        TextView credit_textview = findViewById(R.id.credit_textview);
                        credit_textview.setText(String.valueOf(_user.getCoin()));
                    }
                    else
                        Log.i("MyAmplifyApp", "User coin see nothing");
                },
                failure -> Log.e("MyAmplifyApp", "Query failed: " + failure.getMessage())
            );
        }, error -> {
            Log.e("MyAmplifyApp", "Error starting DataStore.", error);
        });

    }
}