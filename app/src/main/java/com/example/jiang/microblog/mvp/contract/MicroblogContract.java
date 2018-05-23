package com.example.jiang.microblog.mvp.contract;

/**
 * Created by jiang on 2018/1/8.
 */


import com.example.jiang.microblog.bean.Microblog;

import rx.Observable;

/**
 * 契约接口
 */
public interface MicroblogContract {

    interface Model {
        /**
         * 获取用户及用户关注好友的微博
         *
         * @param access_token
         * @param page
         * @return
         */
        public Observable<Microblog> home_timeline(String access_token, int page);

        /**
         * 获取双向关注用户的最新微博
         *
         * @param access_token
         * @param page
         * @return
         */
        public Observable<Microblog> bilateral_timeline(String access_token, int page);

        /**
         * 获取最新的公共微博
         *
         * @param access_token
         * @param page
         * @return
         */
        public Observable<Microblog> public_timeline(String access_token, int page);

        /**
         * 当前用户的微博
         *
         * @param access_token
         * @param page
         * @param feature
         * @return
         */
        public Observable<Microblog> user_timeline(String access_token, int page, int feature);
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
         * 获取用户及用户关注好友的微博
         *
         * @param access_token
         * @param page
         */
        void home_timeline(String access_token, int page);

        /**
         * 获取双向关注用户的最新微博
         *
         * @param access_token
         * @param page
         */
        void bilateral_timeline(String access_token, int page);

        /**
         * 获取最新的公共微博
         *
         * @param access_token
         * @param page
         */
        void public_timeline(String access_token, int page);

        /**
         * 当前用户的微博
         *
         * @param access_token
         * @param page
         * @param feature
         */
        void user_timeline(String access_token, int page, int feature);
    }
}
