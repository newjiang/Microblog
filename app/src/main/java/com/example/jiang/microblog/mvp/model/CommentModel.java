package com.example.jiang.microblog.mvp.model;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.mvp.contract.CommentContract;
import com.example.jiang.microblog.net.RetrofitHelper;
import com.example.jiang.microblog.net.service.CommentService;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public class CommentModel implements CommentContract.Model {

    private CommentService commentService;

    public CommentModel(Context context) {
        this.commentService = RetrofitHelper.getInstance(context).getCommentService();
    }

    @Override
    public Observable<Comment> getComments(String access_token, String id, int page) {
        return commentService.getComments(access_token, id, page);
    }

    @Override
    public Observable<Microblog> getAtMeWeibo(String access_token, int page) {
        return commentService.getAtMeWeibo(access_token, page);
    }

    @Override
    public Observable<Comment> getAtMeComment(String access_token, int page) {
        return commentService.getAtMeComment(access_token, page);
    }

    @Override
    public Observable<Comment> toMeComment(String access_token, int page) {
        return commentService.toMeComment(access_token, page);
    }

    @Override
    public Observable<Comment> byMeComment(String access_token, int page) {
        Log.e("byMeCommentModel", access_token);
        Log.e("byMeCommentModel", page + "");
        return commentService.byMeComment(access_token, page);
    }
}
