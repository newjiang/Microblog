package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.model.MicroblogModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiang on 2018/1/8.
 */
public class MicroblogPresenter implements MicroblogContract.Presenter {

    private CompositeSubscription subscription;
    private MicroblogContract.View view;
    private MicroblogContract.Model model;

    private Microblog microblog;

    /**
     * 初始化Activity
     *
     * @param context
     */
    public MicroblogPresenter(Context context) {
        this.view = (MicroblogContract.View) context;
        this.model = new MicroblogModel(context);
        subscription = new CompositeSubscription();
    }

    /**
     * 初始化Fragment
     *
     * @param fragment
     * @param context
     */
    public MicroblogPresenter(BaseFragment fragment, Context context) {
        this.view = (MicroblogContract.View) fragment;
        this.model = new MicroblogModel(context);
        subscription = new CompositeSubscription();
    }



    @Override
    public void getHomeMicroblog(String access_token) {
        subscription.add(
                model.getHomeMicroblog(access_token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Microblog>() {
                            @Override
                            public void onCompleted() {
                                if (microblog != null) {
                                    view.onSuccess(microblog);
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                view.onError(e.getMessage());
                            }
                            @Override
                            public void onNext(Microblog m) {
                                microblog = m;
                            }
                        })
        );
    }
}
