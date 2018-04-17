package com.example.jiang.microblog.mvp.model;

import android.content.Context;

import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.net.MicroblogService;
import com.example.jiang.microblog.net.RetrofitHelper;

import rx.Observable;

/**
 * Created by jiang on 2018/1/8.
 */
public class MicroblogModel implements MicroblogContract.Model {

    private MicroblogService microblogService;

    public MicroblogModel(Context context) {
        this.microblogService = RetrofitHelper.getInstance(context).getServer();
    }

    @Override
    public Observable<User> getProfile(String uid, String access_token) {
        return microblogService.getProfile(uid, access_token);
    }

    @Override
    public Observable<Microblog> getAllMicroblog(String access_token) {
        return microblogService.getAllMicroblog(access_token);
    }
}
