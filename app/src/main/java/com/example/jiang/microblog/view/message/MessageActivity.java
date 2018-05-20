package com.example.jiang.microblog.view.message;

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
import com.example.jiang.microblog.view.message.fragment.AtMeCommentsFragment;
import com.example.jiang.microblog.view.message.fragment.AtMeWeibosFragment;
import com.example.jiang.microblog.view.message.fragment.ToMeFragment;
import com.example.jiang.microblog.view.message.fragment.ByMeFragment;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {

    //TODO 导航栏标题
    private List<String> navList;
    //TODO 底部导航栏
    private TabLayout tabLayout;
    //TODO 页面切换viewPager控件
    private ViewPager viewPager;
    //TODO BaseFragment
    private List<BaseFragment> fragmentList;
    private ViewPagerAdapter adapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("我的消息");
        }
        index = getIntent().getIntExtra(IntentKey.MESSAGE_FRAGMENT_INDEX, 0);
        initTab(index);
    }

    private void initTab(int index) {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        navList = new ArrayList<>();
        navList.add("@我的微博");
        navList.add("@我的评论");
        navList.add("收到的评论");
        navList.add("发出的评论");
        //TODO 添加fragment
        fragmentList = new ArrayList<>();
        AtMeWeibosFragment awf = new AtMeWeibosFragment();
        AtMeCommentsFragment acf = new AtMeCommentsFragment();
        ToMeFragment tmf = new ToMeFragment();
        ByMeFragment bmf = new ByMeFragment();

        fragmentList.add(awf);
        fragmentList.add(acf);
        fragmentList.add(tmf);
        fragmentList.add(bmf);

        //TODO 初始化viewpager适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, navList);
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setSelectedTabIndicatorHeight(2);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
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

}
