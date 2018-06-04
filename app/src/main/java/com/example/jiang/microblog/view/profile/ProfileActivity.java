package com.example.jiang.microblog.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.presenter.UserPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.adapter.ViewPagerAdapter;
import com.example.jiang.microblog.view.favourites.FavoriteActivity;
import com.example.jiang.microblog.view.profile.fragment.AlbumFragment;
import com.example.jiang.microblog.view.profile.fragment.MicroblogFragment;
import com.example.jiang.microblog.view.profile.fragment.ProfileFragment;
import com.example.jiang.microblog.widget.GlideRoundTransform;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements UserContract.View ,View.OnClickListener {

    private static final String MALE = "m";
    private static final String FEMALE = "f";

    private UserContract.Presenter presenter;
    private Oauth2AccessToken token;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout coll;
    private ImageView background;
    private TextView friends_count;
    private TextView followers_count;
    private TextView favourites_count;
    private TextView description;
    private ImageView gender;
    // 导航栏标题
    private List<String> navList;
    // 底部导航栏
    private TabLayout tabLayout;
    // 页面切换viewPager控件
    private ViewPager viewPager;
    // BaseFragment
    private List<BaseFragment> fragmentList;
    // PagerAdapter
    private ViewPagerAdapter viewPagerAdapter;

    private User userBean;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        token = AccessTokenKeeper.readAccessToken(this);
        presenter = new UserPresenter(this);
        getData();
    }

    @Override
    public void onSuccess(Object object) {
        userBean = (User) object;
        initData();
        initEvents();
        initTab();
        if (String.valueOf(userBean.getId()).equals(token.getUid())) {
            favourites_count.setVisibility(View.VISIBLE);
        } else {
            favourites_count.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(this, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {
        String json = getIntent().getStringExtra(IntentKey.USER_INFORMATION);
        Log.e("获取-用户用户用户：", json + "");
        index = getIntent().getIntExtra(IntentKey.PROFILE_FRAGMENT_INDEX, 0);
        if (json == null || json.equals("")) {
            String username = getIntent().getStringExtra(IntentKey.USERNAME);
            presenter.getProfileByName(token.getToken(), username);
        } else {
            userBean = new Gson().fromJson(json, User.class);
            initData();
            initEvents();
            initTab();
        }
    }

    private void initTab() {
        navList = new ArrayList<>();
        navList.add("关于");
        if (userBean == null) {
            navList.add("微博");
        } else {
            navList.add("微博(" + userBean.getStatuses_count() + ")");
        }
        navList.add("相册");
        // 添加fragment
        fragmentList = new ArrayList<>();
        ProfileFragment profileFragment = new ProfileFragment();
        MicroblogFragment microblogFragment = new MicroblogFragment();
        AlbumFragment albumFragment = new AlbumFragment();

        fragmentList.add(profileFragment);
        fragmentList.add(microblogFragment);
        fragmentList.add(albumFragment);
        String json = new Gson().toJson(userBean);
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.PROFILE_FRAGMENT, json);
        profileFragment.setArguments(bundle);
        microblogFragment.setArguments(bundle);
        albumFragment.setArguments(bundle);
        // 初始化viewpager适配器
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, navList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorHeight(2);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        coll = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        background = (ImageView) findViewById(R.id.background);
        friends_count = (TextView) findViewById(R.id.friends_count);
        followers_count = (TextView) findViewById(R.id.followers_count);
        favourites_count = (TextView) findViewById(R.id.favourites_count);
        description = (TextView) findViewById(R.id.description);
        gender = (ImageView) findViewById(R.id.gender);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        friends_count.setOnClickListener(this);
        followers_count.setOnClickListener(this);
        favourites_count.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (userBean != null) {
            coll.setTitle(userBean.getName());
            coll.setExpandedTitleGravity(Gravity.CENTER_VERTICAL);
            Glide.with(this).load(userBean.getAvatar_large()).transform(new GlideRoundTransform(this, 50)).into(fab);
            Glide.with(this).load(userBean.getCover_image_phone()).into(background);
            friends_count.setText("关注(" + userBean.getFriends_count() + ")");
            followers_count.setText("粉丝(" + userBean.getFollowers_count() + ")");
            favourites_count.setText("收藏(" + userBean.getFavourites_count() + ")");
            description.setText(userBean.getDescription());
            if (userBean.getGender().equals(MALE)) {
                Glide.with(ProfileActivity.this).load(R.drawable.icon_male).into(gender);
            } else if (userBean.getGender().equals(FEMALE)) {
                Glide.with(ProfileActivity.this).load(R.drawable.icon_female).into(gender);
            } else {
                Glide.with(ProfileActivity.this).load(R.drawable.icon_gender).into(gender);
            }
            if (userBean.getIdstr().equals(token.getUid())) {
                favourites_count.setVisibility(View.VISIBLE);
            } else {
                favourites_count.setVisibility(View.GONE);
            }
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_count:
                break;
            case R.id.followers_count:
                break;
            case R.id.favourites_count:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
