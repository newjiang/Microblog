package com.example.jiang.microblog.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.GoodbyeActivity;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.json.UserJson;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.presenter.UserPresenter;
import com.example.jiang.microblog.test.NotificationActivity;
import com.example.jiang.microblog.view.discover.DiscoverFragment;
import com.example.jiang.microblog.view.home.HomeFragment;
import com.example.jiang.microblog.view.main.adapter.MainViewPagerAdapter;
import com.example.jiang.microblog.view.message.MessageFragment;
import com.example.jiang.microblog.view.share.ShareActivity;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements UserContract.View, NavigationView.OnNavigationItemSelectedListener {

    private UserContract.Presenter presenter;

    //TODO　发布微博按钮
    private CircleImageView composeMicroblog;
    //TODO　信息导航栏
    private NavigationView navigationView;
    //TODO　旋转效果
    private ActionBarDrawerToggle toggle;
    //TODO　底部导航栏
    private TabLayout tabLayout;
    //TODO　页面切换viewPager控件
    private ViewPager viewPager;
    //TODO　抽屉侧滑栏
    private DrawerLayout drawer;
    //TODO　Toolbar
    private Toolbar toolbar;
    //TODO　下拉框
    private Spinner spinner;
    //TODO　导航界面头部
    private View headerView;
    //TODO　用户头像
    private CircleImageView header;
    //TODO　用户呢称
    private TextView usernaem;
    //TODO　用户描述
    private TextView description;

    private List<BaseFragment> fragmentList;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initTabs();
        initEvents();

        //TODO 请求获取用户信息
        presenter = new UserPresenter(this);

//        if (user == null) {
//            presenter.getProfile(App.getToken().getUid(), App.getToken().getToken());
//        }
        Gson gson = new Gson();
        user = gson.fromJson(UserJson.JSON, User.class);
        //TODO　设置用户头像
        Glide.with(MainActivity.this).load(user.getAvatar_hd()).into(header);
        //TODO　设置用户昵称
        usernaem.setText(user.getName());
        //TODO　设置用户描述
        description.setText(user.getDescription());
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        composeMicroblog = (CircleImageView) findViewById(R.id.home_compose_microblog);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        spinner = (Spinner) findViewById(R.id.home_spinner);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitle("");
        headerView =  navigationView.getHeaderView(0);
        header = (CircleImageView) headerView.findViewById(R.id.home_account_icon);
        usernaem = (TextView) headerView.findViewById(R.id.home_account_name);
        description = (TextView) headerView.findViewById(R.id.home_account_introduction);

        //TODO　添加fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new DiscoverFragment());
        //TODO　初始化viewpager适配器
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(mainViewPagerAdapter);
        //TODO　设置隐藏和显示之和的fragment总数数
        viewPager.setOffscreenPageLimit(3);
    }

    /**
     * 初始化导航栏
     */
    private void initTabs() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setSelectedTabIndicatorHeight(0);
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
     * 初始化事件
     */
    private void initEvents() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //TODO　下拉框事件
        spinnerEvent();
        //TODO　viewPager滑动监听事件
        viewPagerEvent();

        //TODO　点击发布微博事件
        composeMicroblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 下拉框事件
     */
    private void spinnerEvent() {
        String[] mItems = getResources().getStringArray(R.array.title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_style, mItems);
        adapter.setDropDownViewResource(R.layout.spinner_dialog_style);
        //TODO　绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.title);
                //TODO 下拉框

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * viewPager滑动监听事件
     */
    private void viewPagerEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    toolbar.setTitle("");
                    spinner.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    spinner.setVisibility(View.GONE);
                    toolbar.setTitle("消息");
                } else if (position == 2){
                    spinner.setVisibility(View.GONE);
                    toolbar.setTitle("热门");
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_all) {
            //TODO　全部
            Toast.makeText(this, "全部", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            //TODO　相册
            Toast.makeText(this, "相册", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_favorite) {
            //TODO　收藏
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_original) {
            //TODO　原创
            Toast.makeText(this, "原创", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            //TODO　关于
            Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_skin) {
            //TODO　皮肤
            Toast.makeText(this, "皮肤", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_setting) {
            //TODO　设置
            Toast.makeText(this, "虽然是设置，现在用于测试通知", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        } else if (id == R.id.nav_switch_account) {
            //TODO　切换账号
            AccessTokenKeeper.clear(MainActivity.this);
            startActivity(new Intent(MainActivity.this, GoodbyeActivity.class));
        } else if (id == R.id.nav_quit) {
            //TODO　退出
            startActivity(new Intent(MainActivity.this, GoodbyeActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSuccess(Object object) {
//        user = (User) object;
//        //TODO　设置用户头像
//        Glide.with(MainActivity.this).load(user.getAvatar_hd()).into(header);
//        //TODO　设置用户昵称
//        usernaem.setText(user.getName());
//        //TODO　设置用户描述
//        description.setText(user.getDescription());
    }
    @Override
    public void onError(String result) {
        Log.e("MainActivity-onError", result);
    }
}
