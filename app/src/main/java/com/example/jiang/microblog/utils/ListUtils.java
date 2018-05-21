package com.example.jiang.microblog.utils;

import com.example.jiang.microblog.bean.Statuses;

import java.util.List;

/**
 * Created by jiang on 2018/4/30.
 */

public class ListUtils {

    /**
     * 添加新的元素进入集合
     *
     * @param currentList 当前集合
     * @param newList     新元素的结合
     * @param isDown      是否向下拉动操作
     */
    public static void add(List<Statuses> currentList,
                           List<Statuses> newList, boolean isDown) {
        // 下拉添加数据的位置索引
        int index = 0;
        for (int i = 0; i < newList.size(); i++) {
            //TODO 比较次数
            int count = 0;
            for (int j = 0; j < currentList.size(); j++) {
                if (!(newList.get(i).getMid().equals(currentList.get(j).getMid()))) {
                    count++;
                    if (count == currentList.size()) {
                        if (isDown) {
                            // 从开始节点添加
                            currentList.add(index, newList.get(i));
                            index++;
                        } else {
                            // 从末尾添加
                            currentList.add(newList.get(i));
                        }
                        count = 0;
                    }
                }
            }
        }
    }
}
