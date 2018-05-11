package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;

import com.example.jiang.microblog.bean.Favorites;
import com.example.jiang.microblog.mvp.contract.FavoriteContract;
import com.example.jiang.microblog.mvp.model.FavoriteModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiang on 2018/4/22.
 */
public class FavoritePresenter implements FavoriteContract.Presenter {

    private CompositeSubscription subscription;
    private FavoriteContract.View view;
    private FavoriteContract.Model model;

    private Favorites favorites;

    /**
     * 初始化Activity
     *
     * @param context
     */
    public FavoritePresenter(Context context) {
        this.view = (FavoriteContract.View) context;
        this.model = new FavoriteModel(context);
        subscription = new CompositeSubscription();
    }


    @Override
    public void getFavorites(String access_token, int page) {
        subscription.add(
                model.getFavorites(access_token,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Favorites>() {
                    @Override
                    public void onCompleted() {
                        if (favorites != null) {
                            view.onSuccess(favorites);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Favorites f) {
                        favorites = f;
                    }
                })
        );

    }

    @Override
    public void createFavorites(String access_token, long id) {

    }

    @Override
    public void destroyFavorites(String access_token, long id) {

    }
}
