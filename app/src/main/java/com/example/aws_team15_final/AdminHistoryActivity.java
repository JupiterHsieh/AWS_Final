package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AdminHistoryActivity extends AppCompatActivity {

    ArrayList<AdminHistoryModel> adminHistoryModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        RecyclerView recyclerView = findViewById(R.id.HrecycleView);

        setUpHistoryModels();

        HistoryViewAdapter adapter = new HistoryViewAdapter(this,adminHistoryModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpHistoryModels(){
        //TODO AWS
        String[] transaction = {"1111","2222","3333","4444","55555","66666"};
        String[] username = {"Peter","Jupiter","Danny","AAA","BBB","CCC"};
        String[] timeStamp = {"2023/6/4-12:00","2023/6/5-12:00","2023/6/6-12:00","2023/6/7-12:00","2023/6/8-12:00","2023/6/9-12:00"};
        String[] item = {"Doritos","Oreo","Kido","MacFluffy","Prinkles","Laus"};
       // String[] quantity={"1","2","3","1","2","3"};

        for(int i =0; i<transaction.length;i++){
            adminHistoryModels.add(new AdminHistoryModel(transaction[i],username[i],item[i],timeStamp[i]));
            //System.out.println(transaction[i]);
        }





    }


}