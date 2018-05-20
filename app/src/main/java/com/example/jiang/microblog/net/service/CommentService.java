package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.Microblog;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/4/14.
 */

public interface CommentService {

    @GET("comments/show.json")
    Observable<Comment> getComments(@Query("access_token") String access_token,
                                    @Query("id") String id, @Query("page") int page);

    @GET("statuses/mentions.json")
    Observable<Microblog> getAtMeWeibo(@Query("access_token") String access_token, @Query("page") int page);

    @GET("comments/mentions.json")
    Observable<Comment> getAtMeComment(@Query("access_token") String access_token, @Query("page") int page);

    @GET("comments/to_me.json")
    Observable<Comment> toMeComment(@Query("access_token") String access_token, @Query("page") int page);

    @GET("comments/by_me.json")
    Observable<Comment> byMeComment(@Query("access_token") String access_token, @Query("page") int page);
}
