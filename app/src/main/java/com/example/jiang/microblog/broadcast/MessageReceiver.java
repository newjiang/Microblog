package com.example.jiang.microblog.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jiang.microblog.bean.Message;
import com.example.jiang.microblog.utils.IntentKey;
import com.google.gson.Gson;

public class MessageReceiver extends BroadcastReceiver {

    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getStringExtra(IntentKey.BROADCAST);
        Message message = new Gson().fromJson(extra, Message.class);
        if (onMessageListener != null) {
            if (message != null) {
                //发送消息
                onMessageListener.onMessageListener(message.getStatus(), message.getFollower()
                        , message.getCmt(), message.getMention_status(), message.getMention_cmt());
            }
        }
    }


    /**
     * 消息监听器
     */
    public interface OnMessageListener {
        void onMessageListener(int status, int follower, int cmt, int mention_status, int mention_cmt);
    }

    private OnMessageListener onMessageListener;

    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.onMessageListener = onMessageListener;
    }
}
