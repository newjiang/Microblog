package com.example.jiang.microblog.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jiang.microblog.R;

public class NotificationActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        button = (Button) findViewById(R.id.notification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, NotificationContentActivity.class);
                PendingIntent pi = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //音频uri
                String uri = "android.resource://" + NotificationActivity.this.getPackageName() + "/" + R.raw.doge;
                Uri parse = Uri.parse(uri);
                Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
                        .setContentTitle("这个是标题")
                        .setContentText("我是内容啊啊啊")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                        .setContentIntent(pi)
                        .setSound(Uri.parse(uri))
                        .setVibrate(new long[]{0, 100, 0, 100})
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);
            }
        });
    }
}
