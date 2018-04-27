package com.example.jiang.microblog.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.search.activity.AllHistoryActivity;
import com.example.jiang.microblog.view.search.activity.MoreActivity;
import com.example.jiang.microblog.view.search.activity.ResultActivity;
import com.example.jiang.microblog.view.search.adapter.HistoryAdapter;
import com.example.jiang.microblog.view.search.adapter.RecommendAdapter;

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
    private RecommendAdapter recommendAdapter;

    private List<History> historys;   //TODO 历史记录
    private List<String> recommends; //TODO 热门搜索

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
        recommends = new ArrayList<>();
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
        recommends.add("东风26型导弹列装火箭军");
        recommends.add("杨幂 刘海");
        recommends.add("张杰 她就是小孩");
        recommends.add("尤物唤新季");
        recommends.add("奶茶肚");
        recommends.add("整容院员工去整鼻");
        recommends.add("画出爸妈离婚全过程");
        recommends.add("金莎朗掉入下水道骨折");
        recommends.add("嗯哼吐槽霍思燕方形脸");
        recommends.add("李敖私生女回应");
        recommends.add("谢娜为杨迪庆生");
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
        recommendAdapter = new RecommendAdapter(SearchActivity.this, recommends);
        recommendRecyclerView.setAdapter(recommendAdapter);
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
                historyAdapter.addHistory(new History(searchContent.getText().toString()));
                Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                intent.putExtra(IntentKey.SEARCH_CONTENT, searchContent.getText().toString());
                startActivity(intent);
                break;
            case R.id.clear_history:
                historyAdapter.clearHistory();
                break;
            case R.id.all_history:
                startActivity(new Intent(SearchActivity.this, AllHistoryActivity.class));
                break;
            case R.id.more_recommend:
                startActivity(new Intent(SearchActivity.this, MoreActivity.class));
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
