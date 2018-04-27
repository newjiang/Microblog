package com.example.jiang.microblog.view.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.utils.IntentKey;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        final String key = intent.getStringExtra(IntentKey.SEARCH_CONTENT);
        textView = (TextView) findViewById(R.id.jjjjj);
        button = (Button) findViewById(R.id.bbbbb);
        textView.setText(key);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url("http://s.weibo.com/weibo/hhhhhh")
                                    .build();

                            Response response = client.newCall(request).execute();
                            String string = response.body().string();
                            System.out.println(string);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        });
    }
}
