package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ReserveSnackActivity extends AppCompatActivity {

    private RecyclerView avaliable_items_recyclerview;
    RecyclerViewAdapter recyclerViewAdapter;

    //for test
    private ArrayList<Integer> test_arr = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_snack);

        //for test
        test_arr.add(R.drawable.doritos);
        test_arr.add(R.drawable.koala);
        test_arr.add(R.drawable.milkpuff);
        test_arr.add(R.drawable.noodles);
        test_arr.add(R.drawable.oatbar);
        test_arr.add(R.drawable.lays);

        avaliable_items_recyclerview = findViewById(R.id.avaliable_items_recyclerview);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this,2);
        avaliable_items_recyclerview.setLayoutManager(gridlayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(test_arr);
        avaliable_items_recyclerview.setAdapter(recyclerViewAdapter);
        avaliable_items_recyclerview.setHasFixedSize(true);
    }
}