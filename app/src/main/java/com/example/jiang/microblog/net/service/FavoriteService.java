package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.Favorites;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/5/11.
 */

public interface FavoriteService {

    @GET("favorites.json")
    Observable<Favorites> getFavorites(@Query("access_token") String access_token, @Query("page") int page);

   @GET("favorites/create.json")
    Observable<Favorites> createFavorites(@Field("access_token") String access_token, @Field("id") long id);

    @GET("favorites/destroy.json")
    Observable<Favorites> destroyFavorites(@Field("access_token") String access_token, @Field("id") long id);

}
