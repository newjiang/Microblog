package com.example.jiang.microblog.base;

import android.app.Application;
import android.content.Context;

import com.zhy.changeskin.SkinManager;

import org.litepal.LitePalApplication;

/**
 * Created by jiang on 2018/4/14.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        LitePalApplication.initialize(context);
        //TODO 通过全局Context初始化SkinManager
        SkinManager.getInstance().init(this);
    }

    /**
     * 获取全局context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

}
