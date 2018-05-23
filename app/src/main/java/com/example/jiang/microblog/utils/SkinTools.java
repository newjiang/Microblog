package com.example.jiang.microblog.utils;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by jiang on 2018/5/22.
 */

public class SkinTools {

    public static final int DEFAULT = 0xff00afff;
    public static final int NIGHT = 0xff383838;

    /**
     * 设置当前的activity的actionbar和tatusBar
     *
     * @param activity
     * @param statusColor
     */
    public static void setStatusBarColor(AppCompatActivity activity, int statusColor) {

        Window window = activity.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(statusColor));
        }
    }

    /**
     * 设置已经加载的activity的actionbar和tatusBar
     *
     * @param statusColor
     */
    public static void setMoreStatusBarColor(int statusColor) {

        List<AppCompatActivity> activities = ActivityController.activities;
        for (AppCompatActivity activity : activities) {
            Log.e("setMoreStatusBarColor", activity.toString());
            Window window = activity.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(statusColor);
            ActionBar bar = activity.getSupportActionBar();
            if (bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(statusColor));
            }
        }
    }
}
