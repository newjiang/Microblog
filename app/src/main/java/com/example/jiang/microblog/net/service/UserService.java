package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.Friend;
import com.example.jiang.microblog.bean.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/4/14.
 */

public interface UserService {
    //TODO 通过uid获取用户信息
    @GET("users/show.json")
    Observable<User> getProfile(@Query("access_token") String access_token, @Query("uid") String uid);

    //TODO 通过用户名获取用户信息
    @GET("users/show.json")
    Observable<User> getProfileByName(@Query("access_token")String access_token,
                                      @Query("screen_name")String screen_name);

    @GET("friendships/friends.json")
    Observable<Friend> getNextFriendList(@Query("access_token")String access_token, @Query("uid")String uid,
                                         @Query("next_cursor")int next_cursor);

    @GET("friendships/friends.json")
    Observable<Friend> getPreFriendList(@Query("access_token")String access_token, @Query("uid")String uid,
                                         @Query("previous_cursor")int previous_cursor);

}
