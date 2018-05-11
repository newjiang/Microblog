package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/4/18.
 */

public class Emotion {

    private String phrase;
    private String type;
    private String url;
    private boolean hot;
    private boolean common;
    private String category;
    private String icon;
    private String value;
    private String picid;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    @Override
    public String toString() {
        return "Emotion{" +
                "phrase='" + phrase + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", hot=" + hot +
                ", common=" + common +
                ", category='" + category + '\'' +
                ", icon='" + icon + '\'' +
                ", value='" + value + '\'' +
                ", picid='" + picid + '\'' +
                '}';
    }
}
