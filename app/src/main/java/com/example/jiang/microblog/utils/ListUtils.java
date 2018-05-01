package com.example.jiang.microblog.utils;

import com.example.jiang.microblog.bean.Microblog;

import java.util.List;

/**
 * Created by jiang on 2018/4/30.
 */

public class ListUtils {
    public static void add(List<Microblog.StatusesBean> currentList,
                           List<Microblog.StatusesBean> newList, boolean isDown) {
        //TODO 下拉添加数据的位置索引
        int index = 0;
        for (int i = 0; i < newList.size(); i++) {
            //TODO 比较次数
            int count = 0;
            for (int j = 0; j < currentList.size(); j++) {
                if (!(newList.get(i).getMid().equals(currentList.get(j).getMid()))) {
                    count++;
                    if (count == currentList.size()) {
                        if (isDown) {
                            //TODO 从开始节点添加
                            currentList.add(index, newList.get(i));
                            index++;
                        } else {
                            //TODO 从末尾添加
                            currentList.add(newList.get(i));
                        }
                        count = 0;
                    }
                }
            }
        }
    }
}
