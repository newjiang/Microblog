package com.example.jiang.microblog.mvp.model;

import android.content.Context;

import com.example.jiang.microblog.bean.Favorites;
import com.example.jiang.microblog.mvp.contract.FavoriteContract;
import com.example.jiang.microblog.net.RetrofitHelper;
import com.example.jiang.microblog.net.service.FavoriteService;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public class FavoriteModel implements FavoriteContract.Model {

    private FavoriteService favoriteService;

    public FavoriteModel(Context context) {
        this.favoriteService = RetrofitHelper.getInstance(context).getFavoriteService();
    }

    @Override
    public Observable<Favorites> getFavorites(String access_token, int page) {
        return favoriteService.getFavorites(access_token, page);
    }

    @Override
    public Observable<Favorites> createFavorites(String access_token, long id) {
        return favoriteService.createFavorites(access_token, id);
    }

    @Override
    public Observable<Favorites> destroyFavorites(String access_token, long id) {
        return favoriteService.destroyFavorites(access_token, id);
    }

}
