package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Friend;
import com.example.jiang.microblog.bean.User;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface UserContract {

    interface Model {
        //TODO 通过uid获取用户信息
        public Observable<User> getProfile(String access_token, String uid);
        //TODO 通过用户名获取用户信息
        public Observable<User> getProfileByName(String access_token, String screen_name);

        public Observable<Friend> getNextFriendList(String access_token, String uid, int next_cursor);

        public Observable<Friend> getPreFriendList(String access_token, String uid, int previous_cursor);
    }

    interface View {
        //TODO 成功
        void onSuccess(Object object);
        //TODO 错误
        void onError(String result);
    }

    interface Presenter {
        //TODO 通过uid获取用户信息
        void getProfile(String access_token, String uid);
        //TODO 通过用户名获取用户信息
        void getProfileByName(String access_token, String screen_name);

        void getNextFriendList(String access_token, String uid, int next_cursor);

        void getPreFriendList(String access_token, String uid, int previous_cursor);
    }
}
