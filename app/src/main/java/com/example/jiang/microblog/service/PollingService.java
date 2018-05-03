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
        int timr = 1000 * 10;   //TODO 十秒
        long triggerTime = SystemClock.elapsedRealtime() + timr;
        Intent i = new Intent(this, PollingService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void show() {
        Toast.makeText(this, "定时", Toast.LENGTH_SHORT).show();
    }
}
