package com.example.jiang.microblog.net;

import android.content.Context;

import com.example.jiang.microblog.net.service.CommentService;
import com.example.jiang.microblog.net.service.FavoriteService;
import com.example.jiang.microblog.net.service.MessageService;
import com.example.jiang.microblog.net.service.MicroblogService;
import com.example.jiang.microblog.net.service.ShortUrlService;
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

    private Context context;

    private Retrofit mRetrofit = null;

    OkHttpClient client = new OkHttpClient();

    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());

    private static RetrofitHelper instance = null;

    private RetrofitHelper(Context context) {
        this.context = context;
        init();
    }

    /**
     * 获取RetrofitHelper实例
     *
     * @param context
     * @return
     */
    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
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

    /**
     * 获取MicroblogService的实例
     *
     * @return
     */
    public MicroblogService getMicroblogServer() {
        return mRetrofit.create(MicroblogService.class);
    }

    /**
     * 获取UserService的实例
     *
     * @return
     */
    public UserService getUserServer() {
        return mRetrofit.create(UserService.class);
    }

    /**
     * 获取CommentService的实例
     *
     * @return
     */
    public CommentService getCommentService() {
        return mRetrofit.create(CommentService.class);
    }
    /**
     * 获取MessageService的实例
     *
     * @return
     */
    public MessageService getMessageService() {
        return mRetrofit.create(MessageService.class);
    }
    /**
     * 获取FavoriteService的实例
     *
     * @return
     */
    public FavoriteService getFavoriteService() {
        return mRetrofit.create(FavoriteService.class);
    }
    /**
     * 获取ShortUrlService的实例
     *
     * @return
     */
    public ShortUrlService getShortUrlService() {
        return mRetrofit.create(ShortUrlService.class);
    }
}

