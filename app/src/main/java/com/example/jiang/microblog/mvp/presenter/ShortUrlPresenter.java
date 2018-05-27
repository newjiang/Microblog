package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;

import com.example.jiang.microblog.bean.ShortUrl;
import com.example.jiang.microblog.mvp.contract.ShortUrlContract;
import com.example.jiang.microblog.mvp.model.ShortUrlModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiang on 2018/4/22.
 */
public class ShortUrlPresenter implements ShortUrlContract.Presenter {

    private CompositeSubscription subscription;
    private ShortUrlContract.Model model;
    private ShortUrlContract.View view;

    private ShortUrl shortUrl;

    public ShortUrlPresenter(Context context) {
        this.view = (ShortUrlContract.View) context;
        this.model = new ShortUrlModel(context);
        subscription = new CompositeSubscription();
    }

    @Override
    public void expand(String access_token, String url_short) {
        subscription.add(
                model.expand(access_token, url_short)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ShortUrl>() {
                            @Override
                            public void onCompleted() {
                                if (shortUrl != null) {
                                    view.onSuccess(shortUrl);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ShortUrl url) {
                                shortUrl = url;
                            }
                        })
        );
    }
}
