package com.example.jiang.microblog.view.comment.fragment;

/**
 * Created by jiang on 2018/4/16.
 */

/**
 * 用于数据回调
 */
public interface DialogFragmentDataCallback {

    String getCommentText();

    void sendComment(String comment);
}
