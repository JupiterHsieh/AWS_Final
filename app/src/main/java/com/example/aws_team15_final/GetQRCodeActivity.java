package com.example.aws_team15_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.storage.StorageAccessLevel;
import com.amplifyframework.storage.StorageException;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;
import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.amplifyframework.storage.s3.configuration.AWSS3PluginPrefixResolver;
import com.amplifyframework.storage.s3.configuration.AWSS3StoragePluginConfiguration;

import java.io.File;

public class GetQRCodeActivity extends AppCompatActivity {
    private ImageView QRCode_imageView;
    private String mail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_qrcode);

        QRCode_imageView = findViewById(R.id.QRCode_imageView);
        Button finish_btn = findViewById(R.id.finish_button);

        //Get Email
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    mail = attributes.get(attributes.size()-1).getValue().toString();
                    Log.i("AuthDemo", "User attributes = " + mail);
                    downloadFile(mail);},
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetQRCodeActivity.this,HomeActivity.class));
            }
        });
    }
    private void downloadFile(String mail){
        String key = mail + ".jpg";
        String filepath = getApplicationContext().getFilesDir() + "/" + key;
        File f = new File(filepath);
        Log.i("email.jpg: ",key);
        Amplify.Storage.downloadFile(
                "download/" + key,
                f,
                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                    Log.i("MyAmplifyApp","filepath: " + filepath);
                    if(f.exists()) {
                        Bitmap img = BitmapFactory.decodeFile(filepath);
                        QRCode_imageView.setImageBitmap(img);
                    }
                },
                error -> {
                    Log.e("MyAmplifyApp",  "Download Failure", error);
                    Toast.makeText(getApplicationContext(),"Download failed! Please check if there is a face in the photo",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(GetQRCodeActivity.this,HomeActivity.class));
                }
        );
    }
}
