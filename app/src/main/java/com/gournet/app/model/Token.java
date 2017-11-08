package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Token implements Serializable {
    @SerializedName("REVIEW_MIN_CHAR")
    public int reviewMinChar;

    @SerializedName("access")
    public String access;

    @SerializedName("NOTIF_PAGE_SIZE")
    public int notIfPageSize;

    @SerializedName("refresh")
    public String refresh;

    @SerializedName("user")
    public User user;
}
