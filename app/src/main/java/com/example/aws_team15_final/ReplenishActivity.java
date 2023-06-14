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

public class ReplenishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateInv("item3", 2);
        setContentView(R.layout.activity_admin_replenish);

    }

    private void updateInv(String item_name, Integer deltaInv){
        AtomicReference<Integer> item_old_cnt = new AtomicReference<>(0);
        AtomicReference<List<Integer>> item_old_rule = new AtomicReference<>();
//        Amplify.DataStore.query(Items.class,
//            item_matches ->{
//                while(item_matches.hasNext())
//                {
//                    Items _item = item_matches.next();
//                    item_old_cnt.set(_item.getCount() + deltaInv);
//                    item_old_rule.set(_item.getRule());
////                    Log.i("MyAmplifyApp", "Queried item " + _item.getItem());
//                    Amplify.DataStore.delete(_item,
//                        deleted -> Log.i("MyAmplifyApp", "Deleted item old" + deleted),
//                        failure -> Log.e("MyAmplifyApp", "Delete item old failed.", failure)
//                    );
//                }
//            },
//            error -> Log.i("MyAmplifyApp", "Query Items error", error)
//        );
//        Items item_new = Items.builder()
//            .item(item_name)
//            .count(item_old_cnt.get())
//            .rule(item_old_rule.get())
//            .build();
//        Amplify.DataStore.save(item_new,
//            saved -> Log.i("MyAmplifyApp", "Saved item new "),
//            failed -> Log.i("MyAmplifyApp", "Save item new failed ", failed)
//        );
        final CountDownLatch queryLatch = new CountDownLatch(1);
        final CountDownLatch deleteLatch = new CountDownLatch(1);
        final CountDownLatch saveLatch = new CountDownLatch(1);

        Amplify.DataStore.query(
            Items.class,
            Where.matches(Items.ITEM.eq(item_name)),
            item_matches -> {
                while (item_matches.hasNext()) {
                    Items _item = item_matches.next();
                    item_old_cnt.set(_item.getCount() + deltaInv);
                    item_old_rule.set(_item.getRule());

                    // 同步执行删除操作
                    Amplify.DataStore.delete(
                            _item,
                            deleted -> {
                                Log.i("MyAmplifyApp", "Deleted item old" + deleted);
                                deleteLatch.countDown(); // 删除操作完成，计数减一
                            },
                            failure -> {
                                Log.e("MyAmplifyApp", "Delete item old failed.", failure);
                                deleteLatch.countDown(); // 删除操作失败，计数减一
                            }
                    );
                }
                queryLatch.countDown();
            },
            error -> {
                Log.i("MyAmplifyApp", "Query Items error", error);
                queryLatch.countDown();
            }
        );

        try {
            queryLatch.await();
            deleteLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Items item_new = Items.builder()
                .item(item_name)
                .count(item_old_cnt.get())
                .rule(item_old_rule.get())
                .build();

        Amplify.DataStore.save(
                item_new,
                saved -> {
                    Log.i("MyAmplifyApp", "Saved item new");
                    saveLatch.countDown();
                },
                failed -> {
                    Log.i("MyAmplifyApp", "Save item new failed", failed);
                    saveLatch.countDown();
                }
        );

        try {
            saveLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
