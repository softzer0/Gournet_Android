package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable
{

    @SerializedName("id")
   private int id;

    @SerializedName("username")
     private String username;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("born_ago")
     private int born;

    @SerializedName("address")
    private String address;

    @SerializedName("tz")
    private String timezone;

    @SerializedName("language")
    private String language;

    @SerializedName("currency")
    private String currency;

    @SerializedName("gender")
    private  int gender;

    @SerializedName("location")
    public Location location;


    User(int id,String username,String lastName,String firstName,
                String birthdate,int born,String address,String timezone,String language,String currency,int gender)
    {
        this.id=id;
         this.username=username;
        this.lastName=lastName;
        this.firstName=firstName;
        this.birthdate=birthdate;
        this.born=born;
        this.address=address;
        this.language=language;
        this.currency=currency;
        this.timezone=timezone;
        this.gender=gender;
    }


    public String getUsername() {
        return username;
    }

    public Double getLatitude(){return location.getLatitude();}
    public Double getLongitute() {return location.getLongitude();}

    public String getFullName()
    {
        return firstName+" "+lastName;
    }


    public String getBirthdate() {
        return birthdate;
    }

    public int getBorn() {
        return born;
    }

    public String getAddress() {
        return address;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getLanguage() {
        return language;
    }

    public String getCurrency() {
        return currency;
    }

    public int getGender() {
        return gender;
    }
}
