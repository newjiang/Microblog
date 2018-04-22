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
    Observable<Microblog> getHomeMicroblog(@Query("access_token") String access_token);
}
