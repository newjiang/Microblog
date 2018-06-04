package com.example.jiang.microblog.view.profile.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.base.Constants;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.presenter.UserPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.web.WeiboPageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiang on 2018/4/14.
 */

public class ProfileFragment extends BaseFragment implements UserContract.View, View.OnClickListener {

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

    private static final String FOCUS_ON_EACH_OTHER = "互相关注";
    private static final String FOCUSED  = "已关注";
    private static final String UNFOCUSED  = "未关注";
    private static final String FOLLOWED_ME = "关注我";

    private User userBean;

    private TextView option;

    private TextView relationship;
    private TextView name;
    private TextView location;
    private TextView blogUrl;
    private TextView created_at;

    private WeiboPageUtils weiboPageUtils;
    private Oauth2AccessToken token;
    private AuthInfo mAuthInfo;

    private UserPresenter presenter;

    @Override
    public View initView() {
        presenter = new UserPresenter(this, context);

        token = AccessTokenKeeper.readAccessToken(context);
        mAuthInfo = new AuthInfo(context, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        weiboPageUtils = WeiboPageUtils.getInstance(context, mAuthInfo);

        View view = View.inflate(context, R.layout.fragment_profile, null);
        option = (TextView) view.findViewById(R.id.option);
        relationship = (TextView) view.findViewById(R.id.relationship);
        name = (TextView) view.findViewById(R.id.name);
        location = (TextView) view.findViewById(R.id.location);
        blogUrl = (TextView) view.findViewById(R.id.blog_url);
        created_at = (TextView) view.findViewById(R.id.created_at);
        option.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        String screen_name = (String) getArguments().get(IntentKey.PROFILE_FRAGMENT);
        if (screen_name == null || screen_name.equals("")) {
            User user = new Gson().fromJson(screen_name, User.class);
            presenter.getProfileByName(token.getToken(), user.getName());
        } else {
            //TODO　获取传递过来的数据
            String json = (String) getArguments().get(IntentKey.PROFILE_FRAGMENT);
            Gson gson = new Gson();
            userBean = gson.fromJson(json, User.class);
            if (userBean != null) {
                //TODO 设置显示数据
                initInfo();
            }
        }
    }

    @Override
    public void onSuccess(Object object) {
        userBean = (User) object;
        //TODO 设置显示数据
        initInfo();
    }

    /**
     * 设置显示数据
     */
    private void initInfo() {

        location.setText(userBean.getLocation());
        created_at.setText(getTimeFormat(userBean.getCreated_at()));

        //TODO 判断备注
        if (userBean.getIdstr().equals(token.getUid())) {
            name.setText(userBean.getName());
        } else {
            if ("".equals(userBean.getRemark()) || userBean.getRemark() == null) {
                name.setText("无备注");
            } else {
                name.setText(userBean.getRemark());
            }
        }
        //TODO 判断博客
        if ("".equals(userBean.getUrl()) || userBean.getUrl() == null) {
            blogUrl.setText("无博客");
        } else {
            blogUrl.setText(userBean.getUrl());
        }
        //TODO 判断关系
        if (userBean.isFollow_me() && userBean.isFollowing()) {
            relationship.setText(FOCUS_ON_EACH_OTHER);
        } else if (userBean.isFollow_me() && !userBean.isFollowing()) {
            relationship.setText(FOLLOWED_ME);
        } else if (!userBean.isFollow_me() && userBean.isFollowing()) {
            relationship.setText(FOCUSED);
        } else {
            relationship.setText(UNFOCUSED);
            if (userBean.getIdstr().equals(token.getUid())) {
                relationship.setText("你是最棒的！");
            }
        }
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(context, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 时间的格式转化
     *
     * @param time
     * @return
     */
    public String getTimeFormat(String time) {
        SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
        return f.format(new Date(time));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option:
                weiboPageUtils.startUserMainPage(userBean.getIdstr());
                break;
        }

    }
}
