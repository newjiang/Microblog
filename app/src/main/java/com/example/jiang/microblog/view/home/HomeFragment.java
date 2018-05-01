package com.example.jiang.microblog.view.home;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.view.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.view.home.adapter.RecyclerViewBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private MicroblogContract.Presenter presenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerViewBaseAdapter adapter;
    private List<Microblog.StatusesBean> microblogList = new ArrayList<>();

    private ListViewAdapter.LoaderMoreHolder loaderHolder;

    private boolean isDown = true;  //TODO 判断是否下拉操作
    private int page = 2;//TODO 上拉操作的起始页

    @Override
    public View initView() {
        presenter = new MicroblogPresenter(this,context);
        View view = View.inflate(context, R.layout.fragment_home, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);
        //TODO 下拉刷新
        downPullUpdate();
        return view;
    }

    @Override
    public void initData() {
        if (microblogList.isEmpty()) {
            presenter.getHomeMicroblog(App.getToken().getToken(), 1);
        }
    }
    @Override
    public void onSuccess(Object object) {
        Microblog microblog = (Microblog) object;
        List<Microblog.StatusesBean> m = microblog.getStatuses();
        //TODO 如果是null 则表示是初始化
        if (microblogList.isEmpty()) {
            microblogList = m;
            setListView();
        } else {
            //TODO 添加数据
            adapter.add(m, isDown);
            if (isDown) {
                //TODO 延迟1S处理，关闭下拉操作提示
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            } else {
                //TODO 延迟1S处理，关闭上拉操作提示
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loaderHolder.update(loaderHolder.LOADER_STATE_NORMAL);
                    }
                }, 1000);
            }
        }
    }

    @Override
    public void onError(String result) {
        Log.e("HomeFragment-E", result);
    }

    private void setListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListViewAdapter(context, microblogList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                presenter.getHomeMicroblog(App.getToken().getToken(), 1);
                isDown = true;
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
                    presenter.getHomeMicroblog(App.getToken().getToken(), page);
                    page++;
                    isDown = false;
                }
            });
        }
    }

}

