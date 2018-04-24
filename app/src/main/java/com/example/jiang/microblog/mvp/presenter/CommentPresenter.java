package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.mvp.contract.CommentContract;
import com.example.jiang.microblog.mvp.model.CommentModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiang on 2018/4/22.
 */
public class CommentPresenter implements CommentContract.Presenter {

    private CompositeSubscription subscription;
    private CommentContract.Model model;
    private CommentContract.View view;

    private Comment comment;

    public CommentPresenter(Context context) {
        this.view = (CommentContract.View) context;
        this.model = new CommentModel(context);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getComments(String access_token, String id, int page) {
        subscription.add(
                model.getComments(access_token, id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Comment>() {
                            @Override
                            public void onCompleted() {
                                if (comment != null) {
                                    view.onSuccess(comment);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(Comment c) {
                                comment = c;
                            }
                        }
                )
        );

    }
}
