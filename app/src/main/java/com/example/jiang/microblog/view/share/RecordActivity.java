package com.example.jiang.microblog.view.share;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.share.utils.FileUtil;
import com.example.jiang.microblog.view.share.utils.RecordUtils;

import java.io.File;
import java.util.UUID;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener{

    TextView recordTip;
    SurfaceView surfaceView;
    RecordUtils recordUtils;
    int maxTime = 10;//TODO 录制最大时间
    int recordTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recordTip = (TextView) findViewById(R.id.record_tip);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        recordUtils = new RecordUtils(this);
        recordUtils.setRecorderType(RecordUtils.MEDIA_VIDEO);
        recordUtils.setTargetDir(FileUtil.getExternalStorageDirectory(this));
        recordUtils.setTargetName(UUID.randomUUID() + ".mp4");
        recordUtils.setSurfaceView(surfaceView);
        recordTip.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.record_tip){
            if(!recordUtils.isRecording()) {
                handler.postDelayed(run,1000);
                recordUtils.record();
                recordTip.setText(maxTime +"s");
            }else{
                handler.removeCallbacks(run);
                if(recordTime < 3){
                    recordTime = 0;
                    recordUtils.stopRecordUnSave();
                    recordTip.setText("录制");
                    Toast.makeText(this,"时间太短",Toast.LENGTH_SHORT).show();
                } else{
                    recordTime = 0;
                    recordUtils.stopRecordSave();
                    recordTip.setText("录制");
                    returnPath();
                    Toast.makeText(this,"保存地址："+ recordUtils.getTargetFilePath(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                if(recordTime == 10){
                    recordTime = 0;
                    recordUtils.stopRecordSave();
                    recordTip.setText("录制");
                    returnPath();
                    Toast.makeText(RecordActivity.this,"保存地址："+ recordUtils.getTargetFilePath(),Toast.LENGTH_SHORT).show();
                    return;
                }
                recordTime++;
                recordTip.setText(maxTime - recordTime + "s");
                handler.postDelayed(run,1000);
            }
        }
    };

    private void returnPath() {
        Intent intent = new Intent();
        intent.putExtra(IntentKey.VIDEO_PATH, recordUtils.getTargetFilePath());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        String path = recordUtils.getTargetFilePath();
        boolean exists = new File(path).exists();
        if (exists) {
            returnPath();
        } else {
            Intent intent = new Intent();
            intent.putExtra(IntentKey.VIDEO_PATH, "cancel");
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

}
