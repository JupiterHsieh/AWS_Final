package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInActivity;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.SignInBtn);
        EditText edUsername = (EditText)findViewById(R.id.editTextTextPersonName);
        EditText edPassword = (EditText)findViewById(R.id.editTextTextPassword);
        Switch stch = findViewById(R.id.switch1);

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

        } catch (AmplifyException e) {
            throw new RuntimeException(e);
        }

        LogOut();
        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

        Injection.initialize(getApplicationContext());
        final IdentityManager identityManager = Injection.getAWSService().getIdentityManager();
//
        identityManager.login(this, new DefaultSignInResultHandler() {
            @Override
            public void onSuccess(Activity activity, IdentityProvider identityProvider) {
                Toast.makeText(MainActivity.this, String.format("Logged in as %s", identityManager.getCachedUserID()), Toast.LENGTH_LONG).show();
                // Go to the home activity
                final Intent intent = new Intent(activity, HomeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();
            }
            @Override
            public boolean onCancel(Activity activity) {
                return false;
            }
        });
//        // Start the authentication UI
//        AuthUIConfiguration config = new AuthUIConfiguration.Builder()
//                .userPools(true)
//                .build();
//        SignInActivity.startSignInActivity(this,config);
//        MainActivity.this.finish();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //ToDo AWS Cognito
            public void onClick(View view) {
                if(stch.isChecked()==false){
                    String username = edUsername.getText().toString();
                    String password = edPassword.getText().toString();
                    Amplify.Auth.signIn(
                            username,
                            password,
                            result -> isLogin(getApplicationContext(),result.isSignInComplete()),
                            error -> LoginError(getApplicationContext(),error.toString())
                    );
                }else{
                    startActivity(new Intent(MainActivity.this,AdminActivity.class));
                }

//                if(username.length() > 0 && password.length()>0){
//                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                    Toast.makeText(getApplicationContext(),"Login Success!",Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
//                }
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
    @Override
    protected void onRestart() {
        super.onRestart();
        LogOut();
    }
    private void LogOut(){
        Amplify.Auth.signOut(
                () -> Log.i("AuthQuickstart", "Signed out successfully"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }

    private void isLogin(Context context, boolean isSignIn){
        if(isSignIn){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            Log.i("AuthQuickstart","Sign in succeeded");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Sign in succeeded",Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            MainActivity.this.finish();
            Log.i("AuthQuickstart","Sign in not complete");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Sign in failed",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void LoginError(Context context,String error){
        Log.e("AuthQuickstartError", error);
        String[] tokens = error.split(",");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,tokens[0] + "}",Toast.LENGTH_LONG).show();
            }
        });
    }
}