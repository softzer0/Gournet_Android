package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Location implements Serializable {

    @SerializedName("lat")
    private Double latitude;

    @SerializedName("lng")
    private Double longitude;


    public Location(double latitude,double longitude)
    {
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
