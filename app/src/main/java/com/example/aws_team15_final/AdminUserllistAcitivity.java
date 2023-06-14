package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;

public class AdminUserllistAcitivity extends AppCompatActivity {

    ArrayList<AdminUserModel> adminUserModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_userllist_acitivity);
        RecyclerView recyclerView = findViewById(R.id.user_recycleview);
        setUpUserModels();

        UserViewAdapter adapter = new UserViewAdapter(this, adminUserModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpUserModels(){
//        //TODO AWS
//        String[] username = {"Peter","Jupiter","Danny","AAA","BBB","CCC"};
//
//        for(int i =0; i<username.length;i++){
//            adminUserModels.add(new AdminUserModel(username[i]));
//        }
        Amplify.DataStore.query(User.class,
            matches -> {
                while (matches.hasNext()) {
                    User _user = matches.next();
                    adminUserModels.add(new AdminUserModel(_user.getUsername(), _user.getCoin().toString()));
                }
            },
            failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );
    }
}