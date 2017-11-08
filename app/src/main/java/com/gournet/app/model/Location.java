package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Location implements Serializable {

    @SerializedName("lat")
    public Double latitude;

    @SerializedName("lng")
    public Double longitude;


    public Location(double latitude,double longitude)
    {
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
