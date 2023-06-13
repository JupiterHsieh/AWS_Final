package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
    }
}