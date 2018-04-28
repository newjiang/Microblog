package com.example.jiang.microblog.view.message;

import android.view.View;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;

/**
 * Created by jiang on 2018/4/14.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener, MicroblogContract.View {
    private static final String TAG = MessageFragment.class.getSimpleName();

    private TextView weibo;
    private TextView commends;
    private TextView receive;
    private TextView send;

    @Override
    public View initView() {
        View v = View.inflate(context, R.layout.fragment_message, null);
        weibo = (TextView) v.findViewById(R.id.at_me_weibo);
        commends = (TextView) v.findViewById(R.id.at_me_commends);
        receive = (TextView) v.findViewById(R.id.receive_commends);
        send = (TextView) v.findViewById(R.id.send_commends);
        return v;
    }
    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }

}
