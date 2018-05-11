package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class CommentManageInfoBean {

    private int comment_manage_button;
    private int comment_permission_type;
    private int approval_comment_type;

    public int getComment_manage_button() {
        return comment_manage_button;
    }

    public void setComment_manage_button(int comment_manage_button) {
        this.comment_manage_button = comment_manage_button;
    }

    public int getComment_permission_type() {
        return comment_permission_type;
    }

    public void setComment_permission_type(int comment_permission_type) {
        this.comment_permission_type = comment_permission_type;
    }

    public int getApproval_comment_type() {
        return approval_comment_type;
    }

    public void setApproval_comment_type(int approval_comment_type) {
        this.approval_comment_type = approval_comment_type;
    }

    @Override
    public String toString() {
        return "CommentManageInfoBean{" +
                "comment_manage_button=" + comment_manage_button +
                ", comment_permission_type=" + comment_permission_type +
                ", approval_comment_type=" + approval_comment_type +
                '}';
    }
}