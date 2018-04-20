package com.example.jiang.microblog.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class ActivityController {

    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void add(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public static void remove(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 移除全部Activity
     */
    public static void finishAll(){
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}

