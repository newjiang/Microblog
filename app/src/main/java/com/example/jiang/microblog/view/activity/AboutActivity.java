package com.example.jiang.microblog.view.activity;

import android.os.Bundle;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.zhy.changeskin.SkinManager;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
