package com.example.jiang.microblog.utils;

import com.example.jiang.microblog.bean.Statuses;

/**
 * Created by jiang on 2018/5/24.
 */

/**
 * 收藏事件监听器
 */

public interface OnFavouritesListener {
    /**
     * 收藏或取消收藏
     *
     * @param statuses
     * @param isFavouritd
     */
    void onFavouritesListener(Statuses statuses, boolean isFavouritd);
}
