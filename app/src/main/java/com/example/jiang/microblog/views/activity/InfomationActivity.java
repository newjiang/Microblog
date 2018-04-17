package com.example.jiang.microblog.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.base.IntentKey;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.views.activity.adapter.ViewPagerAdapter;
import com.example.jiang.microblog.views.profile.ProfileFragment;
import com.example.jiang.microblog.widget.GlideRoundTransform;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InfomationActivity extends BaseActivity implements MicroblogContract.View {

    private MicroblogContract.Presenter presenter;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout coll;
    private ImageView background;

    //底部导航栏
    private TabLayout tabLayout;
    //页面切换viewPager控件
    private ViewPager viewPager;

    private List<BaseFragment> fragmentList;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        initViews();
        initEvents();
        getRequestInfomation();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //添加fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(new ProfileFragment());
        fragmentList.add(new ProfileFragment());
        fragmentList.add(new ProfileFragment());
        //初始化viewpager适配器
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        //设置隐藏和显示之和的fragment总数数
        viewPager.setOffscreenPageLimit(3);

        //设置固定的
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置下划线宽度
        tabLayout.setSelectedTabIndicatorHeight(1);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < fragmentList.size(); i++) {
            TabLayout.Tab itemTab = tabLayout.getTabAt(i);
            if (itemTab != null && i == 0) {
                itemTab.setCustomView(R.layout.tabber_home_icon);
            } else if (itemTab != null && i == 1) {
                itemTab.setCustomView(R.layout.tabber_message_icon);
            } else {
                itemTab.setCustomView(R.layout.tabber_discover_icon);
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);

    }
    /**
     * 请求得到用户信息
     */
    private void getRequestInfomation() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(IntentKey.ACCOUNT_ID, 0);
//        presenter = new MicroblogPresenter(this);
//        presenter.getProfile(String.valueOf(id), App.getToken().getToken());

        coll.setTitle("new新健");
        Glide.with(this).load(R.mipmap.ic_launcher).transform(new GlideRoundTransform(this, 50)).into(fab);
        Glide.with(this).load("http://ww1.sinaimg.cn/crop.0.0.640.640.640/9d44112bjw1f1xl1c10tuj20hs0hs0tw.jpg").into(background);
    }
    /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coll = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        coll.setTitle(" ");
        background = (ImageView) findViewById(R.id.background);
    }

    /**
     * 初始化事件
     */
    private void initEvents() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onSuccess(Object object) {
        User user = (User) object;
        String s = new Gson().toJson(user);
        Log.e("onSuccess", s);
        coll.setTitle(user.getName());
//        Glide.with(this).load(user.getAvatar_large()).transform(new GlideRoundTransform(this, 50)).into(fab);
//        Glide.with(this).load(user.getCover_image_phone()).into(background);
        coll.setTitle(user.getName());
        Glide.with(this).load(R.mipmap.ic_launcher).transform(new GlideRoundTransform(this, 50)).into(fab);
        Glide.with(this).load(R.drawable.icon_compose).into(background);
    }

    @Override
    public void onError(String result) {
        Log.e("onError", result);
    }
}
