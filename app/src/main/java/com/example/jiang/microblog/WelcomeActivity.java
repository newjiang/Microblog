package com.example.jiang.microblog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.views.activity.LoginActivity;
import com.example.jiang.microblog.views.main.MainActivity;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import org.litepal.tablemanager.Connector;

public class WelcomeActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式启动欢迎界面
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_welcome);
        boolean isLogin = AccessTokenKeeper.readAccessToken(this).isSessionValid();
        if (isLogin) {
            intent = new Intent(WelcomeActivity.this, MainActivity.class);
        } else {
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        }
        //创建数据库
        Connector.getDatabase();
        //3s秒后启动主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 500);
    }
}
