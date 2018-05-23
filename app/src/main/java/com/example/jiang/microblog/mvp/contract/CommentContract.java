package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.bean.Microblog;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface CommentContract {

    interface Model {

        /**
         * 获取微博评论列表
         *
         * @param access_token
         * @param id
         * @param page
         * @return
         */
        Observable<Comment> getComments(String access_token, String id, int page);

        /**
         * 获取@用户的微博列表
         *
         * @param access_token
         * @param page
         * @return
         */
        Observable<Microblog> getAtMeWeibo(String access_token, int page);

        /**
         * 获取@用户的评论列表
         *
         * @param access_token
         * @param page
         * @return
         */
        Observable<Comment> getAtMeComment(String access_token, int page);

        /**
         * 用户收到的评论列表
         *
         * @param access_token
         * @param page
         * @return
         */
        Observable<Comment> toMeComment(String access_token, int page);

        /**
         * 用户发出的评论列表
         *
         * @param access_token
         * @param page
         * @return
         */
        Observable<Comment> byMeComment(String access_token, int page);

        /**
         * 对一条微博进行评论
         *
         * @param access_token
         * @param comment
         * @param id
         * @param comment_ori
         * @return
         */
        Observable<Comment> create(String access_token, String comment, long id, int comment_ori);

        /**
         * 回复一条评论
         *
         * @param access_token
         * @param cid
         * @param id
         * @param comment
         * @param without_mention
         * @param comment_ori
         * @return
         */
        Observable<Comment> reply(String access_token, long cid, long id,
                                  String comment, int without_mention, int comment_ori);

        /**
         * 删除一条评论
         *
         * @param access_token
         * @param cid
         * @return
         */
        Observable<CommentsBean> destroy(String access_token, long cid);

    }
    interface View {
        /**
         * 请求成功
         *
         * @param object
         */
        void onSuccess(Object object);

        /**
         * 请求错误
         *
         * @param result
         */
        void onError(String result);
    }

    interface Presenter {

        /**
         * 获取微博评论列表
         *
         * @param access_token
         * @param id
         * @param page
         * @return
         */
        void getComments(String access_token, String id, int page);

        /**
         * 获取@用户的微博列表
         *
         * @param access_token
         * @param page
         * @return
         */
        void getAtMeWeibo(String access_token, int page);

        /**
         * 获取@用户的评论列表
         *
         * @param access_token
         * @param page
         */
        void getAtMeComment(String access_token, int page);

        /**
         * 用户收到的评论列表
         *
         * @param access_token
         * @param page
         * @return
         */
        void toMeComment(String access_token, int page);

        /**
         * 用户发出的评论列表
         *
         * @param access_token
         * @param page
         * @return
         */
        void byMeComment(String access_token, int page);
        /**
         * 对一条微博进行评论
         *
         * @param access_token
         * @param comment
         * @param id
         * @param comment_ori
         * @return
         */
        void create(String access_token, String comment, long id, int comment_ori);

        /**
         * 回复一条评论
         *
         * @param access_token
         * @param cid
         * @param id
         * @param comment
         * @param without_mention
         * @param comment_ori
         * @return
         */
        void reply(String access_token, long cid, long id,
                   String comment, int without_mention, int comment_ori);

        /**
         * 删除一条评论
         *
         * @param access_token
         * @param cid
         * @return
         */
        void destroy(String access_token, long cid);
    }
}
