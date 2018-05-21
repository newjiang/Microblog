package com.example.jiang.microblog.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Message;
import com.example.jiang.microblog.bean.Setting;
import com.example.jiang.microblog.broadcast.MessageReceiver;
import com.example.jiang.microblog.mvp.contract.MessageContract;
import com.example.jiang.microblog.mvp.presenter.MessagePresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.message.MessageActivity;
import com.example.jiang.microblog.view.profile.ProfileActivity;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PollingService extends Service implements MessageContract.View {

    private static final int STATUS = 1;        //TODO 未读微博
    private static final int FOLLOWER = 2;      //TODO 新粉丝
    private static final int CMT = 3;            //TODO 新评论
    private static final int MENTION_STATUS = 4;//TODO 新提及我的微博数
    private static final int MENTION_CMT = 5;   //TODO 新提及我的评论数
    private LocalBroadcastManager broadcastManager;

    private Oauth2AccessToken token;
    private MessagePresenter presenter;
    private Setting setting;

    private Message message;
    private MessageReceiver receiver;

    public PollingService() {
        Log.e("PollingService", "PollingService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("IBinder", "IBinder");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate", "onCreate");
        token = AccessTokenKeeper.readAccessToken(this);
        List<Setting> settings = DataSupport.where("uid = ?", token.getUid()).find(Setting.class);
        if (settings.isEmpty()) {
            setting = new Setting(true, true, true, true, "default", "default", token.getUid());
            setting.save();
        } else {
            setting = settings.get(0);
        }
        presenter = new MessagePresenter(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("请求－－－－", token.getToken() + "|" + token.getUid());
        presenter.unread_count(token.getToken(), token.getUid());

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 1000 * 2000;   //TODO 请求间隔时间300秒
        long triggerTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this, PollingService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSuccess(Object object) {
        Log.e("PollingService", "onSuccess");
        message = (Message) object;
        //TODO 注册发送广播
        Intent broadcast = new Intent("com.example.jiang.microblog.MESSAGE_RECEIVER");
        broadcast.putExtra(IntentKey.BROADCAST, new Gson().toJson(message));
        sendBroadcast(broadcast);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.jiang.microblog.MESSAGE_RECEIVER");
        receiver = new MessageReceiver();
        registerReceiver(receiver, filter);
        if (setting.isNotification()) {
            if (message.getFollower() > 0) {
                show(FOLLOWER);
            }
            if (message.getCmt() > 0) {
                show(CMT);
            }
            if (message.getMention_status() > 0) {
                show(MENTION_STATUS);
            }
            if (message.getMention_cmt() > 0) {
                show(MENTION_CMT);
            }
        }
    }

    @Override
    public void onError(String result) {
        Log.e("PollingService-E", result);
    }

    private void show(int type) {
        Intent intent = null;
        //TODO　判断是否是新粉丝消息
        if (type == 2) {
            intent = new Intent(this, ProfileActivity.class);
        } else {
            intent = new Intent(this, MessageActivity.class);
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //TODO 处理声音
        if (setting.isRing()) {
            builder.setSound(getUri(setting.getRing()));
        }

        //TODO 处理震动
        if (setting.isVibrate()) {
            builder.setVibrate(new long[]{50, 100, 50, 100});
        } else {
            builder.setVibrate(new long[]{0, 0, 0, 0});
        }

        //TODO 设置标题
        if (type == 2)
            builder.setContentTitle("你有" + message.getFollower() + "个新粉丝");
        if (type == 3)
            builder.setContentTitle("你有" + message.getCmt() + "条评论");
        if (type == 4)
            builder.setContentTitle("你有" + message.getMention_status() + "条@你的微博");
        if (type == 5)
            builder.setContentTitle("你有" + message.getMention_cmt() + "条@你的评论");

        builder.setContentText("点击查看消息内容");
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        builder.setContentIntent(pi);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        manager.notify(type, notification);
    }

    private Uri getUri(String name) {
        Uri uri = Uri.parse(name);
        if (name.equals("default"))
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (name.equals("ring1"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring1);
        if (name.equals("ring2"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring2);
        if (name.equals("ring3"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring3);
        if (name.equals("ring4"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring4);
        if (name.equals("ring5"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring5);
        if (name.equals("ring6"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring6);
        if (name.equals("ring7"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring7);
        if (name.equals("ring8"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring8);
        if (name.equals("ring9"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring9);
        if (name.equals("ring10"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring10);
        if (name.equals("ring11"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring11);
        if (name.equals("ring12"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring12);
        if (name.equals("ring13"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring13);
        if (name.equals("ring14"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring14);
        if (name.equals("ring15"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring15);
        if (name.equals("ring16"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring16);
        if (name.equals("ring17"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring17);
        if (name.equals("ring18"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring18);
        if (name.equals("ring19"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring19);
        if (name.equals("ring20"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring20);
        if (name.equals("ring21"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring21);
        if (name.equals("ring22"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring22);
        if (name.equals("ring23"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring23);
        if (name.equals("ring24"))
            uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ring24);
        return uri;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
