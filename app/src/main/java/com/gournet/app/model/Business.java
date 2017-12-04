package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SlobodanLjubic on 11/29/2017.
 */

public class Business implements Serializable
{

    @SerializedName("id")
    private int id;

    @SerializedName("shortname")
    private String shortname;

    @SerializedName("name")
    private String name;

    @SerializedName("type_display")
    private String typeDisplay;



    public Business() {
    }

    public Business(int id, String shortname, String name, String typeDisplay) {
        super();
        this.id = id;
        this.shortname = shortname;
        this.name = name;
        this.typeDisplay = typeDisplay;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeDisplay() {
        return typeDisplay;
    }

    public void setTypeDisplay(String typeDisplay) {
        this.typeDisplay = typeDisplay;
    }

}
