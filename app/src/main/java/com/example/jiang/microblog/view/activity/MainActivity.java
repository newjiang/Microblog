package com.example.jiang.microblog.view.activity;

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
import com.example.jiang.microblog.bean.Setting;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.broadcast.MessageReceiver;
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
import com.zhy.changeskin.SkinManager;

import org.litepal.crud.DataSupport;

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
    //　发布微博按钮
    private CircleImageView composeMicroblog;
    // 搜索按钮
    private ImageView homeSearch;
    //　信息导航栏
    private NavigationView navigationView;
    //　旋转效果
    private ActionBarDrawerToggle toggle;
    //　底部导航栏
    private TabLayout tabLayout;
    //　页面切换viewPager控件
    private ViewPager viewPager;
    //　抽屉侧滑栏
    private DrawerLayout drawer;
    //　Toolbar
    private Toolbar toolbar;
    //　下拉框
    private Spinner spinner;
    //　导航界面头部
    private View headerLayout;
    //　用户头像
    private CircleImageView header;
    //　用户呢称
    private TextView usernaem;
    //　用户描述
    private TextView description;
    // 主页提示红点
    private ImageView homeNotice;
    // 消息页页提示红点
    private ImageView messageNotice;

    private List<BaseFragment> fragmentList;
    private MainViewPagerAdapter mainViewPagerAdapter;
    private HomeFragment homeFragment;
    private User user;

    // 主页fragment
    private TabLayout.Tab homeTab;
    // 消息页fragment
    private TabLayout.Tab messageTab;
    // 发现fragment
    private TabLayout.Tab discoverTab;

    // 消息监听器
    private MessageReceiver messageReceiver;
    private AuthInfo mAuthInfo;
    private WbShareHandler shareHandler;
    private Oauth2AccessToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, PollingService.class)); // 启动定时任务
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
    }

    @Override
    public void onSuccess(Object object) {
        user = (User) object;
        //　设置用户头像
        Glide.with(MainActivity.this).load(user.getAvatar_hd()).into(header);
        //　设置用户昵称
        usernaem.setText(user.getName());
        //　设置用户描述
        description.setText(user.getDescription());

        user.save();
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(this, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        composeMicroblog = (CircleImageView) findViewById(R.id.share_microblog);
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
        //　添加fragment
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(new MessageFragment());
        fragmentList.add(new DiscoverFragment());
        //　初始化viewpager适配器
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(mainViewPagerAdapter);
        //　设置隐藏和显示之和的fragment总数数
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
        discoverTab = tabLayout.getTabAt(2);
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
        //　下拉框事件
        spinnerEvent();
        //　viewPager滑动监听事件
        viewPagerEvent();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        homeNotice.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        messageNotice.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.reft_out);
                break;
            case R.id.share_microblog:
                boolean isInstall = WbSdk.isWbInstall(MainActivity.this);
                if (isInstall) {
                    List<Setting> settings = DataSupport.where("uid = ?", token.getUid()).find(Setting.class);
                    boolean b = settings.get(0).isShareByDefault();
                    if (b) {
                        shareByClient();
                    } else {
                        startActivity(new Intent(MainActivity.this, ShareActivity.class));
                    }
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
        //　绑定 Adapter到控件
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
                    // home页面
                    toolbar.setTitle("");
                    spinner.setVisibility(View.VISIBLE);
                    homeSearch.setVisibility(View.GONE);
                    composeMicroblog.setVisibility(View.VISIBLE);
                    homeNotice.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    // message页面
                    spinner.setVisibility(View.GONE);
                    toolbar.setTitle("消息");
                    homeSearch.setVisibility(View.GONE);
                    composeMicroblog.setVisibility(View.VISIBLE);
                    messageNotice.setVisibility(View.INVISIBLE);
                } else if (position == 2) {
                    // discover页面
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
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_skin) {
            startActivity(new Intent(MainActivity.this, SkinActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_switch_account) {
            //TODO　切换账号
            AccessTokenKeeper.clear(MainActivity.this);
            startActivity(new Intent(MainActivity.this, GoodbyeActivity.class).putExtra(IntentKey.SWITCH_ACCOUNT, true));
        } else if (id == R.id.nav_quit) {
            //TODO　退出
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
        void onTypeListener(int type);
    }

    public OnTypeListener listener;

    public void setListener(OnTypeListener listener) {
        this.listener = listener;
    }

}
