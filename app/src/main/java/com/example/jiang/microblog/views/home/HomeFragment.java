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
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.test.MicroblogJson;
import com.example.jiang.microblog.views.home.adapter.ListViewAdapter;
import com.example.jiang.microblog.views.home.adapter.RecyclerViewBaseAdapter;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class HomeFragment extends BaseFragment {

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
                //在这里面去执行刷新数据的操作
                /**
                 * 当我们在顶部,下拉的时候 ,这个方法就会被出发
                 * 但是,这个方法是MainThread是主线程,不可以执行耗时操作。
                 * 一般来说,我们去请求数据在开一个线程去获取
                 * //这里面演示的话,我直接添加一条数据
                 */
                //添加数据
                Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
                List<Microblog.StatusesBean> statuses = microblog.getStatuses();
                microblogList.add(0, statuses.get(1));
                //更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
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
                    //这里面去加载更多的数据,同样,需要在子线程中完成,这里仅作演示

                    //更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
                            List<Microblog.StatusesBean> statuses = microblog.getStatuses();
                            for (Microblog.StatusesBean s : statuses) {
                                microblogList.add(s);
                            }
                            //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                            adapter.notifyDataSetChanged();

                            loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);

//                            Random random = new Random();
//
//                            if (random.nextInt() % 2 == 0) {
//                                Microblog microblog = new Gson().fromJson(MicroblogJson.JSON, Microblog.class);
//                                List<Microblog.StatusesBean> statuses = microblog.getStatuses();
//                                for (Microblog.StatusesBean s : statuses) {
//                                    microblogList.add(s);
//                                }
//                                //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
//                                adapter.notifyDataSetChanged();
//
//                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);
//                            } else {
//                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_RELOAD);
//                            }
                        }
                    }, 3000);
                }
            });
        }
    }


    @Override
    public void onSuccess(Object object) {
        Microblog microblog = (Microblog) object;
        microblogList = microblog.getStatuses();
//        adapter = new HomeRecyclerAdapter(microblogList, context, true);
//        recyclerView.setAdapter(adapter);
//        layoutManager = new GridLayoutManager(context, 1);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        Log.e("onSuccess", "onSuccess");

    }

    @Override
    public void onError(String result) {
        Log.e("onError", result);
    }


}

