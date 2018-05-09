package com.example.jiang.microblog.view.search.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.search.activity.ResultActivity;
import com.example.jiang.microblog.view.search.adapter.AccountAdapter;

import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class AccountFragment extends BaseFragment {

    private static final String TAG = AccountFragment.class.getSimpleName();

    private RecyclerView userRecyclerview;
    private ProgressBar userBar;
    private List<Account> accounts;
    private AccountAdapter accountAdapter;
    private boolean isFinish = false;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_account, null);
        userRecyclerview = (RecyclerView)view.findViewById(R.id.user_recyclerview);
        userBar = (ProgressBar) view.findViewById(R.id.user_bar);
        userBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void initData() {

        final String key = (String) getArguments().get(IntentKey.KEY_WORD);
        Log.e("AccountFragment", key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    accounts = CrawlerTools.findUser(key);
                    isFinish = true;
                    ResultActivity activity = (ResultActivity) context;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initUserView();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void initUserView() {
        if (isFinish) {
            userBar.setVisibility(View.GONE);
            accountAdapter = new AccountAdapter(context, accounts);
            userRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            userRecyclerview.setAdapter(accountAdapter);
        } else {
            userBar.setVisibility(View.VISIBLE);
        }
    }
}
