package com.example.jiang.microblog.mvp.model;

import android.content.Context;

import com.example.jiang.microblog.bean.Friend;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.net.RetrofitHelper;
import com.example.jiang.microblog.net.service.UserService;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public class UserModel implements UserContract.Model {

    private UserService userService;

    public UserModel(Context context) {
        this.userService = RetrofitHelper.getInstance(context).getUserServer();
    }

    @Override
    public Observable<User> getProfile(String access_token, String uid) {
        return userService.getProfile(access_token, uid);
    }

    @Override
    public Observable<User> getProfileByName(String access_token, String screen_name) {
        return userService.getProfileByName(access_token, screen_name);
    }

    @Override
    public Observable<Friend> getNextFriendList(String access_token, String uid, int next_cursor) {
        return userService.getNextFriendList(access_token, uid, next_cursor);
    }

    @Override
    public Observable<Friend> getPreFriendList(String access_token, String uid, int previous_cursor) {
        return userService.getPreFriendList(access_token, uid, previous_cursor);
    }
}
