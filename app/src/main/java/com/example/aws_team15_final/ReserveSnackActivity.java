package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Items;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
        setContentView(R.layout.activity_reserve_snack);

//        User user = User.builder()
//                .username("tool")
//                .group("1")
//                .auth(Boolean.TRUE)
//                .coin(7)
//                .build();
//
//        Amplify.DataStore.save(user,
//                result -> Log.i("MyAmplifyApp", "Created a new post successfully" + result),
//                error -> Log.e("MyAmplifyApp",  "Error creating post", error)
//        );
        ///////////////////////////////////////////////////////////////
//        Items item2 = Items.builder()
//                .item("item2")
//                .count(15)
//                .rule(Arrays.asList(1, 1, 1, 1))
//                .build();
//        Items item3 = Items.builder()
//                .item("item3")
//                .count(5)
//                .rule(Arrays.asList(1, 0, 0, 0))
//                .build();
//        Amplify.DataStore.save(item,
//                result -> Log.i("MyAmplifyApp", "Created a new item successfully" + result),
//                error -> Log.e("MyAmplifyApp",  "Error creating item", error)
//        );
//        Amplify.DataStore.query(Items.class,
//                queryMatches -> {
////                    Log.i("MyAmplifyApp", "Successful query, found "+ queryMatches.next());
//
//                    if (queryMatches.hasNext()) {
//                        Log.i("MyAmplifyApp", "Successful query, found items."+ queryMatches.next());
//
//                    } else {
//                        Log.i("MyAmplifyApp", "Successful query, but no items.");
//                    }
//                },
//                error -> Log.e("MyAmplifyApp",  "Error retrieving items", error)
//        );
        /////////////////////////////////////////
        // 创建一个 CountDownLatch 并设置初始计数为1
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Amplify.DataStore.save(item2,
//                result -> {
//                    Log.i("MyAmplifyApp", "Created a new item successfully" + result);
//                    // 保存操作完成，计数减一
//                    latch.countDown();
//                },
//                error -> {
//                    Log.e("MyAmplifyApp", "Error creating item", error);
//                    // 保存操作完成，计数减一
//                    latch.countDown();
//                }
//        );
//        Amplify.DataStore.save(item3,
//                result -> {
//                    Log.i("MyAmplifyApp", "Created a new item successfully" + result);
//                    // 保存操作完成，计数减一
//                    latch.countDown();
//                },
//                error -> {
//                    Log.e("MyAmplifyApp", "Error creating item", error);
//                    // 保存操作完成，计数减一
//                    latch.countDown();
//                }
//        );
//        try {
//            // 等待计数为0，即保存操作完成
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////////////////////////////////////////
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

        /////////////////////////////////////////
//        Amplify.DataStore.query(Reserve.class,
//                queryMatches -> {
//                    Log.i("MyAmplifyApp", "Successful query, found "+ queryMatches.next());
//
//                    if (queryMatches.hasNext()) {
//                        Log.i("MyAmplifyApp", "Successful query, found posts."+ queryMatches.next());
//
//                    } else {
//                        Log.i("MyAmplifyApp", "Successful query, but no posts.");
//                    }
//                },
//                error -> Log.e("MyAmplifyApp",  "Error retrieving posts", error)
//        );

        //for test
        test_arr.add(R.drawable.doritos);
        test_arr.add(R.drawable.koala);
        test_arr.add(R.drawable.milkpuff);
        test_arr.add(R.drawable.noodles);
        test_arr.add(R.drawable.oatbar);
        test_arr.add(R.drawable.lays);

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
//    @Override
//    public void onButtonClick(int position) {
//        Log.i("button", "button pos" + String.valueOf(position));
//        String item_name = test_items_arr.get(position);
//
//        int remaining_quantity = test_quantity_arr.get(position) - test_cnt_arr.get(position);
//        Toast.makeText(getApplicationContext(),"The reservation for " + item_name + " is successful ! and there are still " + remaining_quantity + " left.",Toast.LENGTH_LONG).show();
//    //        finish();
//    }

//    @Override
//    public void onButtonClick(int position) {
//        String item_name = test_items_arr.get(position);
//        int remaining_quantity = test_quantity_arr.get(position) - test_cnt_arr.get(position);
//        Toast.makeText(getApplicationContext(),"The reservation for " + item_name + " is successful ! and there are still " + remaining_quantity + " left.",Toast.LENGTH_LONG).show();
//    }
    @Override
    public void onButtonClick(int position, int buttonId) {
        if (buttonId == R.id.reserve_button) {
            String item_name = test_items_arr.get(position);
            if(test_quantity_arr.get(position) >= test_cnt_arr.get(position))
                test_quantity_arr.set(position, test_quantity_arr.get(position) - test_cnt_arr.get(position));
            Toast.makeText(getApplicationContext(),"The reservation for " + item_name + " is successful ! and there are still " + test_quantity_arr.get(position) + " left.",Toast.LENGTH_LONG).show();
        }
        else if (buttonId == R.id.cnt_plus) {
            if(test_quantity_arr.get(position) > test_cnt_arr.get(position)) {
                test_cnt_arr.set(position, test_cnt_arr.get(position) + 1);
            }
            Log.i("MyAmplify", "button plus" + test_cnt_arr.get(position) +", " + test_quantity_arr.get(position));
        }
        else if (buttonId == R.id.cnt_minus) {
            if(test_cnt_arr.get(position) > 1)
                test_cnt_arr.set(position, test_cnt_arr.get(position) - 1);
            Log.i("MyAmplify", "button minus" + test_cnt_arr.get(position) +", " + test_quantity_arr.get(position));
        }
        TextView reserve_cnt_text = findViewById(R.id.reserve_cnt);
        reserve_cnt_text.setText(String.valueOf(test_cnt_arr.get(position)));

        TextView quantity_textView = findViewById(R.id.quantity_textView);
        quantity_textView.setText(String.valueOf(test_quantity_arr.get(position)));
    }
}
