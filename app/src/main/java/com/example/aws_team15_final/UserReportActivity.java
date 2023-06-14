package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Useraction;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class UserReportActivity extends AppCompatActivity {

    ArrayList<UserReportModel> UserReportModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        UserReportModels.add(new UserReportModel("000", "item1", "2023-06-14T15:14:14.153797Z", "0"));
        final CountDownLatch latch = new CountDownLatch(1);

        Amplify.DataStore.query(
            Useraction.class,
            Where.matches(Useraction.USERNAME.eq(MainActivity.public_username)),
            matches -> {
                UserReportModels.clear();
                while (matches.hasNext()) {
                    Useraction _useraction = matches.next();
                    UserReportModels.add(new UserReportModel(
                            _useraction.getId(),
                            _useraction.getItem(),
                            _useraction.getTime().toString(),
                            _useraction.getCount().toString()
                    ));
                    Log.i("MyAmplifyApp", "len" + UserReportModels.size());
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

        setContentView(R.layout.activity_user_report);
        RecyclerView recyclerView = findViewById(R.id.UReportrecycleView);
        UserReportViewAdapter adapter = new UserReportViewAdapter(UserReportActivity.this, UserReportModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserReportActivity.this));


    }
}