package com.example.jiang.microblog.view.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.bean.Weibo;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.result.adapter.AccountAdapter;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView userRecyclerview;
    private RecyclerView weiboRecyclerview;
    private ProgressBar bar;

    private List<Weibo> weibos;
    private List<Account> accounts;

    private AccountAdapter accountAdapter;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("搜索结果");
        }
        bar = (ProgressBar) findViewById(R.id.progressbar);

        Intent intent = getIntent();
        String key = intent.getStringExtra(IntentKey.SEARCH_CONTENT);
        search(key);
    }

    private void initView() {
        if (flag) {
            bar.setVisibility(View.GONE);
            userRecyclerview = (RecyclerView) findViewById(R.id.user_recyclerview);
            accountAdapter = new AccountAdapter(this, accounts);
            userRecyclerview.setLayoutManager(new GridLayoutManager(ResultActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
            userRecyclerview.setAdapter(accountAdapter);
        } else {
            bar.setVisibility(View.VISIBLE);
        }

    }


    public void search(final String key) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weibos = CrawlerTools.findWeibo(key);
                    accounts = CrawlerTools.findUser(key);
                    flag = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
}
