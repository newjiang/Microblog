package com.example.jiang.microblog.view.home;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.mvp.contract.FavoriteContract;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.FavoritePresenter;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.utils.OnFavouritesListener;
import com.example.jiang.microblog.view.activity.MainActivity;
import com.example.jiang.microblog.view.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.view.home.adapter.RecyclerViewBaseAdapter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements MicroblogContract.View,FavoriteContract.View{

    private Oauth2AccessToken token;

    private MicroblogContract.Presenter presenter;
    private FavoriteContract.Presenter favoritePresenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar loadingBar;
    private RecyclerViewBaseAdapter adapter;
    private List<Statuses> microblogList = new ArrayList<>();

    private ListViewAdapter.LoaderMoreHolder loaderHolder;
    // 判断是否下拉操作
    private boolean isDown = true;
    // 是否正在刷新
    private boolean isRefreshing = false;
    // 上拉操作的起始页
    private int page = 2;
    // 当前显示微博类型
    private int currentType = -1;
    // 当前显示微博类型是否变化
    private boolean isTypeChange = false;
    // 是否是收藏操作
    private boolean isFavouritsOption = false;
    @Override
    public View initView() {
        token = AccessTokenKeeper.readAccessToken(context);
        presenter = new MicroblogPresenter(this, context);
        favoritePresenter = new FavoritePresenter(this, context);
        View view = View.inflate(context, R.layout.fragment_home, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);
        loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        // 下拉刷新
        downPullUpdate();
        return view;
    }
    @Override
    public void initData() {
        MainActivity activity = (MainActivity) context;
        activity.setListener(new MainActivity.OnTypeListener() {
            @Override
            public void onTypeListener(int type) {
                handleTypeChange(type);
            }
        });
    }

    /**
     * 处理显示微博类型改变
     *
     * @param type
     */
    private void handleTypeChange(int type) {
        if (currentType != type) {
            isTypeChange = true;
            currentType = type;
            page = 2;
            if (!microblogList.isEmpty()) {
                microblogList.clear();
                adapter.notifyDataSetChanged();
            }
            getFirstPage(type);
        }
    }

    /**
     * 获取第一页的数据
     *
     * @param type
     */
    private void getFirstPage(int type) {
        switch (type) {
            case MainActivity.HOME_TIMELINE:
                presenter.home_timeline(token.getToken(), 1);
                break;
            case MainActivity.BILATERAL_TIMELINE:
                presenter.bilateral_timeline(token.getToken(), 1);
                break;
            case MainActivity.PUBLIC_TIMELINE:
                presenter.public_timeline(token.getToken(), 1);
                break;
        }
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
            isTypeChange = false;
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
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(context, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
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
        adapter.setOnFavouritesListener(new OnFavouritesListener() {
            @Override
            public void onFavouritesListener(Statuses statuses, boolean isFavouritd) {
                Log.e("收藏收藏", isFavouritd + "|" + statuses.toString());
                isFavouritsOption = true;
                if (isFavouritd) {
                    favoritePresenter.destroyFavorites(token.getToken(), statuses.getId());
                } else {
                    favoritePresenter.createFavorites(token.getToken(), statuses.getId());
                }
            }
        });
    }

    // 下拉刷新
    private void downPullUpdate() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setEnabled(true);
        refreshLayout.setProgressViewOffset(true, 0, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    getFirstPage(currentType);
                    isRefreshing = true;
                    isDown = true;
                }
            }
        });
    }

    // 上拉刷新
    private void handlerUpPullUpdate() {
        if (adapter instanceof ListViewAdapter) {
            ((ListViewAdapter) adapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    loaderHolder = loaderMoreHolder;
                    if (!isTypeChange) {
                        if (!isRefreshing) {
                            switch (currentType) {
                                case MainActivity.HOME_TIMELINE:
                                    presenter.home_timeline(token.getToken(), page);
                                    break;
                                case MainActivity.BILATERAL_TIMELINE:
                                    presenter.bilateral_timeline(token.getToken(), page);
                                    break;
                                case MainActivity.PUBLIC_TIMELINE:
                                    presenter.public_timeline(token.getToken(), page);
                                    break;
                            }
                            isRefreshing = true;
                        } else {
                            loaderHolder.update(loaderMoreHolder.LOADER_STATE_RELOAD);
                        }
                        page++;
                    }
                    isDown = false;
                }
            });
        }
    }
}

