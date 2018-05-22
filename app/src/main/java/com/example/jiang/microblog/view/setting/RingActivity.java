package com.example.jiang.microblog.view.setting;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.setting.adapter.SettingAdapter;
import com.zhy.changeskin.SkinManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RingActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView back;
    private TextView sure;

    private List<String> list;
    private int split;
    private int position;
    private String ringName;

    private SettingAdapter adapter;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        list = new ArrayList<>();
        initData();
        player = new MediaPlayer();
        back = (TextView) findViewById(R.id.back);
        sure = (TextView) findViewById(R.id.sure);
        back.setOnClickListener(this);
        sure.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.ring_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SettingAdapter(this, list, position, split);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new SettingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(String name) {
                ringName = name;
                Uri uri = getUri(name);
                Log.e("uri", uri.toString());
                playRingtone(uri);
            }
            private void playRingtone(Uri uri) {
                try{
                    player.reset();
                    player.setDataSource(RingActivity.this, uri);
                }catch(Exception e){
                    e.printStackTrace();
                }
                final AudioManager audioManager = (AudioManager) RingActivity.this.getSystemService(Context.AUDIO_SERVICE);
                if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM)!=0){
                    player.setAudioStreamType(AudioManager.STREAM_ALARM);
                    player.setLooping(false);
                    try {
                        player.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.start();
                }
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        player.stop();
                    }
                });
                player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Toast.makeText(RingActivity.this, "该铃声播放不了哦", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });
    }
    private void initData() {
        String ringName = getIntent().getStringExtra(IntentKey.RING);
        list.add("default");
        list.addAll(getSystemRingtones());
        split = list.size();
        list.add("应用铃声");
        for (int i = 0; i < 23; i++) {
            list.add("ring" + i);
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(ringName)) {
                position = i;
            }
        }
    }

    private List<String> getSystemRingtones() {
        List<String> list = new ArrayList<>();
        String path = File.separator + "system"
                + File.separator + "media"
                + File.separator + "audio"
                + File.separator + "ringtones";
        File dir = new File(path);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                list.add(f.toString());
            }
            return list;
        } else {
            return null;
        }
    }



    private Uri getUri(String name) {
        Uri uri = Uri.parse(name);
        if (name.equals("default"))
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(name.equals( "ring1"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring1);
        if(name.equals( "ring2"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring2);
        if(name.equals( "ring3"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring3);
        if(name.equals( "ring4"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring4);
        if(name.equals( "ring5"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring5);
        if(name.equals( "ring6"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring6);
        if(name.equals( "ring7"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring7);
        if(name.equals( "ring8"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring8);
        if(name.equals( "ring9"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring9);
        if(name.equals( "ring10"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring10);
        if(name.equals( "ring11"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring11);
        if(name.equals( "ring12"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring12);
        if(name.equals( "ring13"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring13);
        if(name.equals( "ring14"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring14);
        if(name.equals( "ring15"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring15);
        if(name.equals( "ring16"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring16);
        if(name.equals( "ring17"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring17);
        if(name.equals( "ring18"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring18);
        if(name.equals( "ring19"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring19);
        if(name.equals( "ring20"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring20);
        if(name.equals( "ring21"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring21);
        if(name.equals( "ring22"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring22);
        if(name.equals( "ring23"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring23);
        if(name.equals( "ring24"))
            uri = Uri.parse("android.resource://" + RingActivity.this.getPackageName() + "/" + R.raw.ring24);
        return uri;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        player = null;
        SkinManager.getInstance().unregister(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.sure:
                intent.putExtra(IntentKey.RETURN_RING,ringName);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }

    }

}
