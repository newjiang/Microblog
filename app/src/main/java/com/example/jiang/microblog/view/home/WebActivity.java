package com.example.jiang.microblog.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.ShortUrl;
import com.example.jiang.microblog.mvp.contract.ShortUrlContract;
import com.example.jiang.microblog.mvp.presenter.ShortUrlPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;


public class WebActivity extends BaseActivity implements ShortUrlContract.View {

    private ShortUrlPresenter presenter;

    private Oauth2AccessToken token;

    private WebView webView;

    private List<String> urlLongList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("链接详情");
        }
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        String text = intent.getStringExtra(IntentKey.WEIBO_URL);
        int start = text.lastIndexOf("http://t.cn");
        int end = text.length();
        String url_short = text.substring(start, end - 2);
        token = AccessTokenKeeper.readAccessToken(this);
        presenter = new ShortUrlPresenter(this);
        presenter.expand(token.getToken(),url_short);
     }

    @Override
    public void onSuccess(Object object) {
        ShortUrl shortUrl = (ShortUrl) object;
        for (int i = 0; i < shortUrl.getUrls().size(); i++) {
            urlLongList.add(shortUrl.getUrls().get(i).getUrl_long());
        }
        webView.loadUrl(urlLongList.get(0));
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(this, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
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
