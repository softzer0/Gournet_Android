package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SlobodanLjubic on 11/18/2017.
 */

public class Marker {

    @SerializedName("id")
   private Integer id;

    @SerializedName("shortname")
   private String shortname;

    @SerializedName("name")
   private String name;

    @SerializedName("type_display")
   private String typeDisplay;

    private Location location;

    public Marker(int id,String shortname,String name,String typeDisplay)
    {
        this.id=id;
        this.shortname=shortname;
        this.name=name;
        this.typeDisplay=typeDisplay;
    }

    public Location getLocation()
    {
        return location;
    }

    public Integer getId() {
        return id;
    }

    public String getShortname() {
        return shortname;
    }

    public String getName() {
        return name;
    }

    public String getTypeDisplay() {
        return typeDisplay;
    }
}
