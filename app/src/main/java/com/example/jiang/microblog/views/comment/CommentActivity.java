package com.example.jiang.microblog.views.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.IntentKey;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener, DialogFragmentDataCallback{
    private TextView commentFakeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        String json = intent.getStringExtra(IntentKey.MICROBLOG_JSON);
        Log.e("MicroblogActivity", json);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        commentFakeButton = (TextView) findViewById(R.id.tv_comment_fake_button);
        commentFakeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_fake_button:
                CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
                commentDialogFragment.show(getFragmentManager(), "CommentDialogFragment");
                break;
            default:
                break;
        }
    }

    @Override
    public String getCommentText() {
        return commentFakeButton.getText().toString();
    }

    @Override
    public void setCommentText(String commentTextTemp) {
        commentFakeButton.setText(commentTextTemp);
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
