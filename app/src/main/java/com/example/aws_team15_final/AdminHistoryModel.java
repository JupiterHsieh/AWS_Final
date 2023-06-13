package com.example.aws_team15_final;

public class AdminHistoryModel {
    String transaction;
    String username;
    String item;
    String timeStamp;

    public AdminHistoryModel(String transaction, String username, String item, String timeStamp) {
        this.transaction = transaction;
        this.username = username;
        this.item = item;
        this.timeStamp = timeStamp;
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
}
