package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Message;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface MessageContract {

    interface Model {
        /**
         * 获取未读消息
         *
         * @param access_token
         * @param uid
         * @return
         */
        public Observable<Message> unread_count(String access_token, String uid);
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
         * 获取未读消息
         *
         * @param access_token
         * @param uid
         */
        void unread_count(String access_token, String uid);
    }
}
