package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.ShortUrl;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface ShortUrlContract {

    interface Model {
        /**
         * 获取微博评论列表
         *
         * @param access_token
         * @param url_short
         * @return
         */
        public Observable<ShortUrl> expand(String access_token, String url_short);
    }
    interface View {
        /**
         * 成功
         *
         * @param object
         */
        void onSuccess(Object object);

        /**
         * 错误
         *
         * @param result
         */
        void onError(String result);
    }
    interface Presenter {
        /**
         * 获取微博评论列表
         *
         * @param access_token
         * @param url_short
         */
        void expand(String access_token, String url_short);
    }
}
