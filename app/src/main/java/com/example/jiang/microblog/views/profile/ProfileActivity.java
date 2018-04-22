package com.example.jiang.microblog.views.profile;

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
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.views.profile.adapter.InfoViewPagerAdapter;
import com.example.jiang.microblog.views.profile.fragment.AlbumFragment;
import com.example.jiang.microblog.views.profile.fragment.MicroblogFragment;
import com.example.jiang.microblog.views.profile.fragment.ProfileFragment;
import com.example.jiang.microblog.widget.GlideRoundTransform;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements MicroblogContract.View {

    private MicroblogContract.Presenter presenter;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout coll;
    private ImageView background;
    //TODO 导航栏标题
    private List<String> navList;
    //TODO 底部导航栏
    private TabLayout tabLayout;
    //TODO 页面切换viewPager控件
    private ViewPager viewPager;
    //TODO BaseFragment
    private List<BaseFragment> fragmentList;
    //TODO PagerAdapter
    private InfoViewPagerAdapter viewPagerAdapter;

    private Microblog.StatusesBean.UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getUserInfo();
        initViews();
        initEvents();
        initTab();
    }

    private void getUserInfo() {
        Intent intent = getIntent();
        String json = intent.getStringExtra(IntentKey.USER_INFORMATION);
        userBean = new Gson().fromJson(json, Microblog.StatusesBean.UserBean.class);
    }


    private void initTab() {
        navList = new ArrayList<>();
        navList.add("关于");
        navList.add("微博(" + userBean.getStatuses_count() + ")");
        navList.add("相册");
        //TODO 添加fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(new ProfileFragment());
        fragmentList.add(new MicroblogFragment());
        fragmentList.add(new AlbumFragment());
        //TODO 初始化viewpager适配器
        viewPagerAdapter = new InfoViewPagerAdapter(getSupportFragmentManager(), fragmentList,navList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE );
        tabLayout.setSelectedTabIndicatorHeight(2);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
    }

     /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coll = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        background = (ImageView) findViewById(R.id.background);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        coll.setTitle(userBean.getName());
        Glide.with(this).load(userBean.getAvatar_large()).transform(new GlideRoundTransform(this, 50)).into(fab);
        Glide.with(this).load(userBean.getCover_image_phone()).into(background);
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
        //TODO Glide.with(this).load(user.getAvatar_large()).transform(new GlideRoundTransform(this, 50)).into(fab);
        //TODO Glide.with(this).load(user.getCover_image_phone()).into(background);
        coll.setTitle(user.getName());


        Glide.with(this).load(R.mipmap.ic_launcher).transform(new GlideRoundTransform(this, 50)).into(fab);
        Glide.with(this).load(R.drawable.icon_share).into(background);
    }

    @Override
    public void onError(String result) {
        Log.e("onError", result);
    }
}
