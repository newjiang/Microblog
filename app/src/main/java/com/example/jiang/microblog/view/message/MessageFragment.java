package com.example.jiang.microblog.view.message;

import android.util.Log;
import android.view.View;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;

/**
 * Created by jiang on 2018/4/14.
 */

public class MessageFragment extends BaseFragment implements MicroblogContract.View {
    private static final String TAG = MessageFragment.class.getSimpleName();
    @Override
    public View initView() {
        Log.e(TAG, "MessageFragment...view...init...");
        View view = View.inflate(context, R.layout.fragment_message, null);
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "MessageFragment...data...init...");
    }
    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }
}
