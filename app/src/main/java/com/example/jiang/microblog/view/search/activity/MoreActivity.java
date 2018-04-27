package com.example.jiang.microblog.view.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.search.adapter.RecommendAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecommendAdapter adapter;
    private List<Hot> hots; //TODO 热门搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        hots = new ArrayList<>();
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.more_recyclerview);
        adapter = new RecommendAdapter(MoreActivity.this, hots);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoreActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        Intent intent = getIntent();
        String json = intent.getStringExtra(IntentKey.MORE_TOP);
        hots = new Gson().fromJson(json, new TypeToken<List<Hot>>(){}.getType());
    }
}
