package com.example.jiang.microblog.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

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
        int timr = 1000 * 10;
        long triggerTime = SystemClock.elapsedRealtime() + timr;
        Intent i = new Intent(this, PollingService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void show() {
        Toast.makeText(this, "定时", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(PollingService.this, NotificationContentActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(PollingService.this, 0, intent, 0);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        //音频uri
//        String uri = "android.resource://" + PollingService.this.getPackageName() + "/" + R.raw.doge;
//
//        Notification notification = new NotificationCompat.Builder(PollingService.this)
//                .setContentTitle("这个是标题")
//                .setContentText("我是内容啊啊啊")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.logo)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
//                .setContentIntent(pi)
//                .setSound(Uri.parse(uri))
//                .setVibrate(new long[]{0, 100, 0, 100})
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .build();
//        manager.notify(1, notification);
    }
}
