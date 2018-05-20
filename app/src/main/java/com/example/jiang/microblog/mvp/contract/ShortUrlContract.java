package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.ShortUrl;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface ShortUrlContract {

    interface Model {
        //TODO 获取微博评论列表
        public Observable<ShortUrl> expand(String access_token, String url_short);
    }
    interface View {
        //TODO 成功
        void onSuccess(Object object);

        //TODO 错误
        void onError(String result);
    }
    interface Presenter {
        //TODO 获取微博评论列表
        void expand(String access_token, String url_short);
    }
}
