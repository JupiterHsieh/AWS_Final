package com.example.aws_team15_final;

public class SnackApplyModel {
    String username;
    String snacksname;
    String snackquantity;
    int image;

    public SnackApplyModel(String username, String snacksname, String snackquantity, int image) {
        this.username = username;
        this.snacksname = snacksname;
        this.snackquantity = snackquantity;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getSnacksname() {
        return snacksname;
    }

    public String getSnackquantity() {
        return snackquantity;
    }

    public int getImage() {
        return image;
    }
}
