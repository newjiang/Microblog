package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.Message;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/4/14.
 */

public interface MessageService {
    //TODO 获取未读消息
    @GET("remind/unread_count.json")
    Observable<Message> unread_count(@Query("access_token")String access_token, @Query("uid")String uid);

}
