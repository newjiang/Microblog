package com.example.jiang.microblog.view.profile.fragment;

import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.base.Constants;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.FavoriteContract;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.FavoritePresenter;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.OnFavouritesListener;
import com.example.jiang.microblog.view.profile.adapter.MicroblogAdapter;
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

public class MicroblogFragment extends BaseFragment implements MicroblogContract.View ,FavoriteContract.View{

    private WeiboPageUtils weiboPageUtils;
    private Oauth2AccessToken token;
    private AuthInfo mAuthInfo;

    private MicroblogContract.Presenter presenter;
    private FavoriteContract.Presenter favoritePresenter;

    private List<Statuses> microblogList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MicroblogAdapter adapter;
    private ProgressBar loadingBar;
    private TextView openView;
    private User user;
    // 是否是收藏操作
    private boolean isFavouritsOption = false;
    @Override
    public View initView() {
        token = AccessTokenKeeper.readAccessToken(context);
        presenter = new MicroblogPresenter(this, context);
        favoritePresenter = new FavoritePresenter(this, context);
        View view = View.inflate(context, R.layout.fragment_microblog, null);
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
                if (microblogList.isEmpty()) {
                    //测试
//                    presenter.user_timeline(token.getToken(), 1, 0);
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
        if (isFavouritsOption) {
            isFavouritsOption = false;
            return;
        }
        Microblog microblog = (Microblog) object;
        List<Statuses> m = microblog.getStatuses();
        // 如果是null 则表示是初始化
        if (microblogList.isEmpty()) {
            microblogList = m;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initRecyclerView();
                }
            }, 2000);
        } else {
            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(context, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MicroblogAdapter(context, microblogList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        loadingBar.setVisibility(View.GONE);
        adapter.setOnFavouritesListener(new OnFavouritesListener() {
            @Override
            public void onFavouritesListener(Statuses statuses, boolean isFavouritd) {
                isFavouritsOption = true;
                if (isFavouritd) {
                    favoritePresenter.destroyFavorites(token.getToken(), statuses.getId());
                } else {
                    favoritePresenter.createFavorites(token.getToken(), statuses.getId());
                }
            }
        });
    }
}
