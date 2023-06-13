package com.example.aws_team15_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Reserve;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GrabCheckActivity extends AppCompatActivity implements CheckRecyclerViewAdapter.ButtonListener{

    private RecyclerView check_items_recyclerview;
    CheckRecyclerViewAdapter checkRecyclerViewAdapter;

    private ArrayList<String> test_check_items_arr = new ArrayList<>();
    private final ArrayList<Integer> test_check_quantity_arr = new ArrayList<>();
    private ArrayList<Integer> test_check_inv_arr = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected void onStart(){
        super.onStart();

        setContentView(R.layout.activity_grab_check);

        CountDownLatch latch = new CountDownLatch(1);

        Amplify.DataStore.query(
                Reserve.class,
                Where.matches(Reserve.USERNAME.eq("tool")),
                queryMatches -> {
                    try {
                        while (queryMatches.hasNext()) {
                            Reserve _reserve = queryMatches.next();
                            Log.i("MyAmplifyApp", "Successful query, found item: " + _reserve.getItem() + ", " + _reserve.getCount());

                            test_check_items_arr.add(_reserve.getItem());
                            test_check_quantity_arr.add(_reserve.getCount());
                        }
                    } catch (Exception e) {
                        Log.e("MyAmplifyApp", "Error retrieving items", e);
                    } finally {
                        latch.countDown(); // 计数减一
                    }
                },
                error -> {
                    Log.e("MyAmplifyApp", "Error retrieving items", error);
                    latch.countDown(); // 计数减一
                }
        );

        try {
            latch.await(); // 等待计数为0，即查询完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runOnUiThread(() -> {
            check_items_recyclerview = findViewById(R.id.check_items_recyclerview);
            check_items_recyclerview.setLayoutManager(new LinearLayoutManager(this));
            checkRecyclerViewAdapter = new CheckRecyclerViewAdapter(test_check_items_arr, test_check_quantity_arr, this);
            check_items_recyclerview.setAdapter(checkRecyclerViewAdapter);
            check_items_recyclerview.setHasFixedSize(true);
        });


        Button confirm_btn = findViewById(R.id.confirm_button);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GrabCheckActivity.this,GrabSnackActivity.class));
            }
        });
    }
    @Override
    public void minusOnClick(int position) {
        String item_name = test_check_items_arr.get(position);
        int item_quantity = test_check_quantity_arr.get(position);
        int update_quantity = item_quantity - 1;
        test_check_quantity_arr.set(position,update_quantity);
        Toast.makeText(getApplicationContext(),item_name + " are reduced from " + item_name + " to " + update_quantity,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void plusOnclick(int position) {
        String item_name = test_check_items_arr.get(position);
        int item_quantity = test_check_quantity_arr.get(position);
        int update_quantity = item_quantity + 1;
        test_check_quantity_arr.set(position,update_quantity);
        Toast.makeText(getApplicationContext(),item_name + " are added from " + item_name + " to " + update_quantity,Toast.LENGTH_SHORT).show();
    }
}
