package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class PicUrlsBean {

    private String thumbnail_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {

        this.thumbnail_pic = thumbnail_pic;
    }

    @Override
    public String toString() {
        return "PicUrlsBean{" +
                "thumbnail_pic='" + thumbnail_pic + '\'' +
                '}';
    }
}