package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.model.UserModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiang on 2018/4/22.
 */
public class UserPresenter implements UserContract.Presenter {

    private CompositeSubscription compositeSubscription;
    private UserContract.View view;
    private UserContract.Model model;

    private User user;

    /**
     * 初始化Activity
     *
     * @param context
     */
    public UserPresenter(Context context) {
        this.view = (UserContract.View) context;
        this.model = new UserModel(context);
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 初始化Fragment
     *
     * @param frag
     * @param context
     */
    public UserPresenter(BaseFragment frag, Context context) {
        this.view = (UserContract.View) frag;
        this.model = new UserModel(context);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getProfile(String uid, String access_token) {
        compositeSubscription.add(
                model.getProfile(uid, access_token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<User>() {
                            @Override
                            public void onCompleted() {
                                if (user != null) {
                                    view.onSuccess(user);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(User u) {
                                user = u;
                            }
                        })
        );
    }
}
