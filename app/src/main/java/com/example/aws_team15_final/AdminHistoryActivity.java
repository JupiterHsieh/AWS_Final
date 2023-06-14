package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Useraction;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
public class AdminHistoryActivity extends AppCompatActivity {

    ArrayList<AdminHistoryModel> adminHistoryModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final CountDownLatch latch = new CountDownLatch(1);

        Amplify.DataStore.query(
                Useraction.class,
                matches -> {
                    adminHistoryModels.clear();
                    while (matches.hasNext()) {
                        Useraction _useraction = matches.next();
                        adminHistoryModels.add(new AdminHistoryModel(
                                _useraction.getId(),
                                _useraction.getUsername(),
                                _useraction.getItem(),
                                _useraction.getTime().toString(),
                                _useraction.getCount().toString()
                        ));
//                        adminHistoryModels.add(new AdminHistoryModel(_useraction.getId(), _useraction.getUsername(), _useraction.getItem(), OffsetDateTime.parse(_useraction.getTime().toString()).format(DateTimeFormatter.ofPattern("MM/dd HH:mm")), _useraction.getCount().toString()));
                        Log.i("MyAmplifyApp", "len" + adminHistoryModels.size());
                        Log.i("MyAmplifyApp", "User action " + _useraction.toString());
                    }
                    latch.countDown(); // 唤醒主线程，计数减一
                },
                failure -> {
                    Log.i("MyAmplifyApp", "User action query failed ", failure);
                    latch.countDown(); // 失败时也要唤醒主线程，计数减一
                }
        );

        try {
            latch.await(); // 阻塞主线程，直到计数为0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_admin_history);
        RecyclerView recyclerView = findViewById(R.id.HrecycleView);
        HistoryViewAdapter adapter = new HistoryViewAdapter(AdminHistoryActivity.this, adminHistoryModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminHistoryActivity.this));

    }
}