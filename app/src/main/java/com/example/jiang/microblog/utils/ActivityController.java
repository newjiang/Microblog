package com.example.jiang.microblog.utils;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jiang.microblog.bean.Setting;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class ActivityController {

    public static List<AppCompatActivity> activities = new ArrayList<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void add(AppCompatActivity activity) {
        activities.add(activity);
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(activity);
        List<Setting> settings = DataSupport.where("uid = ?", token.getUid()).find(Setting.class);
        Log.e("设置设置设置", settings.toString());
        if (settings.isEmpty()) {
            SkinTools.setStatusBarColor(activity, 0xff00aff);
        } else {
            String fuffix = settings.get(0).getSkinFuffix();
            if ("default".equals(fuffix)) {
                SkinTools.setStatusBarColor(activity, SkinTools.DEFAULT);
            } else {
                SkinTools.setStatusBarColor(activity, SkinTools.NIGHT);
            }
        }

    }

    /**
     * 移除Activity
     * @param activity
     */
    public static void remove(AppCompatActivity activity) {
        activities.remove(activity);
    }

    /**
     * 移除全部Activity
     */
    public static void finishAll(){
        for (AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}

