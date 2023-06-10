package com.example.aws_team15_final;

import androidx.appcompat.app.AppCompatActivity;

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
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.cognito.result.GlobalSignOutError;
import com.amplifyframework.auth.cognito.result.HostedUIError;
import com.amplifyframework.auth.cognito.result.RevokeTokenError;
import com.amplifyframework.core.Amplify;
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
        } catch (AmplifyException e) {
            throw new RuntimeException(e);
        }
        try {
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
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                Amplify.Auth.signIn(
                        username,
                        password,
                        result -> isLogin(getApplicationContext(),result.isSignedIn()),
                        error -> LoginError(getApplicationContext(),error.toString())
                );
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
        Amplify.Auth.signOut( signOutResult -> {
            if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                // Sign Out completed fully and without errors.
                Log.i("AuthQuickStart", "Signed out successfully");
            } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                // Sign Out completed with some errors. User is signed out of the device.
                AWSCognitoAuthSignOutResult.PartialSignOut partialSignOutResult =
                        (AWSCognitoAuthSignOutResult.PartialSignOut) signOutResult;

                HostedUIError hostedUIError = partialSignOutResult.getHostedUIError();
                if (hostedUIError != null) {
                    Log.e("AuthQuickStart", "HostedUI Error", hostedUIError.getException());
                    // Optional: Re-launch hostedUIError.getUrl() in a Custom tab to clear Cognito web session.
                }

                GlobalSignOutError globalSignOutError = partialSignOutResult.getGlobalSignOutError();
                if (globalSignOutError != null) {
                    Log.e("AuthQuickStart", "GlobalSignOut Error", globalSignOutError.getException());
                    // Optional: Use escape hatch to retry revocation of globalSignOutError.getAccessToken().
                }

                RevokeTokenError revokeTokenError = partialSignOutResult.getRevokeTokenError();
                if (revokeTokenError != null) {
                    Log.e("AuthQuickStart", "RevokeToken Error", revokeTokenError.getException());
                    // Optional: Use escape hatch to retry revocation of revokeTokenError.getRefreshToken().
                }
            } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                AWSCognitoAuthSignOutResult.FailedSignOut failedSignOutResult =
                        (AWSCognitoAuthSignOutResult.FailedSignOut) signOutResult;
                // Sign Out failed with an exception, leaving the user signed in.
                Log.e("AuthQuickStart", "Sign out Failed", failedSignOutResult.getException());
            }
        });
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