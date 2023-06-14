package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.Useraction;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ImageButton UserDataBtn = (ImageButton) findViewById(R.id.UserDataImageButton);
        ImageButton ReplenishBtn = (ImageButton) findViewById(R.id.ReplenishImageButton);
        ImageButton PermissionBtn = (ImageButton) findViewById(R.id.PermissionImageButton);

        PermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,AdminPermissionActivity.class));
            }
        });
        UserDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,AdminHistoryActivity.class));
            }
        });

        ReplenishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,ReplenishActivity.class));
            }
        });
        Amplify.DataStore.start(() -> {
            Log.i("MyAmplifyApp", "DataStore started.");
        },
        error -> {
            Log.e("MyAmplifyApp", "Error starting DataStore.", error);
        });
    }
}