package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Items;
import com.amplifyframework.datastore.generated.model.User;

import com.amplifyframework.core.model.query.Where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


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

//        try {
////            Amplify.configure(getApplicationContext());
//
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
//        }
        //Get Email
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    String mail = attributes.get(attributes.size()-1).getValue().toString();
                    Log.i("AuthDemo", "User attributes = " + mail);},
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

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
        AtomicReference<String> temp = new AtomicReference<>("");
        Amplify.DataStore.start(() -> {
            Log.i("MyAmplifyApp", "DataStore started.");

            Amplify.DataStore.query(
                User.class,
                Where.matches(User.USERNAME.eq(MainActivity.public_username)),
                matches -> {
                    if (matches.hasNext()) {
                        User _user = matches.next();
                        Log.i("MyAmplifyApp", "User coin: " + _user.getCoin());
                        usercoin = _user.getCoin();
                        temp.set(_user.getId());
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