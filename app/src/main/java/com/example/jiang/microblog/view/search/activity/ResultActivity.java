package com.example.jiang.microblog.view.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.bean.Weibo;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.IntentKey;
import com.google.gson.Gson;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        final String key = intent.getStringExtra(IntentKey.SEARCH_CONTENT);
        textView = (TextView) findViewById(R.id.jjjjj);
        textView.setText(key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Weibo> weibos = CrawlerTools.findWeibo(key);
                    for (Weibo w : weibos) {
                        Log.e("微博微博", w.toString());
                    }

                    List<Account> accounts = CrawlerTools.findUser(key);
                    for (Account a : accounts) {
                        Log.e("用户用户", a.toString());
                    }
                    List<Hot> hots = CrawlerTools.findTopSearch();
                    for (Hot h : hots) {
                        Log.e("热门搜索", h.toString());
                    }
                    System.out.println();
                    Log.e("", new Gson().toJson(hots));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
