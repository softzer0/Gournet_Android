package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {
    @SerializedName("REVIEW_MIN_CHAR")
    private int reviewMinChar;

    @SerializedName("access")
    private TokenObj access;

    @SerializedName("NOTIF_PAGE_SIZE")
    private int notIfPageSize;

    @SerializedName("refresh")
    private TokenObj refresh;

    @SerializedName("refresh_date")
    private long refresh_date;

    @SerializedName("access_date")
    private long access_date;

    @SerializedName("user")
    private User user;

    public Token(TokenObj access,TokenObj refresh)
    {
     this.access=access;
     this.refresh=refresh;
    }

    public User getUser()
    {
        return user;
    }




    public long getRefresh_Date() {
        return refresh_date;
    }

    public long getAccess_Date() {
        return access_date;
    }


    public TokenObj getAccess() {
        return access;
    }

    public TokenObj getRefresh() {
        return refresh;
    }
}
