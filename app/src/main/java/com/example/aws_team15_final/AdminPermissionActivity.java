package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AdminPermissionActivity extends AppCompatActivity {

    ArrayList<SnackApplyModel> snackApplyModels = new ArrayList<>();
    int[] snackpic = {R.drawable.candy,R.drawable.doritos,R.drawable.candy,R.drawable.baseline_cookie_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_permission);
        RecyclerView recyclerView = findViewById(R.id.admin_approve_RecycleView);
        setUpSnackApplyModel();
        SnackApplyAdapter adapter = new SnackApplyAdapter(this,snackApplyModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpSnackApplyModel(){

        String[] UserNames = getResources().getStringArray(R.array.user_names);
        String[] SnackNames = getResources().getStringArray(R.array.user_snacks);
        String[] SnackQuantity = getResources().getStringArray(R.array.snacks_quantity);


        for(int i = 0;i<UserNames.length;i++){

            snackApplyModels.add(new SnackApplyModel(UserNames[i],SnackNames[i],SnackQuantity[i],snackpic[i]));
        }
    }
}