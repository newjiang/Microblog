package com.example.jiang.microblog.view.discover.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.view.discover.adapter.AllHistoryAdapter;
import com.zhy.changeskin.SkinManager;

import org.litepal.crud.DataSupport;

import java.util.List;

public class AllHistoryActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private TextView clearHistory;
    private AllHistoryAdapter adapter;
    private List<History> histories;

    public TextView getClearHistory() {
        return clearHistory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_history);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("历史记录");
        }
        //TODO　查询搜索的历史记录
        histories = DataSupport.findAll(History.class);

        recyclerView = (RecyclerView) findViewById(R.id.all_history_recyclerview);
        clearHistory = (TextView) findViewById(R.id.clear_all_history);

        adapter = new AllHistoryAdapter(AllHistoryActivity.this, histories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllHistoryActivity.this, LinearLayoutManager.VERTICAL, false));

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearHistory();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
