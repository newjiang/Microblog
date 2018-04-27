package com.example.jiang.microblog.bean;

import java.io.Serializable;

/**
 * Created by jiang on 2018/4/27.
 */

public class Hot implements Serializable {

    private String title;
    private String degree;
    private String count;

    public Hot() {
    }

    public Hot(String title, String degree, String count) {
        this.title = title;
        this.degree = degree;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Hot{" +
                "title='" + title + '\'' +
                ", degree='" + degree + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
