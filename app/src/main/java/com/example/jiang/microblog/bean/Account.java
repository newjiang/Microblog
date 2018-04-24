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
    private String friendsCount;
    //粉丝数
    private String followersCount;
    //微博数
    private String statusesCount;

    public Account() {
    }

    public Account(String name, String gender, String imageUrl, String description, String friendsCount, String followersCount, String statusesCount) {
        this.name = name;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.description = description;
        this.friendsCount = friendsCount;
        this.followersCount = followersCount;
        this.statusesCount = statusesCount;
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

    public String getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(String friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(String followersCount) {
        this.followersCount = followersCount;
    }

    public String getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(String statusesCount) {
        this.statusesCount = statusesCount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", friendsCount='" + friendsCount + '\'' +
                ", followersCount='" + followersCount + '\'' +
                ", statusesCount='" + statusesCount + '\'' +
                '}';
    }
}
