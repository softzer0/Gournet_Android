package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable
{

    @SerializedName("id")
    int id;

    @SerializedName("username")
    String username;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("birthdate")
    String birthdate;

    @SerializedName("born_ago")
    int born;

    @SerializedName("address")
    String address;

    @SerializedName("tz")
    String timezone;

    @SerializedName("language")
    String language;

    @SerializedName("currency")
    String currency;

    @SerializedName("gender")
    int gender;

    @SerializedName("location")
    Location location;


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


}
