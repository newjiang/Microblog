package com.example.jiang.microblog.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.search.activity.MoreActivity;
import com.example.jiang.microblog.view.search.activity.ResultActivity;
import com.example.jiang.microblog.view.search.adapter.HistoryAdapter;
import com.example.jiang.microblog.view.search.adapter.HotAdapter;
import com.google.gson.Gson;
import com.zhy.changeskin.SkinManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    private EditText searchContent;     // 搜索框内容
    private ImageView searchIcon;       // 去搜索图标

    private TextView clearText;         // 清除搜索的历史记录

    private TextView moreRecommend;     // 更多

    private RecyclerView historyRecyclerView;
    private RecyclerView recommendRecyclerView;

    private HistoryAdapter historyAdapter;
    private HotAdapter hotAdapter;

    private List<History> historys;     // 历史记录
    private List<Hot> hots;             // 热门搜索

    public TextView getClearText() {
        return clearText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        historys = new ArrayList<>();
        hots = new ArrayList<>();
        initData();
    }

    private void initData() {
        final List<History> historyList = DataSupport.findAll(History.class);
        // 只显示5条历史记录
        if (historyList.size() > 5) {
            for (int i = 0; i < 5; i++) {
                historys.add(historyList.get(i));
            }
        } else {
            historys = historyList;
        }
        // 搜索热搜榜
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Hot> hotList = null;
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }
        }).start();

    }

    private void initView() {
        searchContent = (EditText) findViewById(R.id.search_edit);
        searchIcon = (ImageView) findViewById(R.id.search_icon);
        clearText = (TextView) findViewById(R.id.clear_history);
        moreRecommend = (TextView) findViewById(R.id.more_hots);
        historyRecyclerView = (RecyclerView) findViewById(R.id.search_history);
        recommendRecyclerView = (RecyclerView) findViewById(R.id.search_hots);
        searchIcon.setOnClickListener(this);
        clearText.setOnClickListener(this);
        moreRecommend.setOnClickListener(this);
        initHistory();
        initHots();
        showTips();
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
        hotAdapter = new HotAdapter(SearchActivity.this, hots);
        recommendRecyclerView.setAdapter(hotAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recommendRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
    /**
     * 初始化搜索历史记录
     */
    private void initHistory() {
        historyAdapter = new HistoryAdapter(SearchActivity.this, historys);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_icon:
                if (TextUtils.isEmpty(searchContent.getText())) {
                    Toast.makeText(this, "请输入搜索的内容", Toast.LENGTH_SHORT).show();
                } else {
                    historyAdapter.addHistory(new History(searchContent.getText().toString()));
                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                    intent.putExtra(IntentKey.SEARCH_CONTENT, searchContent.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.clear_history:
                historyAdapter.clearHistory();
                break;
            case R.id.more_hots:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent more = new Intent(SearchActivity.this, MoreActivity.class);
                        List<Hot> topSearch = CrawlerTools.findTopSearch();
                        more.putExtra(IntentKey.MORE_TOP, new Gson().toJson(topSearch));
                        startActivity(more);
                    }
                }).start();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        //　设置退出该activit时的动画
        overridePendingTransition(R.anim.search_reft_in, R.anim.search_right_out);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        historys.clear();
        List<History> historyList = DataSupport.findAll(History.class);
        if (historyList.size() > 5) {
            for (int i = 0; i < 5; i++) {
                historys.add(historyList.get(i));
            }
        } else {
            historys = historyList;
        }
        initHistory();
        showTips();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().register(this);
    }
}