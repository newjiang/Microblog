package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.Microblog;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface CommentContract {

    interface Model {

        //TODO 获取微博评论列表
        public Observable<Comment> getComments(String access_token, String id, int page);

        //TODO 获取@用户的微博列表
        public Observable<Microblog> getAtMeWeibo(String access_token, int page);

        //TODO 获取@用户的评论列表
        public Observable<Comment> getAtMeComment(String access_token, int page);

        //TODO 用户收到的评论列表
        public Observable<Comment> toMeComment(String access_token, int page);

        //TODO 用户发出的评论列表
        public Observable<Comment> byMeComment(String access_token, int page);
    }
    interface View {
        //TODO 成功
        void onSuccess(Object object);

        //TODO 错误
        void onError(String result);
    }

    interface Presenter {

        //TODO 获取微博评论列表
        void getComments(String access_token, String id, int page);

        //TODO 获取@用户的微博列表
        void getAtMeWeibo(String access_token, int page);

        //TODO 获取@用户的评论列表
        void getAtMeComment(String access_token, int page);

        //TODO 用户收到的评论列表
        void toMeComment(String access_token, int page);

        //TODO 用户发出的评论列表
        void byMeComment(String access_token, int page);
    }
}
