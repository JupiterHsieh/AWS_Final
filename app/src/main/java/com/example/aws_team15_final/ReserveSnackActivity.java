package com.example.aws_team15_final;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDateTime;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Items;
import com.amplifyframework.datastore.generated.model.Reserve;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.CountDownLatch;

public class ReserveSnackActivity extends AppCompatActivity implements RecyclerViewAdapter.ButtonClickListener {

    private RecyclerView avaliable_items_recyclerview;
    RecyclerViewAdapter recyclerViewAdapter;

    //for test
    private ArrayList<Integer> test_arr = new ArrayList<>();
    private ArrayList<String> test_items_arr = new ArrayList<>();
    private ArrayList<Integer> test_quantity_arr = new ArrayList<>();
    private ArrayList<Integer> test_cnt_arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Amplify.DataStore.query(Items.class,
                queryMatches -> {
                    while (queryMatches.hasNext()) {
                        Items item = queryMatches.next();
                        Log.i("MyAmplifyApp", "Successful query, found item: " + item.getItem() + ", " + item.getCount());

                        try {
                            test_items_arr.add(item.getItem());
                            test_quantity_arr.add(item.getCount());
                            test_cnt_arr.add(1);
                        } catch (ArithmeticException e) {
                            Log.i("add arr", String.valueOf(e));
                        }
                    }
                },
                error -> Log.e("MyAmplifyApp", "Error retrieving items", error)
        );

        test_arr.add(R.drawable.doritos);
        test_arr.add(R.drawable.koala);
        test_arr.add(R.drawable.milkpuff);
        test_arr.add(R.drawable.noodles);
        test_arr.add(R.drawable.oatbar);
        test_arr.add(R.drawable.lays);
        setContentView(R.layout.activity_reserve_snack);
    }
    protected void onStart() {
        super.onStart();
        avaliable_items_recyclerview = findViewById(R.id.avaliable_items_recyclerview);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this, 2);
        avaliable_items_recyclerview.setLayoutManager(gridlayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(test_arr, test_items_arr, test_quantity_arr, test_cnt_arr, this);
        avaliable_items_recyclerview.setAdapter(recyclerViewAdapter);
        avaliable_items_recyclerview.setHasFixedSize(true);
    }

    @Override
    public void onButtonClick(int position, int buttonId) {
        if (buttonId == R.id.reserve_button) {
            String item_name = test_items_arr.get(position);
            if(test_quantity_arr.get(position) >= test_cnt_arr.get(position)) {

                String formattedDateTime = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                Log.i("MyAmplify", "Get reserve data" + test_items_arr.get(position) + ", " + test_cnt_arr.get(position) + ", " + formattedDateTime);
                Reserve reserve = Reserve.builder()
                        .username("tool")
                        .item(test_items_arr.get(position))
                        .count(test_cnt_arr.get(position))
                        .starttime(new Temporal.DateTime(formattedDateTime))
                        .endtime(new Temporal.DateTime(formattedDateTime))
                        .build();
                Amplify.DataStore.save(reserve,
                    result -> Log.i("MyAmplifyApp", "Reserve saved" + result),
                    error -> Log.e("MyAmplifyApp",  "Reserve error", error)
                );

                AtomicReference<Integer> item_quantity = new AtomicReference<>(0);
                AtomicReference<List<Integer>> item_rule = new AtomicReference<>();
                CountDownLatch latch = new CountDownLatch(1);

                Amplify.DataStore.query(
                        Items.class,
                        Where.matches(Items.ITEM.eq(test_items_arr.get(position))),
                        item_matches -> {
                            while (item_matches.hasNext()) {
                                Items _item = item_matches.next();
                                item_quantity.set(_item.getCount());
                                item_rule.set(_item.getRule());
                                Log.i("MyAmplifyApp", "item_quantity: " + item_quantity);
                                Amplify.DataStore.delete(_item,
                                        deleted -> Log.i("MyAmplifyApp", "Item old deleted" + deleted),
                                        failure -> Log.e("MyAmplifyApp", "Delete failed.", failure)
                                );
                            }
                            latch.countDown();
                        },
                        failure -> {
                            Log.e("MyAmplifyApp", "Query failed.", failure);
                            latch.countDown();
                        }
                );

                try {
                    latch.await(); // 等待查询完成
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (test_quantity_arr.get(position) == item_quantity.get()) {
                    test_quantity_arr.set(position, test_quantity_arr.get(position) - test_cnt_arr.get(position));
                    Items item = Items.builder()
                            .item(test_items_arr.get(position))
                            .count(item_quantity.get() - test_cnt_arr.get(position))
                            .rule(item_rule.get())
                            .build();

                    Amplify.DataStore.save(
                            item,
                            result -> Log.i("MyAmplifyApp", "Item new quant saved: " + result),
                            error -> Log.e("MyAmplifyApp", "Item new quant error", error)
                    );
                }
                Toast.makeText(getApplicationContext(), "The reservation for " + item_name + " is successful ! and there are still " + test_quantity_arr.get(position) + " left.", Toast.LENGTH_LONG).show();
            }
        }
        else if (buttonId == R.id.cnt_plus) {
            if(test_quantity_arr.get(position) > test_cnt_arr.get(position)) {
                test_cnt_arr.set(position, test_cnt_arr.get(position) + 1);
            }
            Log.i("MyAmplify", position + "button plus" + test_cnt_arr.get(position) +", " + test_quantity_arr.get(position));
        }
        else if (buttonId == R.id.cnt_minus) {
            if(test_cnt_arr.get(position) > 1)
                test_cnt_arr.set(position, test_cnt_arr.get(position) - 1);
            Log.i("MyAmplify", position + "button minus" + test_cnt_arr.get(position) +", " + test_quantity_arr.get(position));
        }
        TextView reserve_cnt_text = findViewById(R.id.reserve_cnt);
        recyclerViewAdapter.notifyItemChanged(position); // 通知适配器该项已更改
//        reserve_cnt_text.setText(String.valueOf(test_cnt_arr.get(position)));

        TextView quantity_textView = findViewById(R.id.quantity_textView);
        quantity_textView.setText(String.valueOf(test_quantity_arr.get(position)));
    }
}
