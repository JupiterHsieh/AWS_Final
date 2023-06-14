package com.example.aws_team15_final;

import com.amplifyframework.core.model.temporal.Temporal;

public class AdminHistoryModel {
    String transaction;
    String username;
    String item;
    String timeStamp;
    String cnt;

    public AdminHistoryModel(String transaction, String username, String item, String timeStamp, String cnt) {
        this.transaction = transaction;
        this.username = username;
        this.item = item;
        this.timeStamp = timeStamp;
        this.cnt = cnt;
    }


    public String getTransaction() {
        return transaction;
    }

    public String getUsername() {
        return username;
    }

    public String getItem() {
        return item;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    public String getCnt() { return cnt; }
}
