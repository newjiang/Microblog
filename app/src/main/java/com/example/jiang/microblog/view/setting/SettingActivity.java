package com.example.jiang.microblog.view.setting;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.Setting;
import com.example.jiang.microblog.utils.IntentKey;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView notification;

    private LinearLayout linearLayout;
    private ImageView ring;
    private TextView ringName;
    private ImageView vibrate;
    private Oauth2AccessToken token;
    private Setting setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        token = AccessTokenKeeper.readAccessToken(this);
        Log.e("getUid", token.getUid());
        List<Setting> settings = DataSupport.where("uid = ?", token.getUid()).find(Setting.class);
        if (settings.isEmpty()) {
                setting = new Setting(true, true, true, true, "default", "default", token.getUid());
                setting.save();
        } else {
            setting = settings.get(0);
        }
        initView();
    }

    private void initView() {
        notification = (ImageView) findViewById(R.id.notification);
        ring = (ImageView) findViewById(R.id.ring);
        ringName = (TextView) findViewById(R.id.ring_name);
        vibrate = (ImageView) findViewById(R.id.vibrate);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        notification.setOnClickListener(this);
        ring.setOnClickListener(this);
        ringName.setOnClickListener(this);
        vibrate.setOnClickListener(this);
        if (setting.isNotification()) {
            notification.setBackgroundResource(R.drawable.on);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            notification.setBackgroundResource(R.drawable.off);
            linearLayout.setVisibility(View.GONE);
        }
        if (setting.getRing().equals("default")) {
            ringName.setText("系统默认");
        } else {
            ringName.setText(setting.getRing());
        }
        if (setting.isRing()) {
            ring.setBackgroundResource(R.drawable.on);
        } else {
            ring.setBackgroundResource(R.drawable.off);
        }
        if (setting.isVibrate()) {
            vibrate.setBackgroundResource(R.drawable.on);
        } else {
            vibrate.setBackgroundResource(R.drawable.off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification:
                notificationOnClick();
                break;
            case R.id.ring:
                ringOnClick();
                break;
            case R.id.vibrate:
                vibrateOnClick();
                break;
            case R.id.ring_name:
                Intent intent = new Intent(SettingActivity.this, RingActivity.class);
                intent.putExtra(IntentKey.RING, setting.getRing());
                startActivityForResult(intent, 1);
                break;
        }
    }

    private void ringOnClick() {
        if (setting.isRing()) {
            ring.setBackgroundResource(R.drawable.off);
        } else {
            ring.setBackgroundResource(R.drawable.on);
        }
        setting.setRing(!setting.isRing());
        ContentValues values = new ContentValues();
        values.put("isRing", setting.isNotification());
        DataSupport.update(Setting.class, values, setting.getId());
    }
    private void vibrateOnClick() {
        if (setting.isVibrate()) {
            vibrate.setBackgroundResource(R.drawable.off);
        } else {
            vibrate.setBackgroundResource(R.drawable.on);
        }
        setting.setVibrate(!setting.isVibrate());
        ContentValues values = new ContentValues();
        values.put("isVibrate", setting.isVibrate());
        DataSupport.update(Setting.class, values, setting.getId());
    }

    private void notificationOnClick() {
        if (setting.isNotification()) {
            notification.setBackgroundResource(R.drawable.off);
            linearLayout.setVisibility(View.GONE);
        } else {
            notification.setBackgroundResource(R.drawable.on);
            linearLayout.setVisibility(View.VISIBLE);
        }
        setting.setNotification(!setting.isNotification());
        ContentValues values = new ContentValues();
        values.put("isNotification", setting.isNotification());
        DataSupport.update(Setting.class, values, setting.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

        }

    }
}
