package com.example.jiang.microblog.view.search.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Weibo;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.search.activity.ResultActivity;
import com.example.jiang.microblog.view.search.adapter.WeiboAdapter;

import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class WeiboFragment extends BaseFragment {

    private static final String TAG = WeiboFragment.class.getSimpleName();

    private RecyclerView weiboRecyclerview;
    private ProgressBar weiboBar;
    private List<Weibo> weibos;
    private WeiboAdapter weiboAdapter;
    private boolean isFinish = false;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_weibo, null);
        weiboRecyclerview = (RecyclerView) view.findViewById(R.id.weibo_recyclerview);
        weiboBar = (ProgressBar) view.findViewById(R.id.weibo_bar);
        weiboBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void initData() {
        final String key = (String) getArguments().get(IntentKey.KEY_WORD);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weibos = CrawlerTools.findWeibo(key);
                    isFinish = true;
                    ResultActivity activity = (ResultActivity) context;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initWeiboView();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initWeiboView() {
        if (isFinish) {
            weiboBar.setVisibility(View.GONE);
            weiboAdapter = new WeiboAdapter(context, weibos);
            weiboRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            weiboRecyclerview.setAdapter(weiboAdapter);
        } else {
            weiboBar.setVisibility(View.VISIBLE);
        }
    }
}
