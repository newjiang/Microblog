package com.example.jiang.microblog.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.view.search.adapter.AllHistoryAdapter;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_history);
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
    public void onBackPressed() {
        startActivity(new Intent(AllHistoryActivity.this, SearchActivity.class));
    }

}
