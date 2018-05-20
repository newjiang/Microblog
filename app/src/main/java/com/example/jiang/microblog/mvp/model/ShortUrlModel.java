package com.example.jiang.microblog.mvp.model;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.bean.ShortUrl;
import com.example.jiang.microblog.mvp.contract.ShortUrlContract;
import com.example.jiang.microblog.net.RetrofitHelper;
import com.example.jiang.microblog.net.service.ShortUrlService;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public class ShortUrlModel implements ShortUrlContract.Model {

    private ShortUrlService shortUrlService;

    public ShortUrlModel(Context context) {
        this.shortUrlService = RetrofitHelper.getInstance(context).getShortUrlService();
    }


    @Override
    public Observable<ShortUrl> expand(String access_token, String url_short) {
        Log.e("Model-token", access_token);
        Log.e("Model-url", url_short);
        return shortUrlService.expand(access_token, url_short);
    }
}
