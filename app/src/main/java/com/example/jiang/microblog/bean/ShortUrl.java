package com.example.jiang.microblog.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiang on 2018/5/19.
 */

public class ShortUrl implements Serializable{

    private List<UrlsBean> urls;

    public List<UrlsBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlsBean> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "urls=" + urls +
                '}';
    }
}
