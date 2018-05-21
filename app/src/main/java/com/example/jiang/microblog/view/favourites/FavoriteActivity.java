package com.example.jiang.microblog.view.favourites;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.Favorites;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.mvp.contract.FavoriteContract;
import com.example.jiang.microblog.mvp.presenter.FavoritePresenter;
import com.example.jiang.microblog.view.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.view.home.adapter.RecyclerViewBaseAdapter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends BaseActivity implements
        FavoriteContract.View ,View.OnLongClickListener{

    private FavoriteContract.Presenter presenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar loadingBar;
    private RecyclerViewBaseAdapter adapter;
    private List<Statuses> microblogList = new ArrayList<>();

    private ListViewAdapter.LoaderMoreHolder loaderHolder;

    private boolean isDown = true;          // 判断是否下拉操作
    private boolean isRefreshing = false;  // 是否正在刷新
    private int page = 2;                    // 上拉操作的起始页
    private Oauth2AccessToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("我的收藏");
        }
        setContentView(R.layout.activity_favorite);
        recyclerView = (RecyclerView) findViewById(R.id.favourites);
        recyclerView.setOnLongClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipere_freshlayout);
        loadingBar = (ProgressBar) findViewById(R.id.progress_bar);
        // 下拉刷新
        downPullUpdate();
        token = AccessTokenKeeper.readAccessToken(this);
        presenter = new FavoritePresenter(this);
        if (microblogList.isEmpty()) {
            presenter.getFavorites(token.getToken(), 1);
        }

    }

    @Override
    public void onSuccess(Object object) {
        Favorites f = (Favorites) object;
        List<Statuses> s = new ArrayList<>();
        for (int i = 0; i < f.getFavorites().size(); i++) {
            s.add(f.getFavorites().get(i).getStatus());
        }
        if (microblogList.isEmpty()) {
            microblogList = s;
            setListView();
        } else {
            if (s.isEmpty()) {
                loaderHolder.update(loaderHolder.LOADER_STATE_COMPLETED);
            } else {
                // 添加数据
                adapter.add(s, isDown);
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

    private void setListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListViewAdapter(this, microblogList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        loadingBar.setVisibility(View.GONE);
        handlerUpPullUpdate();
    }

    @Override
    public void onError(String result) {

    }

    //TODO 上拉刷新
    private void downPullUpdate() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setEnabled(true);
        refreshLayout.setProgressViewOffset(true, 0, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    presenter.getFavorites(token.getToken(), 1);
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
                        presenter.getFavorites(token.getToken(), page);
                        page++;
                        isDown = false;
                        isRefreshing = true;
                    }

                }
            });
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
    public boolean onLongClick(View v) {
        Toast.makeText(this, "长安", Toast.LENGTH_SHORT).show();
        return true;
    }
}
