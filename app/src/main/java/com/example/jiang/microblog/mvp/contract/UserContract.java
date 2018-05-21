package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Friend;
import com.example.jiang.microblog.bean.User;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface UserContract {

    interface Model {
        /**
         * 通过uid获取用户信息
         * @param access_token
         * @param uid
         * @return
         */
        public Observable<User> getProfile(String access_token, String uid);

        /**
         * 通过用户名获取用户信息
         * @param access_token
         * @param screen_name
         * @return
         */
        public Observable<User> getProfileByName(String access_token, String screen_name);

        public Observable<Friend> getNextFriendList(String access_token, String uid, int next_cursor);

        public Observable<Friend> getPreFriendList(String access_token, String uid, int previous_cursor);
    }

    interface View {
        /**
         * 成功
         * @param object
         */
        void onSuccess(Object object);

        /**
         * 错误
         * @param result
         */
        void onError(String result);
    }

    interface Presenter {
        /**
         * 通过uid获取用户信息
         * @param access_token
         * @param uid
         */
        void getProfile(String access_token, String uid);

        /**
         * 通过用户名获取用户信息
         * @param access_token
         * @param screen_name
         */
        void getProfileByName(String access_token, String screen_name);

        void getNextFriendList(String access_token, String uid, int next_cursor);

        void getPreFriendList(String access_token, String uid, int previous_cursor);
    }
}
