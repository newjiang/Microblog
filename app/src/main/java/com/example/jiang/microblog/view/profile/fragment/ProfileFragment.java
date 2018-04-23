package com.example.jiang.microblog.view.profile.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.utils.IntentKey;
import com.google.gson.Gson;

/**
 * Created by jiang on 2018/4/14.
 */

public class ProfileFragment extends BaseFragment implements MicroblogContract.View {
    private static final int ONLINE_STATUS = 1;
    private static final String ONLINE = "在线";
    private static final String OFFLINE = "不在线";

    private Microblog.StatusesBean.UserBean userBean;

    private TextView relationship;
    private TextView onlineStatus;
    private TextView name;
    private TextView location;
    private TextView blogUrl;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_profile, null);
        relationship = (TextView) view.findViewById(R.id.relationship);
        onlineStatus = (TextView) view.findViewById(R.id.online_status);
        name = (TextView) view.findViewById(R.id.name);
        location = (TextView) view.findViewById(R.id.location);
        blogUrl = (TextView) view.findViewById(R.id.blog_url);
        return view;
    }

    @Override
    public void initData() {
        String json = (String) getArguments().get(IntentKey.PROFILE_FRAGMENT);
        Gson gson = new Gson();
        userBean = gson.fromJson(json, Microblog.StatusesBean.UserBean.class);

        if (userBean.getOnline_status() == ONLINE_STATUS) {
            onlineStatus.setText(ONLINE);
        } else {
            onlineStatus.setText(OFFLINE);
        }
        name.setText(userBean.getRemark());
        location.setText(userBean.getLocation());
        blogUrl.setText(userBean.getUrl());

    }

    @Override
    public void onSuccess(Object object) {
        relationship.setText("互相关注");
    }

    @Override
    public void onError(String result) {

    }
}
