package com.example.jiang.microblog.net;

import android.content.Context;

import com.example.jiang.microblog.net.service.CommentService;
import com.example.jiang.microblog.net.service.MicroblogService;
import com.example.jiang.microblog.net.service.UserService;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiang on 2018/1/8.
 */

public class RetrofitHelper {

    private Context mCntext;

    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    private RetrofitHelper(Context mContext) {
        mCntext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/2/")
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public MicroblogService getMicroblogServer() {
        return mRetrofit.create(MicroblogService.class);
    }

    public UserService getUserServer() {
        return mRetrofit.create(UserService.class);
    }

    public CommentService getCommentService() {
        return mRetrofit.create(CommentService.class);
    }
}

