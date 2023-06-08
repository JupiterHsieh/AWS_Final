package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ReserveSnackActivity extends AppCompatActivity implements RecyclerViewAdapter.ButtonClickListener{

    private RecyclerView avaliable_items_recyclerview;
    RecyclerViewAdapter recyclerViewAdapter;

    //for test
    private ArrayList<Integer> test_arr = new ArrayList<>();
    private ArrayList<String> test_items_arr = new ArrayList<>();
    private ArrayList<Integer> test_quantity_arr = new ArrayList<>();

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
        test_items_arr.add("Doritos");
        test_items_arr.add("Koala");
        test_items_arr.add("Milk Puff");
        test_items_arr.add("Noodles");
        test_items_arr.add("Oat Bar");
        test_items_arr.add("Lay's");
        test_quantity_arr.add(12);
        test_quantity_arr.add(22);
        test_quantity_arr.add(8);
        test_quantity_arr.add(34);
        test_quantity_arr.add(56);
        test_quantity_arr.add(3);
        //

        avaliable_items_recyclerview = findViewById(R.id.avaliable_items_recyclerview);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this,2);
        avaliable_items_recyclerview.setLayoutManager(gridlayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(test_arr,test_items_arr,test_quantity_arr,this);
        avaliable_items_recyclerview.setAdapter(recyclerViewAdapter);
        avaliable_items_recyclerview.setHasFixedSize(true);
    }

    @Override
    public void onButtonClick(int position) {
        String item_name = test_items_arr.get(position);
        int remaining_quantity = test_quantity_arr.get(position) - 1;
        Toast.makeText(getApplicationContext(),"The reservation for " + item_name + " is successful ! and there are still " + remaining_quantity + " left.",Toast.LENGTH_LONG).show();
        finish();
    }
}