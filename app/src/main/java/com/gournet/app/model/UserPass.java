package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;


public class UserPass {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public UserPass(String username,String password)
    {
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
