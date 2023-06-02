package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.SignInBtn);
        EditText edUsername = (EditText)findViewById(R.id.editTextTextPersonName);
        EditText edPassword = (EditText)findViewById(R.id.editTextTextPassword);
        Switch stch = findViewById(R.id.switch1);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //ToDo AWS Cognito
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                if(username.length() > 0 && password.length()>0){
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    Toast.makeText(getApplicationContext(),"Login Success!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
                }
            }
        });

        stch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    stch.setText("Admin");
                }else{
                    stch.setText("User");
                }

            }
        });


    }
}