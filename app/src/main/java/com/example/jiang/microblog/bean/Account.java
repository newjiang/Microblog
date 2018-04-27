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

    public Account() {
    }

    public Account(String name, String gender, String imageUrl, String description) {
        this.name = name;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.description = description;
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

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
