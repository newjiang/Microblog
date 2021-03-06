package com.example.jiang.microblog.view.share.at;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Friend;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.mvp.presenter.UserPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.share.at.adapter.FriendAdapter;
import com.example.jiang.microblog.view.share.at.adapter.HeaderAdapter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class AtActivity extends AppCompatActivity implements UserContract.View {

    private UserContract.Presenter presenter;

    private RecyclerView friendRecyclerView;
    private RecyclerView headerRecyclerView;

    private FriendAdapter friendAdapter;
    private HeaderAdapter headerAdapter;

    private List<User> users = new ArrayList<>();
    private List<User> headers = new ArrayList<>();
    private Oauth2AccessToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at);
        token = AccessTokenKeeper.readAccessToken(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("选择@的好友");
        }
        presenter = new UserPresenter(this);
        initData();
    }

    @Override
    public void onSuccess(Object object) {
        Friend friend = (Friend) object;
        if (users.isEmpty()) {
            users = friend.getUsers();
            initFriendList();
            initHeaders();
        }
    }

    @Override
    public void onError(String result) {

    }

    private void initData() {
        if (users.isEmpty()) {
            presenter.getNextFriendList(token.getToken(), token.getUid(), 0);
        }
    }

    private void initHeaders() {
        headerRecyclerView = (RecyclerView) findViewById(R.id.friend_header);
        headerAdapter = new HeaderAdapter(AtActivity.this, headers);
        headerRecyclerView.setAdapter(headerAdapter);
        headerRecyclerView.setLayoutManager(new LinearLayoutManager(AtActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initFriendList() {
        friendRecyclerView = (RecyclerView) findViewById(R.id.friend_recyclerview);
        friendAdapter = new FriendAdapter(AtActivity.this, users);
        friendRecyclerView.setAdapter(friendAdapter);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(AtActivity.this, LinearLayoutManager.VERTICAL, false));
        friendAdapter.setClickListener(new FriendAdapter.OnItemOnClickListener() {

            @Override
            public void onItemOnClickListener(View view, int position, boolean isCheck) {
                if (isCheck) {
                    headerAdapter.add(users.get(position));
                } else {
                    headerAdapter.remove(users.get(position));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        String friends = "";
        for (int i = 0; i < headers.size(); i++) {
            friends += "@" + headers.get(i).getName() + " ";
        }
        Intent intent = new Intent();
        intent.putExtra(IntentKey.AT_FRIEND, friends);
        if (headers.isEmpty()) {
            setResult(RESULT_CANCELED, intent);
        } else {
            setResult(RESULT_OK, intent);
        }
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String friends = "";
                for (int i = 0; i < headers.size(); i++) {
                    friends += "@" + headers.get(i).getName() + " ";
                }
                Intent intent = new Intent();
                intent.putExtra(IntentKey.AT_FRIEND, friends);
                if (headers.isEmpty()) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    setResult(RESULT_OK, intent);
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
