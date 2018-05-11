package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Favorites;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface FavoriteContract {

    interface Model {
        //TODO 获取收藏列表
        public Observable<Favorites> getFavorites(String access_token, int page);
        //TODO 添加一条收藏
        public Observable<Favorites> createFavorites(String access_token, long id);
        //TODO 删除一条
        public Observable<Favorites> destroyFavorites(String access_token, long id);

    }

    interface View {
        //TODO 成功
        void onSuccess(Object object);
        //TODO 错误
        void onError(String result);
    }

    interface Presenter {
        //TODO 获取收藏列表
        void getFavorites(String access_token, int page);
        //TODO 添加一条收藏
        void createFavorites(String access_token, long id);
        //TODO 删除一条
        void destroyFavorites(String access_token, long id);
    }
}
