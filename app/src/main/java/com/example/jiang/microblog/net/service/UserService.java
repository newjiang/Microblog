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
    //TODO 获取用户信息
    @GET("users/show.json")
    Observable<User> getProfile(@Query("uid") String uid, @Query("access_token") String access_token);

    @GET("friendships/friends.json")
    Observable<Friend> getNextFriendList(@Query("access_token")String access_token, @Query("uid")String uid,
                                         @Query("next_cursor")int next_cursor);
    @GET("friendships/friends.json")
    Observable<Friend> getPreFriendList(@Query("access_token")String access_token, @Query("uid")String uid,
                                         @Query("previous_cursor")int previous_cursor);
}
