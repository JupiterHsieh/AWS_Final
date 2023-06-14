
package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AdminReplenishActivity extends AppCompatActivity implements ReplenishRecyclerViewAdapter.ReplenishButtonListener{
    private RecyclerView replenish_item_recyclerview;
    ReplenishRecyclerViewAdapter replenishRecyclerViewAdapter;
    private ArrayList<Integer> test_img_arr = new ArrayList<>();
    private ArrayList<String> test_replenish_items_arr = new ArrayList<>();
    private ArrayList<Integer> test_replenish_quantity_arr = new ArrayList<>();
    private ArrayList<Integer> test_replenish_count_arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_replenish);
        //test
        test_img_arr.add(R.drawable.noodles);
        test_img_arr.add(R.drawable.oatbar);
        test_img_arr.add(R.drawable.doritos);
        test_img_arr.add(R.drawable.milkpuff);
        test_img_arr.add(R.drawable.koala);
        test_img_arr.add(R.drawable.lays);
        test_replenish_items_arr.add("Noodles");
        test_replenish_items_arr.add("Oat Bar");
        test_replenish_items_arr.add("Doritos");
        test_replenish_items_arr.add("Milk Puff");
        test_replenish_items_arr.add("Koala");
        test_replenish_items_arr.add("Lays");
        test_replenish_quantity_arr.add(12);
        test_replenish_quantity_arr.add(3);
        test_replenish_quantity_arr.add(5);
        test_replenish_quantity_arr.add(0);
        test_replenish_quantity_arr.add(2);
        test_replenish_quantity_arr.add(22);
        test_replenish_count_arr.add(0);
        test_replenish_count_arr.add(0);
        test_replenish_count_arr.add(0);
        test_replenish_count_arr.add(0);
        test_replenish_count_arr.add(0);
        test_replenish_count_arr.add(0);

        replenish_item_recyclerview = findViewById(R.id.replenish_recyclerView);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(AdminReplenishActivity.this, 2);
        replenish_item_recyclerview.setLayoutManager(gridlayoutManager);
        replenishRecyclerViewAdapter = new ReplenishRecyclerViewAdapter(test_img_arr,test_replenish_items_arr,test_replenish_quantity_arr,test_replenish_count_arr,this);
        replenish_item_recyclerview.setAdapter(replenishRecyclerViewAdapter);
        replenish_item_recyclerview.setHasFixedSize(true);
    }

    @Override
    public void minusOnClick(int position) {
        int replenish_count = test_replenish_count_arr.get(position);
        // change android side
        int update_count = replenish_count - 1;
        test_replenish_count_arr.set(position,update_count);
    }

    @Override
    public void plusOnclick(int position) {
        int replenish_count = test_replenish_count_arr.get(position);
        // change android side
        int update_count = replenish_count + 1;
        test_replenish_count_arr.set(position,update_count);
    }

    @Override
    public void replenishOnClick(int position) {

    }
}