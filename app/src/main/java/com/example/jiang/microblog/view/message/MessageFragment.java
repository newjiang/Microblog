package com.example.jiang.microblog.view.message;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.utils.IntentKey;

/**
 * Created by jiang on 2018/4/14.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = MessageFragment.class.getSimpleName();

    private LinearLayout layoutAtMeWeibos;
    private LinearLayout layoutAtMeCommends;
    private LinearLayout layoutReceiveCommends;
    private LinearLayout layoutSendCommends;

    private TextView weibo;
    private TextView commends;
    private TextView receive;
    private TextView send;

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
        super.initData();
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
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(IntentKey.MESSAGE_FRAGMENT_INDEX, index);
        context.startActivity(intent);
    }

}
