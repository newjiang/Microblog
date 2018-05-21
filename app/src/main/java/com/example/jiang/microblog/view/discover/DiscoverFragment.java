package com.example.jiang.microblog.view.discover;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.view.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.view.home.adapter.RecyclerViewBaseAdapter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class DiscoverFragment extends BaseFragment implements MicroblogContract.View {

    private static final String TAG = DiscoverFragment.class.getSimpleName();
    private Oauth2AccessToken token;
    private MicroblogContract.Presenter presenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar loadingBar;
    private RecyclerViewBaseAdapter adapter;
    private List<Statuses> microblogList = new ArrayList<>();
    private ListViewAdapter.LoaderMoreHolder loaderHolder;
    private boolean isDown = true;          // 判断是否下拉操作
    private boolean isRefreshing = false;  // 是否正在刷新
    private int page = 2;                    // 上拉操作的起始页

    @Override
    public View initView() {
        Log.e(TAG, "DiscoverFragment...view...init...");
        View view = View.inflate(context, R.layout.fragment_discover, null);
        token = AccessTokenKeeper.readAccessToken(context);
        presenter = new MicroblogPresenter(this, context);
        recyclerView = (RecyclerView) view.findViewById(R.id.discover_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.discover_swipe_refresh);
        loadingBar = (ProgressBar) view.findViewById(R.id.discover_loading_bar);
        // 下拉刷新
        downPullUpdate();
        return view;
    }

    @Override
    public void initData() {
//        if (microblogList.isEmpty()) {
//            presenter.public_timeline(token.getToken(), 1);
//        }
    }

    @Override
    public void onSuccess(Object object) {
        Microblog microblog = (Microblog) object;
        List<Statuses> m = microblog.getStatuses();
        // 如果是null 则表示是初始化
        if (microblogList.isEmpty()) {
            microblogList = m;
            loadingBar.setVisibility(View.GONE);
            setListView();
        } else {
            // 添加数据
            adapter.add(m, isDown);
            if (isDown) {
                // 延迟2S处理，关闭下拉操作提示
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        isRefreshing = false;
                    }
                }, 2000);
            } else {
                if (m.isEmpty()) {
                    loaderHolder.update(loaderHolder.LOADER_STATE_COMPLETED);
                } else {
                    // 延迟2S处理，关闭上拉操作提示
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loaderHolder.update(loaderHolder.LOADER_STATE_NORMAL);
                            isRefreshing = false;
                        }
                    }, 2000);
                }
            }
        }
    }

    @Override
    public void onError(String result) {
        Log.e("DiscoverFragment-E", result);
    }

    private void setListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListViewAdapter(context, microblogList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        loadingBar.setVisibility(View.GONE);
        handlerUpPullUpdate();
    }

    //TODO 下拉刷新
    private void downPullUpdate() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setEnabled(true);
        refreshLayout.setProgressViewOffset(true, 0, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    presenter.public_timeline(token.getToken(), 1);
                    isRefreshing = true;
                    isDown = true;
                }
            }
        });
    }

    //TODO 上拉刷新
    private void handlerUpPullUpdate() {
        if (adapter instanceof ListViewAdapter) {
            ((ListViewAdapter) adapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    loaderHolder = loaderMoreHolder;
                    if (!isRefreshing) {
                        presenter.public_timeline(token.getToken(), page);
                        isRefreshing = true;
                        isDown = false;
                        page++;
                    }
                }
            });
        }
    }
}
