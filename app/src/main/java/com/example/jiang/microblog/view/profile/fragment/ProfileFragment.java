package com.example.jiang.microblog.view.profile.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.presenter.UserPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiang on 2018/4/14.
 */

public class ProfileFragment extends BaseFragment implements UserContract.View {

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

    private static final String FOCUS_ON_EACH_OTHER = "互相关注";
    private static final String FOCUSED  = "已关注";
    private static final String UNFOCUSED  = "未关注";
    private static final String FOLLOWED_ME = "关注我";

    private User userBean;

    private TextView relationship;
    private TextView name;
    private TextView location;
    private TextView blogUrl;
    private TextView created_at;

    private UserPresenter presenter;
    private Oauth2AccessToken token;


    @Override
    public View initView() {
        presenter = new UserPresenter(this, context);
        token = AccessTokenKeeper.readAccessToken(context);
        View view = View.inflate(context, R.layout.fragment_profile, null);
        relationship = (TextView) view.findViewById(R.id.relationship);
        name = (TextView) view.findViewById(R.id.name);
        location = (TextView) view.findViewById(R.id.location);
        blogUrl = (TextView) view.findViewById(R.id.blog_url);
        created_at = (TextView) view.findViewById(R.id.created_at);
        return view;
    }

    @Override
    public void initData() {

        String screen_name = (String) getArguments().get(IntentKey.PROFILE_FRAGMENT);
        if (screen_name == null || screen_name.equals("")) {
            presenter.getProfileByName(token.getToken(), screen_name);
        } else {
            //TODO　获取传递过来的数据
            String json = (String) getArguments().get(IntentKey.PROFILE_FRAGMENT);
            Gson gson = new Gson();
            userBean = gson.fromJson(json, User.class);
            //TODO 设置显示数据
            name.setText(userBean.getRemark());
            location.setText(userBean.getLocation());
            blogUrl.setText(userBean.getUrl());
            created_at.setText(getTimeFormat(userBean.getCreated_at()));
        }
    }

    @Override
    public void onSuccess(Object object) {
        relationship.setText(FOCUS_ON_EACH_OTHER);
        userBean = (User) object;
        //TODO 设置显示数据
        name.setText(userBean.getRemark());
        location.setText(userBean.getLocation());
        blogUrl.setText(userBean.getUrl());
        created_at.setText(getTimeFormat(userBean.getCreated_at()));
    }

    @Override
    public void onError(String result) {

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
}
