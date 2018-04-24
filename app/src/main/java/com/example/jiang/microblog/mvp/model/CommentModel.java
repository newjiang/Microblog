package com.example.jiang.microblog.mvp.model;

import android.content.Context;
import android.util.Log;

import com.example.jiang.microblog.bean.Comment;
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

}
