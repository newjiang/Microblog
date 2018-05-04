package com.example.jiang.microblog.view.at;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Friend;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.json.FriendJson;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.view.at.adapter.FriendAdapter;
import com.example.jiang.microblog.view.at.adapter.HeaderAdapter;
import com.google.gson.Gson;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("选择@的好友");
        }
        initData();
        initFriendList();
        initHeaders();
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }

    private void initData() {
        Gson gson = new Gson();
        Friend friend = gson.fromJson(FriendJson.JSON, Friend.class);
        users = friend.getUsers();
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
}
