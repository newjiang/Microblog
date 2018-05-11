package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Message;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface MessageContract {

    interface Model {
        //TODO 获取未读消息
        public Observable<Message> unread_count(String access_token, String uid);
    }
    interface View {
        //TODO 成功
        void onSuccess(Object object);

        //TODO 错误
        void onError(String result);
    }
    interface Presenter {
        //TODO 获取未读消息
        void unread_count(String access_token, String uid);
    }
}
