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
import com.example.jiang.microblog.view.search.adapter.HistoryAdapter;
import com.example.jiang.microblog.view.search.adapter.RecommendAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    //TODO 微博热搜榜  http://s.weibo.com/top/summary?cate=realtimehot

    private EditText searchContent;
    private ImageView searchIcon;

    private TextView clearText;
    private TextView allHistory;

    private RecyclerView historyRecyclerView;
    private RecyclerView recommendRecyclerView;

    private HistoryAdapter historyAdapter;
    private RecommendAdapter recommendAdapter;

    private List<History> historys;
    private List<String> recommends;

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
        recommends.add("世界知识产权日");
        recommends.add("嗯哼吐槽霍思燕方形脸");
        recommends.add("饺子化妆");
        recommends.add("乐华七子");
        recommends.add("谢娜为杨迪庆生");
        recommends.add("当了39次伴娘仍单身");
        recommends.add("科三没过 我感觉委屈");
        recommends.add("世界知识产权日");
        recommends.add("嗯哼吐槽霍思燕方形脸");
        recommends.add("饺子化妆");
        recommends.add("乐华七子");
    }

    private void initView() {
        searchContent = (EditText) findViewById(R.id.search_edit);
        searchIcon = (ImageView) findViewById(R.id.search_icon);
        clearText = (TextView) findViewById(R.id.clear_history);
        allHistory = (TextView) findViewById(R.id.all_history);
        historyRecyclerView = (RecyclerView) findViewById(R.id.search_history);
        recommendRecyclerView = (RecyclerView) findViewById(R.id.search_recommend);
        searchIcon.setOnClickListener(this);
        clearText.setOnClickListener(this);
        allHistory.setOnClickListener(this);

        historyAdapter = new HistoryAdapter(SearchActivity.this, historys);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));

        recommendAdapter = new RecommendAdapter(SearchActivity.this, recommends);
        recommendRecyclerView.setAdapter(recommendAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recommendRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        if (historys.isEmpty()) {
            clearText.setVisibility(View.GONE);
            allHistory.setVisibility(View.GONE);
        } else {
            clearText.setVisibility(View.VISIBLE);
            allHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_icon:
                historyAdapter.addHistory(new History(searchContent.getText().toString()));
                break;
            case R.id.clear_history:
                historyAdapter.clearHistory();
                break;
            case R.id.all_history:
                startActivity(new Intent(SearchActivity.this, AllHistoryActivity.class));
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.search_reft_in, R.anim.search_right_out);
    }
}
