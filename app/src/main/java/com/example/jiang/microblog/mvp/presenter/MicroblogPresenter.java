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

    private CompositeSubscription compositeSubscription;
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
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 初始化Fragment
     *
     * @param frag
     * @param context
     */
    public MicroblogPresenter(BaseFragment frag, Context context) {
        this.view = (MicroblogContract.View) frag;
        this.model = new MicroblogModel(context);
        compositeSubscription = new CompositeSubscription();
    }



    @Override
    public void getHomeMicroblog(String access_token) {
        compositeSubscription.add(
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
                                Log.e("onError,getMessage", e.getMessage());
                                view.onError(e.getMessage());
                            }
                            @Override
                            public void onNext(Microblog m) {
                                microblog = m;
                                view.onSuccess(microblog);
                            }
                        })
        );
    }
}
