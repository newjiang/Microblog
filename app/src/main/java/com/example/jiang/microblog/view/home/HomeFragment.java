package com.example.jiang.microblog.view.home;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.json.Down;
import com.example.jiang.microblog.json.MicroblogJson;
import com.example.jiang.microblog.json.Up;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.view.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.view.home.adapter.RecyclerViewBaseAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class HomeFragment extends BaseFragment {

    private MicroblogContract.Presenter presenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerViewBaseAdapter adapter;
    private List<Microblog.StatusesBean> microblogList = new ArrayList<>();

    int down = 1;
    int up = 1;

    @Override
    public View initView() {
        presenter = new MicroblogPresenter(this,context);
        View view = View.inflate(context, R.layout.fragment_home, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);
        //TODO 下拉加载更多
        handlerDownPullUpdate();
        return view;
    }

    @Override
    public void initData() {
        //TODO TODO 获取用户及用户关注好友的微博
        if (microblogList.isEmpty()) {
            presenter.getHomeMicroblog(App.getToken().getToken());
        } else {

        }
        Gson gson = new Gson();
        Microblog microblog = gson.fromJson(MicroblogJson.JSON, Microblog.class);
        microblogList = microblog.getStatuses();
        setListView();


    }
    @Override
    public void onSuccess(Object object) {
        Log.e("onSuccess", object.toString());
//        Microblog microblog = (Microblog) object;
//        //TODO 初始化数据
//        microblogList = microblog.getStatuses();
//        setListView();
    }
    @Override
    public void onError(String result) {
        Log.e("HomeFragment-onError", result);
    }
    /**
     * 这个方法用于现实ListView一样的效果
     */
    private void setListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //TODO 设置垂直
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListViewAdapter(context, microblogList);
        //TODO 设置到RecyclerView里头
        recyclerView.setAdapter(adapter);
        //TODO 下划线
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //TODO 上拉加载更多
        handlerUpPullUpdate();
    }

    /**
     * 下来事件
     */
    private void handlerDownPullUpdate() {
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//TODO 下拉   刷新加载+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //TODO 添加数据
                Microblog.StatusesBean microblog
                        = new Gson().fromJson(Up.JSON, Microblog.StatusesBean.class);
                microblog.setText("下拉下拉下拉下拉下拉 NO." + down);
                //TODO 添加的条数
                down++;
                microblogList.add(0, microblog);

                //TODO 延迟2秒更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO 这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
    private void handlerUpPullUpdate() {
        //TODO 这里面去处理上拉加载更多
        if (adapter instanceof ListViewAdapter) {
            ((ListViewAdapter) adapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    //TODO 更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//TODO 上拉   刷新加载+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Microblog.StatusesBean microblog
                                    = new Gson().fromJson(Down.JSON, Microblog.StatusesBean.class);
                            microblog.setText("上拉上拉上拉上拉上拉 NO." + up);
                            //TODO 添加的条数
                            up++;
                            microblogList.add(microblog);
                            //TODO 这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                            adapter.notifyDataSetChanged();
                            loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);
                        }
                    }, 1000);
                }
            });
        }
    }
}

