package com.example.jiang.microblog.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.view.message.MessageActivity;

public class PollingService extends Service {

    public PollingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        show();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 1000 * 5;   //TODO 25秒
        long triggerTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this, PollingService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
    private void show() {
        Intent intent = new Intent(this, MessageActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //音频uri
        String uri = "android.resource://" + this.getPackageName() + "/" + R.raw.doge;

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("这个是标题")
                .setContentText("我是内容啊啊啊")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentIntent(pi)
                .setSound(Uri.parse(uri))
                .setVibrate(new long[]{50, 100, 200, 300})
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .build();

        manager.notify(1, notification);
    }
}
