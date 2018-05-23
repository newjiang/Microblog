package com.example.jiang.microblog;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jiang.microblog.bean.Setting;
import com.example.jiang.microblog.utils.SkinTools;
import com.example.jiang.microblog.view.activity.LoginActivity;
import com.example.jiang.microblog.view.setting.SettingActivity;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.zhy.changeskin.SkinManager;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().changeSkin("");
        super.onCreate(savedInstanceState);
        //TODO 沉浸式启动欢迎界面
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
        //TODO 判断是否登陆过
        boolean isLogin = AccessTokenKeeper.readAccessToken(this).isSessionValid();
        if (isLogin) {
            //TODO　进入主页
            setSkinPlugin("default");
          intent = new Intent(WelcomeActivity.this, SettingActivity.class);
        } else {
            //TODO　进入登陆页面
            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        }
        //TODO 创建数据库
        Connector.getDatabase();
        //1s秒后启动主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void setSkinPlugin(String skinName) {
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
        SkinManager.getInstance().changeSkin(skinName);
        List<Setting> settings = DataSupport.where("uid = ?", token.getUid()).find(Setting.class);
        if (settings.isEmpty()) {
            Setting setting = new Setting(true, true, true, true, "default", "default", token.getUid());
            setting.setSkinFuffix(skinName);
            setting.save();
            SkinTools.setMoreStatusBarColor(SkinTools.DEFAULT);
        } else {
            Setting setting = settings.get(0);
            setting.setSkinFuffix(skinName);
            SkinTools.setMoreStatusBarColor(SkinTools.DEFAULT);
            ContentValues values = new ContentValues();
            values.put("skinFuffix", setting.getSkinFuffix());
            DataSupport.update(Setting.class, values, setting.getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
