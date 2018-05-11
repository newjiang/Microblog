package com.example.jiang.microblog.view.message;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.broadcast.MessageReceiver;
import com.example.jiang.microblog.utils.IntentKey;

/**
 * Created by jiang on 2018/4/14.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener, MessageReceiver.OnMessageListener {

    private static final String TAG = MessageFragment.class.getSimpleName();

    private LinearLayout layoutAtMeWeibos;
    private LinearLayout layoutAtMeCommends;
    private LinearLayout layoutReceiveCommends;
    private LinearLayout layoutSendCommends;

    private TextView weibo;
    private TextView commends;
    private TextView receive;
    private TextView send;

    private MessageReceiver messageReceiver;

    @Override

    public View initView() {
        View v = View.inflate(context, R.layout.fragment_message, null);

        layoutAtMeWeibos = (LinearLayout) v.findViewById(R.id.layout_at_me_weibos);
        layoutAtMeCommends = (LinearLayout) v.findViewById(R.id.layout_at_me_commends);
        layoutReceiveCommends = (LinearLayout) v.findViewById(R.id.layout_receive_commends);
        layoutSendCommends = (LinearLayout) v.findViewById(R.id.layout_send_commends);

        weibo = (TextView) v.findViewById(R.id.at_me_weibos);
        commends = (TextView) v.findViewById(R.id.at_me_commends);
        receive = (TextView) v.findViewById(R.id.receive_commends);
        send = (TextView) v.findViewById(R.id.send_commends);

        layoutAtMeWeibos.setOnClickListener(this);
        layoutAtMeCommends.setOnClickListener(this);
        layoutReceiveCommends.setOnClickListener(this);
        layoutSendCommends.setOnClickListener(this);

        return v;
    }

    @Override
    public void initData() {
        //注册广播接收器
        messageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.jiang.microblog.MESSAGE_RECEIVER");
        context.registerReceiver(messageReceiver, intentFilter);
        messageReceiver.setOnMessageListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_at_me_weibos:
                startMessageActivity(0);
                break;
            case R.id.layout_at_me_commends:
                startMessageActivity(1);
                break;
            case R.id.layout_receive_commends:
                startMessageActivity(2);
                break;
            case R.id.layout_send_commends:
                startMessageActivity(3);
                break;
        }
    }

    private void startMessageActivity(int index) {
        switch (index) {
            case 0:
                weibo.setVisibility(View.GONE);
                break;
            case 1:
                commends.setVisibility(View.GONE);
                break;
            case 2:
                receive.setVisibility(View.GONE);
                break;
        }
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(IntentKey.MESSAGE_FRAGMENT_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(messageReceiver);
    }

    @Override
    public void onMessageListener(int status, int follower, int cmt, int mention_status, int mention_cmt) {
        Log.e("MessageFragment", status + "|" + follower + "|" + cmt + "|" + mention_status + "|" + mention_cmt);

        if (mention_status > 0) {
            if (mention_status > 99) {
                weibo.setText("..");
            } else {
                weibo.setText(String.valueOf(mention_status));
            }
            weibo.setVisibility(View.VISIBLE);
        } else {
            weibo.setVisibility(View.GONE);
        }
        if (mention_cmt > 0) {
            if (mention_cmt > 99) {
                commends.setText("..");
            } else {
                commends.setText(String.valueOf(mention_cmt));
            }
            commends.setVisibility(View.VISIBLE);
        } else {
            commends.setVisibility(View.GONE);
        }
        if (cmt > 0) {
            if (cmt > 99) {
                receive.setText("..");
            } else {
                receive.setText(String.valueOf(cmt));
            }
            receive.setVisibility(View.VISIBLE);
        } else {
            receive.setVisibility(View.GONE);
        }
    }
}
