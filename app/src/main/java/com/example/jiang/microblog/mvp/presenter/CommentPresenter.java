package com.example.jiang.microblog.mvp.presenter;

import android.content.Context;

import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.bean.Microblog;
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

    private Comment comments;
    private Microblog microblog;
    private CommentsBean commentsBean;

    public CommentPresenter(Context context) {
        this.view = (CommentContract.View) context;
        this.model = new CommentModel(context);
        subscription = new CompositeSubscription();
    }

    public CommentPresenter(BaseFragment fragment, Context context) {
        this.view = (CommentContract.View) fragment;
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
                                if (comments != null) {
                                    view.onSuccess(comments);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(Comment c) {
                                comments = c;
                            }
                        }
                )
        );

    }

    @Override
    public void getAtMeWeibo(String access_token,  int page) {
        subscription.add(
                model.getAtMeWeibo(access_token, page)
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
                            public void onNext(Microblog weibo) {
                                microblog = weibo;
                            }
                        })
        );
    }

    @Override
    public void getAtMeComment(String access_token, int page) {
        subscription.add(
                model.getAtMeComment(access_token, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<Comment>() {
                                    @Override
                                    public void onCompleted() {
                                        if (comments != null) {
                                            view.onSuccess(comments);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.onError(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Comment c) {
                                        comments = c;
                                    }
                                }
                        )
        );
    }

    @Override
    public void toMeComment(String access_token, int page) {
        subscription.add(
                model.toMeComment(access_token, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<Comment>() {
                                    @Override
                                    public void onCompleted() {
                                        if (comments != null) {
                                            view.onSuccess(comments);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.onError(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Comment c) {
                                        comments = c;
                                    }
                                }
                        )
        );
    }

    @Override
    public void byMeComment(String access_token, int page) {
        subscription.add(
                model.byMeComment(access_token, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<Comment>() {
                                    @Override
                                    public void onCompleted() {
                                        if (comments != null) {
                                            view.onSuccess(comments);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.onError(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Comment c) {
                                        comments = c;
                                    }
                                }
                        )
        );
    }

    @Override
    public void create(String access_token, final String comment, long id) {
        subscription.add(
                model.create(access_token, comment, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<Comment>() {
                                    @Override
                                    public void onCompleted() {
                                        if (CommentPresenter.this.comments != null) {
                                            view.onSuccess(CommentPresenter.this.comments);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.onError(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Comment c) {
                                        CommentPresenter.this.comments = c;
                                    }
                                }
                        )
        );
    }

    @Override
    public void reply(String access_token, long cid, long id, String comment) {
        subscription.add(
                model.reply(access_token, cid, id, comment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<Comment>() {
                                    @Override
                                    public void onCompleted() {
                                        if (comments != null) {
                                            view.onSuccess(comments);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.onError(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Comment c) {
                                        comments = c;
                                    }
                                }
                        )
        );
    }

    @Override
    public void destroy(String access_token, long cid) {
        subscription.add(
                model.destroy(access_token, cid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Observer<CommentsBean>() {
                                    @Override
                                    public void onCompleted() {
                                        if (commentsBean != null) {
                                            view.onSuccess(commentsBean);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.onSuccess(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(CommentsBean bean) {
                                        commentsBean = bean;
                                    }
                                }
                        )
        );
    }
}
