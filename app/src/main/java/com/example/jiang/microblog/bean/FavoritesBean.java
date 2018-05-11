package com.example.jiang.microblog.bean;

import java.util.List;

/**
 * Created by jiang on 2018/5/12.
 */

public class FavoritesBean {

    private Statuses status;
    private String favorited_time;
    private List<?> tags;

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public String getFavorited_time() {
        return favorited_time;
    }

    public void setFavorited_time(String favorited_time) {
        this.favorited_time = favorited_time;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }
}
