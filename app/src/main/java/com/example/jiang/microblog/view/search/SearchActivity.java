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
import com.example.jiang.microblog.view.search.activity.AllHistoryActivity;
import com.example.jiang.microblog.view.search.activity.MoreActivity;
import com.example.jiang.microblog.view.result.ResultActivity;
import com.example.jiang.microblog.view.search.adapter.HistoryAdapter;
import com.example.jiang.microblog.view.search.adapter.HotAdapter;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    //TODO 微博热搜榜  http://s.weibo.com/top/summary?cate=realtimehot

    private EditText searchContent;  //TODO 搜索框内容
    private ImageView searchIcon;//TODO 去搜索图标

    private TextView clearText;//TODO 清除搜索的历史记录
    private TextView allHistory;//TODO 全部

    private TextView moreRecommend;//TODO 更多

    private RecyclerView historyRecyclerView;
    private RecyclerView recommendRecyclerView;

    private HistoryAdapter historyAdapter;
    private HotAdapter hotAdapter;

    private List<History> historys;   //TODO 历史记录
    private List<Hot> hots; //TODO 热门搜索

    public TextView getClearText() {
        return clearText;
    }

    public TextView getAllHistory() {
        return allHistory;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        historys = new ArrayList<>();
        hots = new ArrayList<>();
        initData();
        initView();
    }

    private void initData() {
        List<History> historyList = DataSupport.findAll(History.class);
        if (historyList.size() > 5) {
            for (int i = 0; i < 5; i++) {
                historys.add(historyList.get(i));
            }
        } else {
            historys = historyList;
        }
        Gson gson = new Gson();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Hot> hotList = CrawlerTools.findTopSearch();
                for (int i = 0; i < 10; i++) {
                    hots.add(hotList.get(i));
                }
            }
        }).start();

    }

    private void initView() {
        searchContent = (EditText) findViewById(R.id.search_edit);
        searchIcon = (ImageView) findViewById(R.id.search_icon);
        clearText = (TextView) findViewById(R.id.clear_history);
        allHistory = (TextView) findViewById(R.id.all_history);
        moreRecommend = (TextView) findViewById(R.id.more_recommend);
        historyRecyclerView = (RecyclerView) findViewById(R.id.search_history);
        recommendRecyclerView = (RecyclerView) findViewById(R.id.search_recommend);
        searchIcon.setOnClickListener(this);
        clearText.setOnClickListener(this);
        allHistory.setOnClickListener(this);
        moreRecommend.setOnClickListener(this);

        initHistory();
        initRecommend();
        showTips();
    }

    private void showTips() {
        if (historys.isEmpty()) {
            clearText.setVisibility(View.GONE);
            allHistory.setVisibility(View.GONE);
        } else {
            clearText.setVisibility(View.VISIBLE);
            allHistory.setVisibility(View.VISIBLE);
        }
    }

    private void initRecommend() {
        hotAdapter = new HotAdapter(SearchActivity.this, hots);
        recommendRecyclerView.setAdapter(hotAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recommendRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

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
            case R.id.all_history:
                startActivity(new Intent(SearchActivity.this, AllHistoryActivity.class));
                break;
            case R.id.more_recommend:
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
}
