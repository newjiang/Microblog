package com.example.jiang.microblog.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.web.WeiboPageUtils;

public class NotificationContentActivity extends AppCompatActivity implements View.OnClickListener {

    private AuthInfo mAuthInfo;

    private Button commentWeibo;
    private Button gotoMyHomePage;
    private Button startUserMainPage;
    private Button startWeiboDetailPage;
    private WeiboPageUtils pageUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_content);
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        pageUtils = WeiboPageUtils.getInstance(NotificationContentActivity.this, mAuthInfo);
        commentWeibo = (Button) findViewById(R.id.commentWeibo);
        gotoMyHomePage = (Button) findViewById(R.id.gotoMyHomePage);
        startUserMainPage = (Button) findViewById(R.id.startUserMainPage);
        startWeiboDetailPage = (Button) findViewById(R.id.startWeiboDetailPage);
        commentWeibo.setOnClickListener(this);
        gotoMyHomePage.setOnClickListener(this);
        startUserMainPage.setOnClickListener(this);
        startWeiboDetailPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentWeibo:
                Toast.makeText(NotificationContentActivity.this, "评论某一条微博", Toast.LENGTH_SHORT).show();
                pageUtils.commentWeibo("4230466846817001");
                break;
            case R.id.gotoMyHomePage:
                Toast.makeText(NotificationContentActivity.this, "打开我的微博信息流", Toast.LENGTH_SHORT).show();
                pageUtils.shareToWeibo("打开我的微博信息流");
                break;
            case R.id.startUserMainPage:
                Toast.makeText(NotificationContentActivity.this, "连接到指定个人主页，连接后可进行加关注等互动", Toast.LENGTH_SHORT).show();
                pageUtils.startUserMainPage("2209988554");
                break;
            case R.id.startWeiboDetailPage:
                Toast.makeText(NotificationContentActivity.this, "连接到指定的单条微博详情页", Toast.LENGTH_SHORT).show();
                pageUtils.startWeiboDetailPage("4230466846817001","2209988554",true);
                break;
            default:
                break;
        }
    }
}
