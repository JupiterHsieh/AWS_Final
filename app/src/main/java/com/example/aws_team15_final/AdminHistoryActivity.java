package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class AdminHistoryActivity extends AppCompatActivity {

    ArrayList<SnackApplyModel> snackApplyModel = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);
    }


}