package com.example.aws_team15_final;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private Context context;
    private String userPoolID = "us-east-2_iXjw6ueWb";
    private String clientID = "gnmo8iuvjfnjfpe3gc2sgovsk";
//    private String clientSecretID = ;
    private Regions regions = Regions.US_EAST_2;

    public CognitoSettings(Context context){
        this.context = context;
    }
    public String getUserPoolID(){
        return userPoolID;
    }

    public String getClientID() {
        return clientID;
    }

    public Regions getRegions() {
        return regions;
    }
}
