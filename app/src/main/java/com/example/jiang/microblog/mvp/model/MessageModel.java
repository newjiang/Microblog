package com.example.jiang.microblog.mvp.model;

import android.content.Context;

import com.example.jiang.microblog.bean.Message;
import com.example.jiang.microblog.mvp.contract.MessageContract;
import com.example.jiang.microblog.net.RetrofitHelper;
import com.example.jiang.microblog.net.service.MessageService;

import rx.Observable;

/**
 * Created by jiang on 2018/4/22.
 */
public class MessageModel implements MessageContract.Model {

    private MessageService messageService;

    public MessageModel(Context context) {
        this.messageService = RetrofitHelper.getInstance(context).getMessageService();
    }

    @Override
    public Observable<Message> unread_count(String access_token, String uid) {
        return messageService.unread_count(access_token, uid);
    }
}
