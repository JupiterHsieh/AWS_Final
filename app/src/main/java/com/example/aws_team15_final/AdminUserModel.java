package com.example.aws_team15_final;

public class AdminUserModel {

    String username;
    String coin;

    public AdminUserModel( String username, String coin) {
        this.username = username;
        this.coin = coin;
    }

    public String getUsername() {
        return username;
    }
    public String getCoin(){ return coin; }
}
