package com.example.jiang.microblog.net;

import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/4/14.
 */

public interface MicroblogService {
    //TODO 获取用户信息
    @GET("users/show.json")
    Observable<User> getProfile(@Query("uid") String uid, @Query("access_token") String access_token);
    //TODO 获取用户及用户关注好友的微博
    @GET("statuses/home_timeline.json")
    Observable<Microblog> getAllMicroblog(@Query("access_token") String access_token);
}
