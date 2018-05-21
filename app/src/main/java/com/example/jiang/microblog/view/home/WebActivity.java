package com.example.jiang.microblog.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.ShortUrl;
import com.example.jiang.microblog.mvp.contract.ShortUrlContract;
import com.example.jiang.microblog.mvp.presenter.ShortUrlPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends BaseActivity implements ShortUrlContract.View {

    private ShortUrlPresenter presenter;

    private Oauth2AccessToken token;

    private WebView webView;

    private List<String> urlLongList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        String text = intent.getStringExtra(IntentKey.WEIBO_URL);
        int start = text.lastIndexOf("http://t.cn");
        int end = text.length();
        final String url_short = text.substring(start, end).trim();
        token = AccessTokenKeeper.readAccessToken(this);
//        presenter.expand(token.getToken(), url_short);
    }

    @Override
    public void onSuccess(Object object) {
        ShortUrl shortUrl = (ShortUrl) object;
        Log.e("WebActivity-E", shortUrl.toString());
        for (int i = 0; i < shortUrl.getUrls().size(); i++) {
            urlLongList.add(shortUrl.getUrls().get(i).getUrl_long());
        }
        webView.loadUrl(urlLongList.get(0));
    }

    @Override
    public void onError(String result) {
        Log.e("WebActivity-E", result);
    }
}