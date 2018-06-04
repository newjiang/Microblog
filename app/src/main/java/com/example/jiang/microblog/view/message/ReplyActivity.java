package com.example.jiang.microblog.view.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.mvp.contract.CommentContract;
import com.example.jiang.microblog.mvp.presenter.CommentPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.view.share.at.AtActivity;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import static com.example.jiang.microblog.view.share.ShareActivity.AT_FRIENDS;

public class ReplyActivity extends BaseActivity implements CommentContract.View, View.OnClickListener {

    private CommentContract.Presenter presenter;
    private Oauth2AccessToken token;

    private EditText comment;
    private ImageView atIcon;
    private ImageView sengIcon;

    private CommentsBean commentsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Intent intent = getIntent();
        String json = intent.getStringExtra(IntentKey.COMMENT_CONTENT);
        if (json != null) {
            commentsBean = new Gson().fromJson(json, CommentsBean.class);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("回复" + commentsBean.getUser().getName());
        }
        presenter = new CommentPresenter(this);
        token = AccessTokenKeeper.readAccessToken(this);

        comment = (EditText) findViewById(R.id.comment);
        atIcon = (ImageView) findViewById(R.id.at_icon);
        sengIcon = (ImageView) findViewById(R.id.send_icon);
        atIcon.setOnClickListener(this);
        sengIcon.setOnClickListener(this);
    }

    @Override
    public void onSuccess(Object object) {
        Toast.makeText(this, "回复成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(this, "访问次数已用完,回复失败", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_icon:
                presenter.reply(token.getToken(), commentsBean.getId(),
                        commentsBean.getStatus().getId(), comment.getText().toString());
                Log.e("回复：", commentsBean.getId() + "|" + commentsBean.getStatus().getId() + "|" + comment.getText().toString());
                break;
            case R.id.at_icon:
                startActivityForResult(new Intent(ReplyActivity.this, AtActivity.class), AT_FRIENDS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AT_FRIENDS:
                if (resultCode == RESULT_OK) {
                    String friends = data.getStringExtra(IntentKey.AT_FRIEND);
                    SpannableStringBuilder highlight = TextColorTools.highlight(comment.getText() + friends, friends);
                    comment.setText(highlight);
                }
                break;
            default:
                break;
        }
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
