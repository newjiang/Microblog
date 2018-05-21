package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.bean.Microblog;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @POST("comments/create.json")
    Observable<Comment> create(@Field("access_token") String access_token, @Field("comment") String comment,
                               @Field("id") long id, @Field("comment_ori") int comment_ori);

    @FormUrlEncoded
    @POST("comments/reply.json")
    Observable<Comment> reply(@Field("access_token") String access_token, @Field("cid") long cid,
                              @Field("id") long id, @Field("comment") String comment,
                              @Field("without_mention") int without_mention, @Field("comment_ori") int comment_ori);

    @FormUrlEncoded
    @POST("comments/destroy.json")
    Observable<CommentsBean> destroy(@Field("access_token") String access_token, @Field("cid") long cid);

}
