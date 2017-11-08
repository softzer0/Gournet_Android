package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;


public class UserPass {

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    public UserPass(String username,String password)
    {
        this.username=username;
        this.password=password;
    }
}
