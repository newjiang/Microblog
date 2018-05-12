package com.example.jiang.microblog.view.main;

import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.GoodbyeActivity;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.base.Constants;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.broadcast.MessageReceiver;
import com.example.jiang.microblog.json.UserJson;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.presenter.UserPresenter;
import com.example.jiang.microblog.service.PollingService;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.adapter.MainViewPagerAdapter;
import com.example.jiang.microblog.view.discover.DiscoverFragment;
import com.example.jiang.microblog.view.favourites.FavoriteActivity;
import com.example.jiang.microblog.view.home.HomeFragment;
import com.example.jiang.microblog.view.message.MessageFragment;
import com.example.jiang.microblog.view.profile.ProfileActivity;
import com.example.jiang.microblog.view.search.SearchActivity;
import com.example.jiang.microblog.view.setting.SettingActivity;
import com.example.jiang.microblog.view.setting.SkinActivity;
import com.example.jiang.microblog.view.share.ShareActivity;
import com.google.gson.Gson;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements UserContract.View,
        NavigationView.OnNavigationItemSelectedListener, WbShareCallback, View.OnClickListener,
        MessageReceiver.OnMessageListener {

    public static final int HOME_TIMELINE = 0;//用户及好友微博
    public static final int BILATERAL_TIMELINE = 1;//互相关注的微博
    public static final int PUBLIC_TIMELINE = 2;//公共微博

    private UserContract.Presenter presenter;
    private CircleImageView composeMicroblog; //TODO　发布微博按钮
    private ImageView homeSearch;              //TODO 搜索按钮
    private NavigationView navigationView;    //TODO　信息导航栏
    private ActionBarDrawerToggle toggle;      //TODO　旋转效果
    private TabLayout tabLayout;               //TODO　底部导航栏
    private ViewPager viewPager;               //TODO　页面切换viewPager控件
    private DrawerLayout drawer;               //TODO　抽屉侧滑栏
    private Toolbar toolbar;                   //TODO　Toolbar
    private Spinner spinner;                   //TODO　下拉框
    private View headerLayout;                //TODO　导航界面头部
    private CircleImageView header;            //TODO　用户头像
    private TextView usernaem;                 //TODO　用户呢称
    private TextView description;              //TODO　用户描述
    private ImageView homeNotice;
    private ImageView messageNotice;

    private List<BaseFragment> fragmentList;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private User user;

    private AuthInfo mAuthInfo;

    private WbShareHandler shareHandler;
    private HomeFragment homeFragment;
    private Oauth2AccessToken token;
    private TabLayout.Tab homeTab;
    private TabLayout.Tab messageTab;
    private MessageReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, PollingService.class)); //TODO 启动定时任务


        //注册广播接收器
        messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.jiang.microblog.MESSAGE_RECEIVER");
        registerReceiver(messageReceiver, intentFilter);
        messageReceiver.setOnMessageListener(this);

        token = AccessTokenKeeper.readAccessToken(this);
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        WbSdk.install(this, mAuthInfo);
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        initViews();
        initTabs();
        initEvents();
        //TODO 请求获取用户信息
        presenter = new UserPresenter(this);
        if (user == null) {
            presenter.getProfile(token.getToken(),token.getUid());
        }
        user = new Gson().fromJson(UserJson.JSON, User.class);
        Glide.with(MainActivity.this).load(user.getAvatar_hd()).into(header);
        //TODO　设置用户昵称
        usernaem.setText(user.getName());
        //TODO　设置用户描述
        description.setText(user.getDescription());
    }

    @Override
    public void onSuccess(Object object) {
        user = (User) object;
        //TODO　设置用户头像
        Glide.with(MainActivity.this).load(user.getAvatar_hd()).into(header);
        //TODO　设置用户昵称
        usernaem.setText(user.getName());
        //TODO　设置用户描述
        description.setText(user.getDescription());
    }

    @Override
    public void onError(String result) {
        Log.e("MainActivity-E", result);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        composeMicroblog = (CircleImageView) findViewById(R.id.home_share_microblog);
        homeSearch = (ImageView) findViewById(R.id.home_search);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        spinner = (Spinner) findViewById(R.id.home_spinner);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setTitle("");
        headerLayout = navigationView.getHeaderView(0);
        header = (CircleImageView) headerLayout.findViewById(R.id.home_account_icon);
        usernaem = (TextView) headerLayout.findViewById(R.id.home_account_name);
        description = (TextView) headerLayout.findViewById(R.id.home_description);
        //TODO　添加fragment
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);
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
        homeTab = tabLayout.getTabAt(0);
        messageTab = tabLayout.getTabAt(1);
        TabLayout.Tab discoverTab = tabLayout.getTabAt(2);
        homeTab.setCustomView(R.layout.tabber_home_icon);
        messageTab.setCustomView(R.layout.tabber_message_icon);
        discoverTab.setCustomView(R.layout.tabber_discover_icon);
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
        homeNotice = (ImageView) homeTab.getCustomView().findViewById(R.id.home_notice);
        messageNotice = (ImageView) messageTab.getCustomView().findViewById(R.id.message_notice);
        homeNotice.setVisibility(View.INVISIBLE);
        messageNotice.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化事件
     */
    private void initEvents() {
        composeMicroblog.setOnClickListener(this);
        homeSearch.setOnClickListener(this);
        header.setOnClickListener(this);
        usernaem.setOnClickListener(this);
        description.setOnClickListener(this);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //TODO　下拉框事件
        spinnerEvent();
        //TODO　viewPager滑动监听事件
        viewPagerEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.reft_out);
                break;
            case R.id.home_share_microblog:
                boolean isInstall = WbSdk.isWbInstall(MainActivity.this);
                if (isInstall) {
                    shareByClient();
                } else {
                    startActivity(new Intent(MainActivity.this, ShareActivity.class));
                }
                break;
            case R.id.home_account_icon:
            case R.id.home_account_name:
            case R.id.home_description:
                startProfileActivity(0);
                drawer.closeDrawers();
                break;
        }

    }

    /**
     * 启动ProfileActivity
     */
    private void startProfileActivity(int index) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(IntentKey.USER_INFORMATION, new Gson().toJson(user));
        intent.putExtra(IntentKey.USERNAME, "");
        intent.putExtra(IntentKey.PROFILE_FRAGMENT_INDEX, index);
        startActivity(intent);
    }

    /**
     * 通过微博客户端分享微博
     */
    private void shareByClient() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = "";
        textObject.title = "xxxx";
        textObject.actionUrl = "http://www.sina.com";
        weiboMessage.textObject = textObject;
        shareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 下拉框事件
     */
    private void spinnerEvent() {
        String[] mItems = getResources().getStringArray(R.array.title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.style_spinner, mItems);
        adapter.setDropDownViewResource(R.layout.style_spinner_dialog);
        //TODO　绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case HOME_TIMELINE:
                        if (listener != null) {
                            listener.onTypeListener(0);
                        }
                        break;
                    case BILATERAL_TIMELINE:
                        if (listener != null) {
                            listener.onTypeListener(1);
                        }
                        break;
                    case PUBLIC_TIMELINE:
                        if (listener != null) {
                            listener.onTypeListener(2);
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
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
                    //TODO home页面
                    toolbar.setTitle("");
                    spinner.setVisibility(View.VISIBLE);
                    homeSearch.setVisibility(View.GONE);
                    composeMicroblog.setVisibility(View.VISIBLE);
                    homeNotice.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    //TODO message页面
                    spinner.setVisibility(View.GONE);
                    toolbar.setTitle("消息");
                    homeSearch.setVisibility(View.GONE);
                    composeMicroblog.setVisibility(View.VISIBLE);
                    messageNotice.setVisibility(View.INVISIBLE);
                } else if (position == 2) {
                    //TODO discover页面
                    spinner.setVisibility(View.GONE);
                    toolbar.setTitle("热门");
                    homeSearch.setVisibility(View.VISIBLE);
                    composeMicroblog.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_all) {
            startProfileActivity(1);
        } else if (id == R.id.nav_gallery) {
            startProfileActivity(2);
        } else if (id == R.id.nav_favorite) {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
        } else if (id == R.id.nav_about) {
            Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_skin) {
            startActivity(new Intent(MainActivity.this, SkinActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_switch_account) {//TODO　切换账号
            AccessTokenKeeper.clear(MainActivity.this);
            startActivity(new Intent(MainActivity.this, GoodbyeActivity.class).putExtra(IntentKey.SWITCH_ACCOUNT, true));
        } else if (id == R.id.nav_quit) {//TODO　退出
            startActivity(new Intent(MainActivity.this, GoodbyeActivity.class).putExtra(IntentKey.SWITCH_ACCOUNT, false));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //TODO 按下back键时，不退出，返回桌面
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void onWbShareSuccess() {

    }

    @Override
    public void onWbShareCancel() {

    }

    @Override
    public void onWbShareFail() {

    }

    @Override
    public void onMessageListener(int status, int follower, int cmt, int mention_status, int mention_cmt) {
        Log.e("MainActivity", status + "|" + follower + "|" + cmt + "|" + mention_status + "|" + mention_cmt);
        if (status > 0) {
            homeNotice.setVisibility(View.VISIBLE);
        } else {
            homeNotice.setVisibility(View.INVISIBLE);
        }
        if (cmt > 0 || mention_cmt > 0 || mention_status > 0) {
            messageNotice.setVisibility(View.VISIBLE);
        } else {
            messageNotice.setVisibility(View.INVISIBLE);
        }

    }

    public interface OnTypeListener {
        public void onTypeListener(int type);
    }

    public OnTypeListener listener;

    public void setListener(OnTypeListener listener) {
        this.listener = listener;
    }

}
