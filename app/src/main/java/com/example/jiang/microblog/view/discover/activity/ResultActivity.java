package com.example.jiang.microblog.view.discover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.adapter.ViewPagerAdapter;
import com.example.jiang.microblog.view.discover.fragment.AccountFragment;
import com.example.jiang.microblog.view.discover.fragment.WeiboFragment;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity {

    // 导航栏标题
    private List<String> navList;
    // 底部导航栏
    private TabLayout tabLayout;
    // 页面切换viewPager控件
    private ViewPager viewPager;
    // BaseFragment
    private List<BaseFragment> fragmentList;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("搜索结果");
        }
        // 获取关键字
        Intent intent = getIntent();
        String key = intent.getStringExtra(IntentKey.SEARCH_CONTENT);
        initTab(key);
    }

    private void initTab(String key) {

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        navList = new ArrayList<>();
        navList.add("微博");
        navList.add("用户");
        // 添加fragment
        fragmentList = new ArrayList<>();
        AccountFragment af = new AccountFragment();
        WeiboFragment wf = new WeiboFragment();

        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.KEY_WORD, key);
        af.setArguments(bundle);
        wf.setArguments(bundle);

        fragmentList.add(wf);
        fragmentList.add(af);
        // 初始化viewpager适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, navList);
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setSelectedTabIndicatorHeight(2);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
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
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
