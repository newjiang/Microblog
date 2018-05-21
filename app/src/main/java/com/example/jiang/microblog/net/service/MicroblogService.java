package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.Microblog;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/4/14.
 */

public interface MicroblogService {

    //TODO 获取用户及用户关注好友的微博
    @GET("statuses/home_timeline.json")
    Observable<Microblog> home_timeline(@Query("access_token") String access_token, @Query("page") int page);

    //TODO 获取双向关注用户的最新微博
    @GET("statuses/bilateral_timeline.json")
    Observable<Microblog> bilateral_timeline(@Query("access_token") String access_token, @Query("page") int page);

    //TODO 获取最新的公共微博
    @GET("statuses/public_timeline.json")
    Observable<Microblog> public_timeline(@Query("access_token") String access_token, @Query("page") int page);

    //TODO 当前用户的微博
    @GET("statuses/user_timeline.json")
    Observable<Microblog> user_timeline(@Query("access_token") String access_token,
                                        @Query("page") int page, @Query("feature") int feature);

}
