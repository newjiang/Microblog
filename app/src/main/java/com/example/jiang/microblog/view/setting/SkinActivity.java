package com.example.jiang.microblog.view.setting;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.Setting;
import com.example.jiang.microblog.utils.SkinTools;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.zhy.changeskin.SkinManager;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SkinActivity extends BaseActivity implements View.OnClickListener {

    private Oauth2AccessToken token;
    private Button defaultSkin;
    private Button nightSkin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("点击确认皮肤");
        }
        token = AccessTokenKeeper.readAccessToken(this);
        defaultSkin = (Button) findViewById(R.id.default_skin);
        nightSkin = (Button) findViewById(R.id.night_skin);
        defaultSkin.setOnClickListener(this);
        nightSkin.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.default_skin:
                setSkinPlugin("default");
                break;
            case R.id.night_skin:
                setSkinPlugin("night");
                break;
            default:
                break;
        }
    }

    private void setSkinPlugin(String skinName) {
        SkinManager.getInstance().changeSkin(skinName);
        List<Setting> settings = DataSupport.where("uid = ?", token.getUid()).find(Setting.class);
        if (settings.isEmpty()) {
            Setting setting = new Setting(true, true, true, true, "default", "default", token.getUid());
            setting.setSkinFuffix(skinName);
            setting.save();
            if ("default".equals(skinName))
                SkinTools.setMoreStatusBarColor(SkinTools.DEFAULT);
             else
                SkinTools.setMoreStatusBarColor(SkinTools.NIGHT);
        } else {
            Setting setting = settings.get(0);
            setting.setSkinFuffix(skinName);
            if ("default".equals(skinName))
                SkinTools.setMoreStatusBarColor(SkinTools.DEFAULT);
            else
                SkinTools.setMoreStatusBarColor(SkinTools.NIGHT);
            ContentValues values = new ContentValues();
            values.put("skinFuffix", setting.getSkinFuffix());
            DataSupport.update(Setting.class, values, setting.getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
