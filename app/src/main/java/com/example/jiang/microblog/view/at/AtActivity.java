package com.example.jiang.microblog.view.at;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.mvp.contract.UserContract;
import com.example.jiang.microblog.view.at.adapter.FriendAdapter;
import com.example.jiang.microblog.view.at.adapter.HeaderAdapter;

import java.util.List;

public class AtActivity extends AppCompatActivity implements UserContract.View {

    private UserContract.Presenter presenter;

    private RecyclerView friendRecyclerView;
    private RecyclerView headerRecyclerView;

    private FriendAdapter friendAdapter;
    private HeaderAdapter headerAdapter;

    private List<User> users;
    private List<User> headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at);
//        presenter.
        initFriendList();
        initHeaders();
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }

    private void initHeaders() {
        headerRecyclerView = (RecyclerView) findViewById(R.id.friend_header);
        headerAdapter = new HeaderAdapter(AtActivity.this, headers);
        friendRecyclerView.setAdapter(friendAdapter);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(AtActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }


    private void initFriendList() {
        friendRecyclerView = (RecyclerView) findViewById(R.id.friend_recyclerview);
        friendAdapter = new FriendAdapter(AtActivity.this, users);
        friendRecyclerView.setAdapter(friendAdapter);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(AtActivity.this));
    }
}
