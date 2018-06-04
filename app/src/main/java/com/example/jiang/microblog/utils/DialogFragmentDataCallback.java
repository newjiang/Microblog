package com.example.jiang.microblog.utils;

/**
 * Created by jiang on 2018/4/16.
 */

/**
 * 用于数据回调
 */
public interface DialogFragmentDataCallback {

    /**
     * 获取评论内容
     *
     * @return
     */
    String getCommentText();

    /**
     * 发送评论内容
     *
     * @param comment
     */
    void sendComment(String comment);
}
