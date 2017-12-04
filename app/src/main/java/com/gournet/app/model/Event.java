package com.gournet.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SlobodanLjubic on 11/28/2017.
 */

public class Event implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("likestars_count")
    private int likestarsCount;

    @SerializedName("dislike_count")
    private int dislikeCount;

    @SerializedName("comment_count")
    private int commentCount;

    @SerializedName("curruser_status")
    private int curruserStatus;

    @SerializedName("when")
    private String when;

    @SerializedName("text")
    private String text;

    @SerializedName("business")
    private Business business;

    @SerializedName("distance")
    private Distance distance;

    public Event() {
    }


    public Event(int id, int likestarsCount, int dislikeCount, int commentCount, int curruserStatus, String when, String text, Business business,Distance distance) {
        super();
        this.id = id;
        this.likestarsCount = likestarsCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
        this.curruserStatus = curruserStatus;
        this.when = when;
        this.text = text;
        this.business = business;
        this.distance=distance;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLikestarsCount() {
        return likestarsCount;
    }

    public void setLikestarsCount(Integer likestarsCount) {
        this.likestarsCount = likestarsCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public int getCurruserStatus() {
        return curruserStatus;
    }

    public void setCurruserStatus(Integer curruserStatus) {
        this.curruserStatus = curruserStatus;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

}





