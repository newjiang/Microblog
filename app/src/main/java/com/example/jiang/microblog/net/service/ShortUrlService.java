package com.example.jiang.microblog.net.service;

import com.example.jiang.microblog.bean.ShortUrl;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiang on 2018/4/14.
 */

public interface ShortUrlService {

    @GET("short_url/expand.json")
    Observable<ShortUrl> expand(@Query("access_token") String access_token,
                                @Query(value = "url_short", encoded = true)  String url_short);

}
