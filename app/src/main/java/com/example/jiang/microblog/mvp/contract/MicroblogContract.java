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
        //TODO 获取用户及用户关注好友的微博
        public Observable<Microblog> getHomeMicroblog(String access_token, int page);
    }

    interface View {
        //TODO 成功
        void onSuccess(Object object);
        //TODO 错误
        void onError(String result);
    }

    interface Presenter {
        //TODO 获取用户及用户关注好友的微博
        void getHomeMicroblog(String access_token, int page);
    }
}
