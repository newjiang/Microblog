package com.example.jiang.microblog.bean;

import java.io.Serializable;

/**
 * Created by jiang on 2018/4/27.
 */

public class Hot implements Serializable {

    private String title;
    private String hot;
    private String count;

    public Hot() {
    }

    public Hot(String title, String hot, String count) {
        this.title = title;
        this.hot = hot;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
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
                ", hot='" + hot + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
