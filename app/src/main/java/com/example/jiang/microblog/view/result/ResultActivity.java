package com.example.jiang.microblog.view.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.jiang.microblog.view.result.adapter.WeiboAdapter;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView userRecyclerview;
    private RecyclerView weiboRecyclerview;
    private ProgressBar userBar;
    private ProgressBar weiboBar;

    private List<Weibo> weibos;
    private List<Account> accounts;

    private AccountAdapter accountAdapter;
    private WeiboAdapter weiboAdapter;

    private boolean u = false;
    private boolean w = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("搜索结果");
        }
        userBar = (ProgressBar) findViewById(R.id.user_bar);
        weiboBar = (ProgressBar) findViewById(R.id.weibo_bar);

        Intent intent = getIntent();
        String key = intent.getStringExtra(IntentKey.SEARCH_CONTENT);
        search(key);
    }

    private void initUserView() {
        if (u) {
            userBar.setVisibility(View.GONE);
            userRecyclerview = (RecyclerView) findViewById(R.id.user_recyclerview);
            accountAdapter = new AccountAdapter(this, accounts);
            userRecyclerview.setLayoutManager(new GridLayoutManager(ResultActivity.this, 1, GridLayoutManager.HORIZONTAL, false));
            userRecyclerview.setAdapter(accountAdapter);
        } else {
            userBar.setVisibility(View.VISIBLE);
        }
    }

    private void initWeiboView() {
        if (w) {
            weiboBar.setVisibility(View.GONE);
            weiboRecyclerview = (RecyclerView) findViewById(R.id.weibo_recyclerview);
            weiboAdapter = new WeiboAdapter(this, weibos);
            weiboRecyclerview.setLayoutManager(new LinearLayoutManager(ResultActivity.this, LinearLayoutManager.VERTICAL, false));
            weiboRecyclerview.setAdapter(weiboAdapter);
        } else {
            weiboBar.setVisibility(View.VISIBLE);
        }
    }

    public void search(final String key) {
        //TODO 搜索用户
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    accounts = CrawlerTools.findUser(key);
                    u = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initUserView();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //TODO 搜索微博
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weibos = CrawlerTools.findWeibo(key);
                    w = true;
                    runOnUiThread(new Runnable() {
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
