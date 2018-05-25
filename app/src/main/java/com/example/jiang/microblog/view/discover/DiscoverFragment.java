package com.example.jiang.microblog.view.discover;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.discover.activity.MoreActivity;
import com.example.jiang.microblog.view.discover.activity.ResultActivity;
import com.example.jiang.microblog.view.discover.adapter.HistoryAdapter;
import com.example.jiang.microblog.view.discover.adapter.HotAdapter;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class DiscoverFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = DiscoverFragment.class.getSimpleName();
    private EditText searchContent;     // 搜索框内容
    private ImageView searchIcon;       // 去搜索图标

    private TextView clearText;         // 清除搜索的历史记录

    private TextView moreRecommend;     // 更多

    private RecyclerView historyRecyclerView;
    private RecyclerView recommendRecyclerView;
    private ProgressBar progressBar;

    private HistoryAdapter historyAdapter;
    private HotAdapter hotAdapter;
    // 历史记录
    private List<History> historys;
    // 热门搜索
    private List<Hot> hots = new ArrayList<Hot>();
    // 全部热门搜索
    private List<Hot> hotList = new ArrayList<Hot>();
    public TextView getClearText() {
        return clearText;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_search, null);
        searchContent = (EditText) view.findViewById(R.id.search_edit);
        searchIcon = (ImageView) view.findViewById(R.id.search_icon);
        clearText = (TextView) view.findViewById(R.id.clear_history);
        moreRecommend = (TextView) view.findViewById(R.id.more_hots);
        historyRecyclerView = (RecyclerView) view.findViewById(R.id.search_history);
        recommendRecyclerView = (RecyclerView) view.findViewById(R.id.search_hots);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        searchIcon.setOnClickListener(this);
        clearText.setOnClickListener(this);
        moreRecommend.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        final List<History> historyList = DataSupport.findAll(History.class);
        // 只显示5条历史记录
        if (historyList.size() > 5) {
            for (int i = 0; i < 5; i++) {
                historys.add(historyList.get(i));
            }
        } else {
            //少于5条
            if (historyList == null) {
                //没有搜索历史记录
                historys = new ArrayList<>();
            } else {
                historys = historyList;
            }
        }
        //搜索记录
        initHistory();
        showTips();

        // 搜索热搜榜
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    hotList = CrawlerTools.findTopSearch();
                    if (!hotList.isEmpty() && hotList.size() > 10) {
                        for (int i = 0; i < 10; i++) {
                            try {
                                hots.add(hotList.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (NullPointerException n) {
                    n.printStackTrace();
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initHots();
                    }
                });
            }
        }).start();
    }

    /**
     * 是否显示清除所有的历史记录
     */
    private void showTips() {
        if (historys.isEmpty()) {
            clearText.setVisibility(View.GONE);
        } else {
            clearText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化热搜榜单
     */
    private void initHots() {
        progressBar.setVisibility(View.GONE);
        hotAdapter = new HotAdapter(context, hots, false);
        recommendRecyclerView.setAdapter(hotAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recommendRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
    /**
     * 初始化搜索历史记录
     */
    private void initHistory() {
        historyAdapter = new HistoryAdapter(context, historys, this);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_icon:
                if (TextUtils.isEmpty(searchContent.getText())) {
                    Toast.makeText(context, "请输入搜索的内容", Toast.LENGTH_SHORT).show();
                } else {
                    historyAdapter.addHistory(new History(searchContent.getText().toString()));
                    Intent intent = new Intent(context, ResultActivity.class);
                    intent.putExtra(IntentKey.SEARCH_CONTENT, searchContent.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.clear_history:
                historyAdapter.clearHistory();
                break;
            case R.id.more_hots:
                Intent more = new Intent(context, MoreActivity.class);
                more.putExtra(IntentKey.MORE_TOP, new Gson().toJson(hotList));
                startActivity(more);
                break;
        }
    }
}
