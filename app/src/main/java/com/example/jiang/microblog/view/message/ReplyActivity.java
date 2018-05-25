package com.example.jiang.microblog.view.message;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    private RelativeLayout linearLayout;
    private EditText comment;
    private ImageView atIcon;
    private CheckBox isRetweeted;
    private ImageView sengIcon;

    private CommentsBean commentsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Intent intent = getIntent();
        String extra = intent.getStringExtra(IntentKey.COMMENT_CONTENT);
        if (extra != null) {
            commentsBean = new Gson().fromJson(extra, CommentsBean.class);
        }
        presenter = new CommentPresenter(this);
        token = AccessTokenKeeper.readAccessToken(this);

        linearLayout = (RelativeLayout) findViewById(R.id.activity_reply);
        comment = (EditText) findViewById(R.id.comment);
        atIcon = (ImageView) findViewById(R.id.at_icon);
        sengIcon = (ImageView) findViewById(R.id.send_icon);
        isRetweeted = (CheckBox) findViewById(R.id.is_retweeted);
        sengIcon.setOnClickListener(this);
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_icon:
                if (isRetweeted.isChecked()) {
                    presenter.reply(token.getToken(), commentsBean.getId(),
                            commentsBean.getStatus().getId(), comment.getText().toString(), 1, 0);
                } else {
                    presenter.reply(token.getToken(), commentsBean.getId(),
                            commentsBean.getStatus().getId(), comment.getText().toString(), 1, 1);
                }
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
}
