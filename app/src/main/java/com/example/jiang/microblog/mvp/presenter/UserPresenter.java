package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Friend;
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

    private CompositeSubscription subscription;
    private UserContract.View view;
    private UserContract.Model model;

    private User user;
    private Friend friend;

    /**
     * 初始化Activity
     *
     * @param context
     */
    public UserPresenter(Context context) {
        this.view = (UserContract.View) context;
        this.model = new UserModel(context);
        subscription = new CompositeSubscription();
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
        subscription = new CompositeSubscription();
    }

    @Override
    public void getProfile(String access_token, String uid) {
        subscription.add(
                model.getProfile(access_token, uid)
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

    @Override
    public void getProfileByName(String access_token, String screen_name) {
        subscription.add(
                model.getProfileByName(access_token, screen_name)
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

    @Override
    public void getNextFriendList(String access_token, String uid, int next_cursor) {
        subscription.add(
                model.getNextFriendList(access_token, uid, next_cursor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Friend>() {
                    @Override
                    public void onCompleted() {
                        if (friend != null) {
                            view.onSuccess(friend);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getMessage());
                    }

                    @Override
                    public void onNext(Friend f) {
                        friend = f;
                    }
                })
        );
    }

    @Override
    public void getPreFriendList(String access_token, String uid, int previous_cursor) {
        subscription.add(
                model.getPreFriendList(access_token, uid, previous_cursor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Friend>() {
                            @Override
                            public void onCompleted() {
                                if (friend != null) {
                                    view.onSuccess(friend);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("onError", e.getMessage());
                            }

                            @Override
                            public void onNext(Friend f) {
                                friend = f;
                            }
                        })
        );
    }
}
