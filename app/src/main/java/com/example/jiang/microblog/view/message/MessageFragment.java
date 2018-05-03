package com.example.jiang.microblog.view.message;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;

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
                Toast.makeText(context, "@我的微博", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_at_me_commends:
                Toast.makeText(context, "@我的评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_receive_commends:
                Toast.makeText(context, "收到的评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_send_commends:
                Toast.makeText(context, "发出的评论", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

    }
}
