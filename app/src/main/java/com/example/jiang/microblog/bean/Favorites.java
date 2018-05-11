package com.example.jiang.microblog.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiang on 2018/5/11.
 */

public class Favorites implements Serializable {

    private int total_number;
    private List<FavoritesBean> favorites;

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public List<FavoritesBean> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoritesBean> favorites) {
        this.favorites = favorites;
    }

}
