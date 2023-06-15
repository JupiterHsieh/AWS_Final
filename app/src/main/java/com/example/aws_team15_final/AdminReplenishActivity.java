
package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class AdminReplenishActivity extends AppCompatActivity implements ReplenishRecyclerViewAdapter.ReplenishButtonListener{
    private RecyclerView replenish_item_recyclerview;
    ReplenishRecyclerViewAdapter replenishRecyclerViewAdapter;
    private ArrayList<Integer> test_img_arr = new ArrayList<>();
    private ArrayList<String> test_replenish_items_arr = new ArrayList<>();
    private ArrayList<Integer> test_replenish_quantity_arr = new ArrayList<>();
    private ArrayList<Integer> test_replenish_count_arr = new ArrayList<>();

    private Map<String, Integer> imageMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_replenish);

        imageMap.put("item1", R.drawable.noodles);
        imageMap.put("item2", R.drawable.oatbar);
        imageMap.put("item3", R.drawable.doritos);
        imageMap.put("item4", R.drawable.milkpuff);
        imageMap.put("item5", R.drawable.koala);
        imageMap.put("item6", R.drawable.lays);
        getInv();

        replenish_item_recyclerview = findViewById(R.id.replenish_RV);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(AdminReplenishActivity.this, 2);
        replenish_item_recyclerview.setLayoutManager(gridlayoutManager);
        replenishRecyclerViewAdapter = new ReplenishRecyclerViewAdapter(test_img_arr,test_replenish_items_arr,test_replenish_quantity_arr,test_replenish_count_arr,this);
        replenish_item_recyclerview.setAdapter(replenishRecyclerViewAdapter);
        replenish_item_recyclerview.setHasFixedSize(true);
    }
    protected void onStart(){
        super.onStart();
//        getInv();
    }
    public void getInv(){
        test_replenish_items_arr.clear();
        test_replenish_quantity_arr.clear();
        test_replenish_count_arr.clear();
        test_img_arr.clear();

        Amplify.DataStore.query(Items.class,
                queryMatches -> {
                    while (queryMatches.hasNext()) {
                        Items item = queryMatches.next();
                        Log.i("MyAmplifyApp", "Successful query, found item: " + item.getItem() + ", " + item.getCount());

                        try {
                            test_replenish_items_arr.add(item.getItem());
                            test_replenish_quantity_arr.add(item.getCount());
                            test_replenish_count_arr.add(1);
                            if (imageMap.containsKey(item.getItem())) {
                                int resourceId = imageMap.get(item.getItem());
                                test_img_arr.add(resourceId);
                            }

                            Log.i("MyAmplify", "test img arr len " + test_img_arr.size());
                        } catch (ArithmeticException e) {
                            Log.i("MyAmplifyApp", String.valueOf(e));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("MyAmplify", "test arr len " + test_img_arr.size());

                            setContentView(R.layout.activity_admin_replenish);
                            replenish_item_recyclerview = findViewById(R.id.replenish_RV);
                            GridLayoutManager gridlayoutManager = new GridLayoutManager(AdminReplenishActivity.this, 2);
                            replenish_item_recyclerview.setLayoutManager(gridlayoutManager);
                            replenishRecyclerViewAdapter = new ReplenishRecyclerViewAdapter(test_img_arr, test_replenish_items_arr,test_replenish_quantity_arr,test_replenish_count_arr,AdminReplenishActivity.this);
                            replenish_item_recyclerview.setAdapter(replenishRecyclerViewAdapter);
                            replenish_item_recyclerview.setHasFixedSize(true);
                        }
                    });
                },
                error -> Log.e("MyAmplifyApp", "Error retrieving items", error)
        );
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
        updateInv(test_replenish_items_arr.get(position), test_replenish_count_arr.get(position));
        getInv();
    }
    private void updateInv(String item_name, Integer deltaInv){
        AtomicReference<Integer> item_old_cnt = new AtomicReference<>(0);
        AtomicReference<List<Integer>> item_old_rule = new AtomicReference<>();
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