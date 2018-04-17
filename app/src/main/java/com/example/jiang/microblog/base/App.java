package com.example.jiang.microblog.base;

import android.app.Application;
import android.content.Context;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.litepal.LitePalApplication;

/**
 * Created by jiang on 2018/4/14.
 */

public class App extends Application {

    private static Context context;

    private static Oauth2AccessToken token;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        LitePalApplication.initialize(context);
        //获取token
        token = AccessTokenKeeper.readAccessToken(context);
    }
    public static Context getContext() {
        return context;
    }

    public static Oauth2AccessToken getToken() {
        return token;
    }

}
