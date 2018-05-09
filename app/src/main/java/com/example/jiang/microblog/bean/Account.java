package com.example.jiang.microblog.bean;

import java.io.Serializable;

/**
 * Created by jiang on 2018/4/24.
 */

public class Account implements Serializable {

    //用户的名字
    private String name;
    //用户的性别
    private String gender;
    //用户头像的url
    private String imageUrl;
    //用户的个人描述
    private String description;
    //关注数
    private String friends;
    //粉丝数
    private String followers;
    //微博数
    private String statuses;


    public Account() {
    }

    public Account(String name, String gender, String imageUrl, String description, String friends, String followers, String statuses) {
        this.name = name;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.description = description;
        this.friends = friends;
        this.followers = followers;
        this.statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", friends='" + friends + '\'' +
                ", followers='" + followers + '\'' +
                ", statuses='" + statuses + '\'' +
                '}';
    }
}
