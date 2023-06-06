package com.example.aws_team15_final;

import android.content.Context;

public class Injection {
    private static AWSService awsService = null;
    public static synchronized AWSService getAWSService() {
        return awsService;
    }

    public static synchronized void initialize(Context context) {
        if (awsService == null) {
            awsService = new AWSService(context);
        }
    }
}
