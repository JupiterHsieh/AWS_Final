package com.example.aws_team15_final;

public class UserReportModel {
    String transaction;
    String item;
    String timeStamp;
    String cnt;

    public UserReportModel(String transaction, String item, String timeStamp, String cnt) {
        this.transaction = transaction;
        this.item = item;
        this.timeStamp = timeStamp;
        this.cnt = cnt;
    }


    public String getTransaction() {
        return transaction;
    }

    public String getItem() {
        return item;
    }

    public String getTimeStamp() {
        String tmp = timeStamp.substring(34,53);
        tmp = tmp.replace("T"," ");
        return tmp;
    }
    public String getCnt() { return cnt; }
}
