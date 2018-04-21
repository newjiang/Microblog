package com.example.jiang.microblog.views.home;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.json.Down;
import com.example.jiang.microblog.json.MicroblogJson;
import com.example.jiang.microblog.json.Up;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.views.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.views.home.adapter.RecyclerViewBaseAdapter;
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

    List<Microblog.StatusesBean> microblogList = new ArrayList<>();

    int down = 1;
    int up = 1;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);

        //初始化数据
        Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
        microblogList = microblog.getStatuses();

        showList(true, false);
        handlerDownPullUpdate();
        return view;
    }

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

                //添加数据
                Microblog.StatusesBean microblog
                        = new Gson().fromJson(Up.JSON, Microblog.StatusesBean.class);
                microblog.setText("下拉下拉下拉下拉下拉 NO." + down);
                //TODO 添加的条数
                down++;
                microblogList.add(0, microblog);
                //更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    /**
     * 这个方法用于现实ListView一样的效果
     */
    private void showList(boolean isVertical, boolean isReverse) {
        //RecyclerView需要设置样式,其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //设置布局管理器来控制
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置标准(正向)还是反向的
        layoutManager.setReverseLayout(isReverse);

        recyclerView.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new ListViewAdapter(context,microblogList);
        //设置到RecyclerView里头
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //初始化事件
        initListener();
    }

    private void initListener() {

        //这里面去处理上拉加载更多
        if (adapter instanceof ListViewAdapter) {
            ((ListViewAdapter) adapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    //更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Microblog.StatusesBean microblog
                                    = new Gson().fromJson(Down.JSON, Microblog.StatusesBean.class);
                            microblog.setText("上拉上拉上拉上拉上拉 NO." + up);
                            //TODO 添加的条数
                            up++;
                            microblogList.add(microblog);
                            //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                            adapter.notifyDataSetChanged();
                            loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);
                        }
                    }, 1000);
                }
            });
        }
    }

    @Override
    public void onSuccess(Object object) {
        Log.e("onSuccess", object.toString());
    }

    @Override
    public void onError(String result) {
        Log.e("onError", result);
    }

}

