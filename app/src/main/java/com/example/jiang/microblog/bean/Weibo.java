package com.example.jiang.microblog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/25.
 */

public class Weibo implements Serializable {

    String mid; //TODO 微博的mid
    String header; //TODO 用户头像
    String name; //TODO 用户名字
    String content; //TODO 微博文字内容
    List<String> pictureList = new ArrayList<>(); //TODO 微博配图

    String time; //TODO 发布时间
    String from; //TODO 来源
    String like; //TODO 点赞数
    String redirect; //TODO 转发数
    String comment; //TODO 评论数

    String retweetedUser; //TODO 转发微博微博的作者
    String retweetedContent; //TODO 转发微博文字内容
    List<String> retweetedPicture = new ArrayList<>(); //TODO 转发微博配图

    public Weibo() {
    }

    public Weibo(String mid, String header, String name, String content, List<String> pictureList, String time, String from, String like, String redirect, String comment, String retweetedUser, String retweetedContent, List<String> retweetedPicture) {
        this.mid = mid;
        this.header = header;
        this.name = name;
        this.content = content;
        this.pictureList = pictureList;
        this.time = time;
        this.from = from;
        this.like = like;
        this.redirect = redirect;
        this.comment = comment;
        this.retweetedUser = retweetedUser;
        this.retweetedContent = retweetedContent;
        this.retweetedPicture = retweetedPicture;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRetweetedUser() {
        return retweetedUser;
    }

    public void setRetweetedUser(String retweetedUser) {
        this.retweetedUser = retweetedUser;
    }

    public String getRetweetedContent() {
        return retweetedContent;
    }

    public void setRetweetedContent(String retweetedContent) {
        this.retweetedContent = retweetedContent;
    }

    public List<String> getRetweetedPicture() {
        return retweetedPicture;
    }

    public void setRetweetedPicture(List<String> retweetedPicture) {
        this.retweetedPicture = retweetedPicture;
    }

    @Override
    public String toString() {
        return "Weibo{" +
                "mid='" + mid + '\'' +
                ", header='" + header + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", pictureList=" + pictureList +
                ", time='" + time + '\'' +
                ", from='" + from + '\'' +
                ", like='" + like + '\'' +
                ", redirect='" + redirect + '\'' +
                ", comment='" + comment + '\'' +
                ", retweetedUser='" + retweetedUser + '\'' +
                ", retweetedContent='" + retweetedContent + '\'' +
                ", retweetedPicture=" + retweetedPicture +
                '}';
    }
}
