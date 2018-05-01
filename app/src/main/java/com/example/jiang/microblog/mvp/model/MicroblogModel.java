package com.example.jiang.microblog.mvp.model;

import android.content.Context;

import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.net.service.MicroblogService;
import com.example.jiang.microblog.net.RetrofitHelper;

import rx.Observable;

/**
 * Created by jiang on 2018/1/8.
 */
public class MicroblogModel implements MicroblogContract.Model {

    private MicroblogService microblogService;

    public MicroblogModel(Context context) {
        this.microblogService = RetrofitHelper.getInstance(context).getMicroblogServer();
    }

    @Override
    public Observable<Microblog> getHomeMicroblog(String access_token, int page) {
        return microblogService.getHomeMicroblog(access_token, page);
    }
}
