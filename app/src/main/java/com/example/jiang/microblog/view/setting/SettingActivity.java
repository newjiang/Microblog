package com.example.jiang.microblog.view.setting;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
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
import com.zhy.changeskin.SkinManager;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView notification;

    private LinearLayout linearLayout;
    private ImageView ring;
    private TextView ringName;
    private ImageView vibrate;
    private ImageView shareWays;
    private Oauth2AccessToken token;
    private Setting setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("应用设置");
        }
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
        shareWays = (ImageView) findViewById(R.id.share_ways);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        notification.setOnClickListener(this);
        ring.setOnClickListener(this);
        ringName.setOnClickListener(this);
        vibrate.setOnClickListener(this);
        shareWays.setOnClickListener(this);
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
            ringName.setText(getRingName(setting.getRing()));
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
        if (setting.isShareByDefault()) {
            shareWays.setBackgroundResource(R.drawable.on);
        } else {
            shareWays.setBackgroundResource(R.drawable.off);
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
            case R.id.share_ways:
                shareWaysOnClick();
                break;
        }
    }

    private void shareWaysOnClick() {
        if (setting.isShareByDefault()) {
            shareWays.setBackgroundResource(R.drawable.off);
        } else {
            shareWays.setBackgroundResource(R.drawable.on);
        }
        setting.setShareByDefault(!setting.isShareByDefault());
        ContentValues values = new ContentValues();
        values.put("isShareByDefault", setting.isShareByDefault());
        DataSupport.update(Setting.class, values, setting.getId());
    }

    private void ringOnClick() {
        if (setting.isRing()) {
            ring.setBackgroundResource(R.drawable.off);
        } else {
            ring.setBackgroundResource(R.drawable.on);
        }
        setting.setRing(!setting.isRing());
        ContentValues values = new ContentValues();
        values.put("isRing", setting.isRing());
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
            if (resultCode == RESULT_OK) {
                String extra = data.getStringExtra(IntentKey.RETURN_RING);
                setting.setRing(extra);
                ringName.setText(getRingName(extra));
                ContentValues values = new ContentValues();
                values.put("ring", extra);
                DataSupport.update(Setting.class, values, setting.getId());
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getRingName(String ring) {
        if (ring.contains("ogg")) {
            int start = ring.lastIndexOf(File.separator) + 1;
            int end = ring.indexOf(".");
            return ring.substring(start, end);
        }
        return ring;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
