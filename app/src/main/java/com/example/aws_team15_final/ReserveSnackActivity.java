package com.example.aws_team15_final;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDateTime;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Items;
import com.amplifyframework.datastore.generated.model.Reserve;
import com.amplifyframework.datastore.generated.model.User;

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


    }
    protected void onStart() {
        super.onStart();

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
            if(test_cnt_arr.get(position) > HomeActivity.usercoin)
            {
                Toast.makeText(getApplicationContext(), "You don't have enough coins.", Toast.LENGTH_LONG).show();
            }
            else if(test_quantity_arr.get(position) >= test_cnt_arr.get(position)) {

                String formattedDateTime = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//////// change reserve //////////
                CountDownLatch latch2 = new CountDownLatch(1);
                AtomicReference<Integer> prev_reserve_cnt = new AtomicReference<>(0);
                Amplify.DataStore.query(
                        Reserve.class,
                        Where.matches(Reserve.USERNAME.eq("tool").and(Reserve.ITEM.eq(test_items_arr.get(position)))),
                        queryMatches -> {
                            if (queryMatches.hasNext()) {
                                Reserve _reserve = queryMatches.next();
                                prev_reserve_cnt.set(_reserve.getCount());
                                Amplify.DataStore.delete(
                                        _reserve,
                                        deleted -> {
                                            Log.i("MyAmplifyApp", "Deleted an old reserve.");
                                            latch2.countDown();
                                        },
                                        failure -> {
                                            Log.e("MyAmplifyApp", "Delete old reserve failed.", failure);
                                            latch2.countDown();
                                        }
                                );
                            } else {
                                latch2.countDown();
                            }
                        },
                        error -> {
//                            Log.e("MyAmplifyApp", "Error retrieving items", error);
                            latch2.countDown();
                        }
                );

                try {
                    latch2.await();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Reserve reserve = Reserve.builder()
                        .username("tool")
                        .item(test_items_arr.get(position))
                        .count(test_cnt_arr.get(position) + prev_reserve_cnt.get())
                        .starttime(new Temporal.DateTime(formattedDateTime))
                        .endtime(new Temporal.DateTime(formattedDateTime))
                        .build();

                Amplify.DataStore.save(
                        reserve,
                        result -> Log.i("MyAmplifyApp", "Reserve saved: " + result),
                        error -> Log.e("MyAmplifyApp", "Reserve error", error)
                );

///////////////////////// change inventory number /////////////////
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

/////////// change user coin /////////////////////
                    AtomicReference<String> user_old_username = new AtomicReference<>("");
                    AtomicReference<String> user_old_group = new AtomicReference<>("");
                    AtomicReference<Boolean> user_old_auth = new AtomicReference<>();
                    AtomicReference<Integer> user_old_coin = new AtomicReference<>(HomeActivity.usercoin);
                    Amplify.DataStore.query(
                        User.class,
                        Where.matches(User.USERNAME.eq("tool")),
                        matches -> {
                            if (matches.hasNext()) {
                                User _user = matches.next();
                                Log.i("MyAmplifyApp", "User coin: " + _user.getCoin());
                                user_old_username.set(_user.getUsername());
                                user_old_group.set(_user.getGroup());
                                user_old_auth.set(_user.getAuth());
                                user_old_coin.set(_user.getCoin());
                                Amplify.DataStore.delete(_user,
                                    deleted -> {
                                        Log.i("MyAmplifyApp", "Deleted user_old.");
                                        user_old_coin.updateAndGet(v -> v - test_cnt_arr.get(position));
                                        User user_new = User.builder()
                                                .username(String.valueOf(user_old_username))
                                                .group(String.valueOf(user_old_group))
                                                .auth(user_old_auth.get())
                                                .coin(user_old_coin.get())
                                                .build();
                                        Amplify.DataStore.save(user_new,
                                                saved -> Log.i("MyAmplifyApp", "Saved user new"),
                                                failure -> Log.i("MyAmplifyApp", "Save user new failed" + failure)
                                        );
                                    },
                                    failure -> Log.e("MyAmplifyApp", "Delete user_old failed.", failure)
                                );
                            }
                            else
                                Log.i("MyAmplifyApp", "User coin see nothing");
                        },
                        failure -> Log.e("MyAmplifyApp", "Query failed: " + failure.getMessage())
                    );
                    Toast.makeText(getApplicationContext(), test_cnt_arr.get(position) + " coins deducted ", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (buttonId == R.id.cnt_plus) {
            if(test_quantity_arr.get(position) > test_cnt_arr.get(position)) {
                test_cnt_arr.set(position, test_cnt_arr.get(position) + 1);
//            Log.i("MyAmplify", position + "button plus" + test_cnt_arr.get(position) +", " + test_quantity_arr.get(position));
            }
        }
        else if (buttonId == R.id.cnt_minus) {
            if(test_cnt_arr.get(position) > 1)
                test_cnt_arr.set(position, test_cnt_arr.get(position) - 1);
//            Log.i("MyAmplify", position + "button minus" + test_cnt_arr.get(position) +", " + test_quantity_arr.get(position));
        }
        TextView reserve_cnt_text = findViewById(R.id.reserve_cnt);
        recyclerViewAdapter.notifyItemChanged(position); // 通知适配器该项已更改

        TextView quantity_textView = findViewById(R.id.quantity_textView);
        quantity_textView.setText(String.valueOf(test_quantity_arr.get(position)));
    }
}
