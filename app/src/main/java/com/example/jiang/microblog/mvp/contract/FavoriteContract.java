package com.example.jiang.microblog.mvp.contract;

import com.example.jiang.microblog.bean.Favorites;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public interface FavoriteContract {

    interface Model {
        /**
         * 获取收藏列表
         * @param access_token
         * @param page
         * @return
         */
        public Observable<Favorites> getFavorites(String access_token, int page);

        /**
         * 添加一条收藏
         * @param access_token
         * @param id
         * @return
         */
        public Observable<Favorites> createFavorites(String access_token, long id);

        /**
         * 删除一条
         * @param access_token
         * @param id
         * @return
         */
        public Observable<Favorites> destroyFavorites(String access_token, long id);

    }

    interface View {
        /**
         * 请求成功
         * @param object
         */
        void onSuccess(Object object);

        /**
         * 请求错误
         * @param result
         */
        void onError(String result);
    }

    interface Presenter {
        /**
         * 获取收藏列表
         * @param access_token
         * @param page
         */
        void getFavorites(String access_token, int page);

        /**
         * 添加一条收藏
         * @param access_token
         * @param id
         */
        void createFavorites(String access_token, long id);

        /**
         * 删除一条
         * @param access_token
         * @param id
         */
        void destroyFavorites(String access_token, long id);
    }
}
