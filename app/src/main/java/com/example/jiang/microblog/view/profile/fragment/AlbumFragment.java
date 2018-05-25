package com.example.jiang.microblog.view.profile.fragment;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.base.Constants;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.PicUrlsBean;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.profile.adapter.AlbumAdapter;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.web.WeiboPageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class AlbumFragment extends BaseFragment implements MicroblogContract.View {

    private MicroblogContract.Presenter presenter;
    private WeiboPageUtils weiboPageUtils;
    private Oauth2AccessToken token;
    private AuthInfo mAuthInfo;

    private List<PicUrlsBean> picUrlsBeen = new ArrayList<>();
    private AlbumAdapter albumAdapter;
    private RecyclerView recyclerView;
    private ProgressBar loadingBar;
    private TextView openView;
    private User user;

    @Override
    public View initView() {
        token = AccessTokenKeeper.readAccessToken(context);
        presenter = new MicroblogPresenter(this, context);
        View view = View.inflate(context, R.layout.fragment_album, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        openView = (TextView) view.findViewById(R.id.open_web_view);
        return view;
    }

    @Override
    public void initData() {
        mAuthInfo = new AuthInfo(context, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        weiboPageUtils = WeiboPageUtils.getInstance(context, mAuthInfo);

        String userString = (String) getArguments().get(IntentKey.PROFILE_FRAGMENT);
        user = new Gson().fromJson(userString, User.class);

        if (user != null) {
            if (user.getIdstr().equals(token.getUid())) {
                if (picUrlsBeen.isEmpty()) {
                    //测试
//                    presenter.user_timeline(token.getToken(), 1, 2);
                }
            } else {
                recyclerView.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingBar.setVisibility(View.GONE);
                        openView.setVisibility(View.VISIBLE);
                    }
                }, 2000);
            }
        } else {
            Toast.makeText(context, "未获取到数据！", Toast.LENGTH_SHORT).show();
        }

        openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    weiboPageUtils.startUserMainPage(token.getUid());
                } else {
                    weiboPageUtils.startUserMainPage(user.getIdstr());
                }
            }
        });
    }

    @Override
    public void onSuccess(Object object) {
        Microblog microblog = (Microblog) object;
        List<Statuses> list = microblog.getStatuses();
        for (Statuses s : list) {
            if (s.getPic_urls() != null) {
                picUrlsBeen.addAll(s.getPic_urls());
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
            }
        }, 2000);

    }
    private void initRecyclerView() {
        albumAdapter = new AlbumAdapter(context, picUrlsBeen);
        recyclerView.setAdapter(albumAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(context, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

}
