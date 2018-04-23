package com.example.jiang.microblog.view.profile.fragment;

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
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.json.MicroblogJson;
import com.example.jiang.microblog.view.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.view.home.adapter.RecyclerViewBaseAdapter;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jiang on 2018/4/14.
 */

public class MicroblogFragment extends BaseFragment implements MicroblogContract.View {

    private Oauth2AccessToken token;

    private MicroblogContract.Presenter presenter;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private RecyclerViewBaseAdapter adapter;

    List<Microblog.StatusesBean> microblogList = new ArrayList<>();


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);
        setRecyclerViewStyle();
        handlerDownPullUpdate();
        getMicroblogById();
        return view;
    }

    @Override
    public void initData() {
        presenter = new MicroblogPresenter(context);
    }

    /**
     * 初始化数据
     */
    private void getMicroblogById() {
        Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
        microblogList = microblog.getStatuses();
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
                Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
                List<Microblog.StatusesBean> statuses = microblog.getStatuses();
                microblogList.add(0, statuses.get(1));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    /**
     * 这个方法用于现实ListView一样的效果
     */
    private void setRecyclerViewStyle() {
        //RecyclerView需要设置样式,其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //设置布局管理器来控制
        //设置水平还是垂直
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new ListViewAdapter(context, microblogList);
        //设置到RecyclerView里头
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //初始化事件
        initListener();
    }

    private void initListener() {
        if (adapter instanceof ListViewAdapter) {
            ((ListViewAdapter) adapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        Random random = new Random();
                        if (random.nextInt() % 2 == 0) {
                            Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
                            List<Microblog.StatusesBean> statuses = microblog.getStatuses();
                            for (Microblog.StatusesBean s : statuses) {
                                microblogList.add(s);
                            }
                            adapter.notifyDataSetChanged();
                            loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);
                        } else {
                            loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_RELOAD);
                        }
                        }
                    }, 2000);
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
