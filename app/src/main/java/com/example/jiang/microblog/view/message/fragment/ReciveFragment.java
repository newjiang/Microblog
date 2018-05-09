package com.example.jiang.microblog.view.message.fragment;

import android.util.Log;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;

/**
 * Created by jiang on 2018/4/14.
 */

public class ReciveFragment extends BaseFragment implements MicroblogContract.View {
    private static final String TAG = ReciveFragment.class.getSimpleName();
    @Override
    public View initView() {
        Log.e(TAG, TAG + "...view...init...");
        View view = View.inflate(context, R.layout.fragment_recive_comments, null);
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, TAG + "...data...init...");
    }
    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }
}
