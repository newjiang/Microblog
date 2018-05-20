package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;

import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Message;
import com.example.jiang.microblog.mvp.contract.MessageContract;
import com.example.jiang.microblog.mvp.model.MessageModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiang on 2018/4/22.
 */
public class MessagePresenter implements MessageContract.Presenter {

    private CompositeSubscription subscription;
    private MessageContract.View view;
    private MessageContract.Model model;
    private Message message;


    /**
     * 初始化Activity
     *
     * @param context
     */
    public MessagePresenter(Context context) {
        this.view = (MessageContract.View) context;
        this.model = new MessageModel(context);
        subscription = new CompositeSubscription();
    }

    /**
     * 初始化Fragment
     *
     * @param fragment
     * @param context
     */
    public MessagePresenter(BaseFragment fragment, Context context) {
        this.view = (MessageContract.View) fragment;
        this.model = new MessageModel(context);
        subscription = new CompositeSubscription();
    }


    @Override
    public void unread_count(String access_token, String uid) {
        subscription.add(
                model.unread_count(access_token, uid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Message>() {
                            @Override
                            public void onCompleted() {
                                if (message != null) {
                                    view.onSuccess(message);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(Message m) {
                                message = m;
                            }
                        }));

    }
}
