package com.example.aws_team15_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Items;
import com.amplifyframework.datastore.generated.model.Reserve;
import com.amplifyframework.datastore.generated.model.User;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

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
//                        Log.i("MyAmplifyApp", "Successful query, found item: " + _reserve.getItem() + ", " + _reserve.getCount());

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

        for (String i : test_check_items_arr) {
            Amplify.DataStore.query(
                Items.class,
                Where.matches(Items.ITEM.eq(i)),
                queryMatches ->{
                    try {
                        while (queryMatches.hasNext()) {
                            Items _item = queryMatches.next();
                            Log.i("MyAmplifyApp", "found item inventory: " + _item.getItem() + ", " + _item.getCount());
                            test_check_inv_arr.add(_item.getCount());
                        }
                    }
                    catch (Exception e) {
                        Log.e("MyAmplifyApp", "Error retrieving items", e);
                    }
                },
                error -> Log.i("MyAmplifyApp", "Error Query Inventory ", error)
            );
        }

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

        // change android side
        int update_quantity = item_quantity - 1;
        test_check_quantity_arr.set(position, update_quantity);
        // change android side inv
        test_check_inv_arr.set(position, test_check_inv_arr.get(position) + 1);

        // change db usercoin
        CountDownLatch latch1 = new CountDownLatch(1);

        AtomicReference<String> user_old_username = new AtomicReference<>("");
        AtomicReference<String> user_old_group = new AtomicReference<>("");
        AtomicReference<Boolean> user_old_auth = new AtomicReference<>();
        AtomicReference<Integer> user_old_coin = new AtomicReference<>(HomeActivity.usercoin);

        Amplify.DataStore.query(
                User.class,
                Where.matches(User.USERNAME.eq("tool")),
                user_matches -> {
                    if (user_matches.hasNext()) {
                        User _user = user_matches.next();
                        user_old_username.set(_user.getUsername());
                        user_old_group.set(_user.getGroup());
                        user_old_auth.set(_user.getAuth());
                        user_old_coin.set(_user.getCoin());
                        Amplify.DataStore.delete(
                                _user,
                                deleted -> {
                                    Log.i("MyAmplifyApp", "Deleted old user coin.");
                                    latch1.countDown();
                                },
                                failure -> {
                                    Log.e("MyAmplifyApp", "Delete old user coin failed.", failure);
                                    latch1.countDown();
                                }
                        );
                    } else {
                        latch1.countDown();
                    }
                },
                failure -> {
                    Log.i("MyAmplifyApp", "Query old user coin failed.");
                    latch1.countDown();
                }
        );

        try {
            latch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HomeActivity.usercoin += 1;
        User user_new = User.builder()
                .username(user_old_username.get())
                .group(user_old_group.get())
                .auth(user_old_auth.get())
                .coin(HomeActivity.usercoin)
                .build();

        Amplify.DataStore.save(
                user_new,
                saved -> Log.i("MyAmplifyApp", "Saved user new."),
                failure -> Log.e("MyAmplifyApp", "Save user new failed.", failure)
        );

        // change db inventory
        AtomicReference<Integer> item_old_inv = new AtomicReference<>(0);
        AtomicReference<List<Integer>> item_old_rule = new AtomicReference<>();
        CountDownLatch latch2 = new CountDownLatch(1);

        Amplify.DataStore.query(
            Items.class,
            Where.matches(Items.ITEM.eq(item_name)),
            item_matches -> {
                while (item_matches.hasNext()) {
                    Items _item = item_matches.next();
                    item_old_inv.set(_item.getCount());
                    item_old_rule.set(_item.getRule());
                    Amplify.DataStore.delete(_item,
                            deleted -> Log.i("MyAmplifyApp", "Item old deleted" + deleted),
                            failure -> Log.e("MyAmplifyApp", "Delete failed.", failure)
                    );
                }
                latch2.countDown();
            },
            failure -> {
                Log.e("MyAmplifyApp", "Query failed.", failure);
                latch2.countDown();
            }
        );

        try {
            latch2.await(); // 等待查询完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Items item = Items.builder()
                .item(item_name)
                .count(test_check_inv_arr.get(position))
                .rule(item_old_rule.get())
                .build();

        Amplify.DataStore.save(
                item,
                result -> Log.i("MyAmplifyApp", "Item new inv saved: " + result),
                error -> Log.e("MyAmplifyApp", "Item new inv error", error)
        );

        update_reserve(position, -1);
        Toast.makeText(getApplicationContext(),item_name + " are reduced from " + item_name + " to " + update_quantity,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void plusOnclick(int position) {
        String item_name = test_check_items_arr.get(position);
        int item_quantity = test_check_quantity_arr.get(position);
        if(test_check_inv_arr.get(position) >= test_check_quantity_arr.get(position) && HomeActivity.usercoin >= 1) // enough inventory and coin
        {
            // change android side reserve cnt
            int update_quantity = item_quantity + 1;
            test_check_quantity_arr.set(position, update_quantity);
            // change inventory
            test_check_inv_arr.set(position, test_check_inv_arr.get(position) - 1);

            // usercoin
            CountDownLatch latch1 = new CountDownLatch(1);

            AtomicReference<String> user_old_username = new AtomicReference<>("");
            AtomicReference<String> user_old_group = new AtomicReference<>("");
            AtomicReference<Boolean> user_old_auth = new AtomicReference<>();
            AtomicReference<Integer> user_old_coin = new AtomicReference<>(HomeActivity.usercoin);

            Amplify.DataStore.query(
                    User.class,
                    Where.matches(User.USERNAME.eq("tool")),
                    user_matches -> {
                        if (user_matches.hasNext()) {
                            User _user = user_matches.next();
                            user_old_username.set(_user.getUsername());
                            user_old_group.set(_user.getGroup());
                            user_old_auth.set(_user.getAuth());
                            user_old_coin.set(_user.getCoin());
                            Amplify.DataStore.delete(
                                    _user,
                                    deleted -> {
                                        Log.i("MyAmplifyApp", "Deleted old user coin.");
                                        // 删除完成后，调用 countDown() 方法来减少计数器的值
                                        latch1.countDown();
                                    },
                                    failure -> {
                                        Log.e("MyAmplifyApp", "Delete old user coin failed.", failure);
                                        // 出现错误时也需要调用 countDown() 方法来减少计数器的值
                                        latch1.countDown();
                                    }
                            );
                        } else {
                            // 如果没有旧的 User，则直接调用 countDown() 方法来减少计数器的值
                            latch1.countDown();
                        }
                    },
                    failure -> {
                        Log.i("MyAmplifyApp", "Query old user coin failed.");
                        latch1.countDown();
                    }
            );

            try {
                latch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            HomeActivity.usercoin -= 1;
            User user_new = User.builder()
                    .username(user_old_username.get())
                    .group(user_old_group.get())
                    .auth(user_old_auth.get())
                    .coin(HomeActivity.usercoin)
                    .build();

            Amplify.DataStore.save(
                    user_new,
                    saved -> Log.i("MyAmplifyApp", "Saved user new."),
                    failure -> Log.e("MyAmplifyApp", "Save user new failed.", failure)
            );

            // change db inventory
            AtomicReference<Integer> item_old_inv = new AtomicReference<>(0);
            AtomicReference<List<Integer>> item_old_rule = new AtomicReference<>();
            CountDownLatch latch2 = new CountDownLatch(1);

            Amplify.DataStore.query(
                    Items.class,
                    Where.matches(Items.ITEM.eq(item_name)),
                    item_matches -> {
                        while (item_matches.hasNext()) {
                            Items _item = item_matches.next();
                            item_old_inv.set(_item.getCount());
                            item_old_rule.set(_item.getRule());
                            Amplify.DataStore.delete(_item,
                                    deleted -> Log.i("MyAmplifyApp", "Item old deleted" + deleted),
                                    failure -> Log.e("MyAmplifyApp", "Delete failed.", failure)
                            );
                        }
                        latch2.countDown();
                    },
                    failure -> {
                        Log.e("MyAmplifyApp", "Query failed.", failure);
                        latch2.countDown();
                    }
            );

            try {
                latch2.await(); // 等待查询完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Items item = Items.builder()
                    .item(item_name)
                    .count(test_check_inv_arr.get(position))
                    .rule(item_old_rule.get())
                    .build();

            Amplify.DataStore.save(
                    item,
                    result -> Log.i("MyAmplifyApp", "Item new inv saved: " + result),
                    error -> Log.e("MyAmplifyApp", "Item new inv error", error)
            );

            update_reserve(position, 1);
            Toast.makeText(getApplicationContext(),item_name + " are added from " + item_name + " to " + test_check_quantity_arr.get(position),Toast.LENGTH_SHORT).show();
        }
    }
    public void update_reserve(Integer position, Integer delta_reserve){
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Integer> old_reserve_quantity = new AtomicReference<>(0);
        Amplify.DataStore.query(
            Reserve.class,
            Where.matches(Reserve.USERNAME.eq("tool").and(Reserve.ITEM.eq(test_check_items_arr.get(position)))),
            queryMatches -> {
                if (queryMatches.hasNext()) {
                    Reserve _reserve = queryMatches.next();
                    old_reserve_quantity.set(_reserve.getCount());
                    Amplify.DataStore.delete(
                        _reserve,
                        deleted -> {
                            Log.i("MyAmplifyApp", "Deleted an old reserve.");
                            latch.countDown();
                        },
                        failure -> {
                            Log.e("MyAmplifyApp", "Delete old reserve failed.", failure);
                            latch.countDown();
                        }
                    );
                } else {
                    latch.countDown();
                }
            },
            error -> {
//               Log.e("MyAmplifyApp", "Error retrieving items", error);
                latch.countDown();
            }
        );

        try {
            latch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        String formattedDateTime = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        if(old_reserve_quantity.get() + delta_reserve > 0)
        {
            Reserve reserve = Reserve.builder()
                    .username("tool")
                    .item(test_check_items_arr.get(position))
                    .count(old_reserve_quantity.get() + delta_reserve)
                    .starttime(new Temporal.DateTime(formattedDateTime))
                    .endtime(new Temporal.DateTime(formattedDateTime))
                    .build();

            Amplify.DataStore.save(
                    reserve,
                    result -> Log.i("MyAmplifyApp", "Reserve saved: " + result),
                    error -> Log.e("MyAmplifyApp", "Reserve error", error)
            );
        }
    }
}
