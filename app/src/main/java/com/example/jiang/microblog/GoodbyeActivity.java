package com.example.jiang.microblog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jiang.microblog.utils.ActivityController;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.view.activity.LoginActivity;

public class GoodbyeActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_goodbye);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityController.finishAll();
                //退出
                if (App.getToken().isSessionValid()) {
                    finish();
                } else {//切换账号
                   startActivity(new Intent(GoodbyeActivity.this, LoginActivity.class));
                }
            }
        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
