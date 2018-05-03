package com.example.jiang.microblog.view.share;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.widget.BothWayProgressBar;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class RecordingActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        View.OnTouchListener, BothWayProgressBar.OnProgressEndListener {

    private static final int LISTENER_START = 200;

    private SurfaceView surfaceView;    //TODO 预览view
    private Camera camera;               //TODO  相机
    private View startButton;           //TODO  开始按钮
    private BothWayProgressBar progressBar; //TODO  进度条
    private Thread progressThread;          //TODO  进度条线程
    private MediaRecorder mMediaRecorder;   //TODO  录制视频
    private SurfaceHolder surfaceHolder;
    private int videoWidth;                 //TODO  屏幕分辨率宽
    private int videoHeight;                //TODO  屏幕分辨率高
    private boolean isRecording;            //TODO  是否在录制
    private File targetFile;                 //TODO 视频保存目录
    private int progress;                    //TODO 当前进度
    public static final int MAX_TIME = 15;  //TODO 最大录制时间
    private boolean isCancel;                //TODO 上滑是否取消
    private GestureDetector detector;         //TODO 手势处理
    private boolean isZoomIn = false;        //TODO 是否放大

    private MyHandler mHandler;
    private TextView textView;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_recording);
        initView();
    }

    private void initView() {
        videoWidth = 640;
        videoHeight = 480;
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        detector = new GestureDetector(this, new ZoomGestureListener());
        //TODO SurfaceView的双击事件
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setFixedSize(videoWidth, videoHeight);//TODO 设置分辨率
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);

        startButton = findViewById(R.id.progress_control);
        textView = (TextView) findViewById(R.id.main_tip);
        startButton.setOnTouchListener(this);

        progressBar = (BothWayProgressBar) findViewById(R.id.progress_bar); //TODO 双向进度条
        progressBar.setOnProgressEndListener(this);
        mHandler = new MyHandler(this);
        mMediaRecorder = new MediaRecorder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        startPreView(holder);
    }

    /**
     * 开启预览
     *
     * @param holder
     */
    private void startPreView(SurfaceHolder holder) {
        if (camera == null) {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        if (camera != null) {
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(holder);
                Camera.Parameters parameters = camera.getParameters();
                //TODO Camera自动对焦
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                camera.setParameters(parameters);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO 释放资源
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    @Override
    public void onProgressEndListener() {
        //TODO 停止视频录制
        stopRecordSave();
    }

    /**
     * 开始录制
     */
    private void startRecord() {
        if (mMediaRecorder != null) {
            //TODO 判断是否有外置储存，没有则停止录制
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            try {
                camera.unlock();
                mMediaRecorder.setCamera(camera);
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//TODO 从相机采集视频
                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//TODO 从相机采集视频
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//TODO 设置视频输出格式
                mMediaRecorder.setVideoSize(videoWidth, videoHeight);
                mMediaRecorder.setVideoFrameRate(24);//TODO 设置帧数
                mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//TODO 设置视频编码格式
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//TODO 设置音频编码格式
                mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024 * 100);//TODO 设置帧频率

                File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                targetFile = new File(targetDir, SystemClock.currentThreadTimeMillis() + ".mp4");

                mMediaRecorder.setOutputFile(targetFile.getAbsolutePath());
                mMediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                mMediaRecorder.setOrientationHint(90);//TODO 解决录制视频, 播放器横向问题
                mMediaRecorder.prepare();
                mMediaRecorder.start();//TODO 开始录制
                isRecording = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止录制 并且保存
     */
    private void stopRecordSave() {
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            Log.e("视频位置：",targetFile.getAbsolutePath());
            onBackPressed();
        }
    }

    /**
     * 停止录制, 不保存
     */
    private void stopRecordUnSave() {
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            if (targetFile.exists()) {
                targetFile.delete(); //TODO 删除文集
            }
        }
    }

    /**
     * 相机变焦
     *
     * @param zoomValue
     */
    public void setZoom(int zoomValue) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            //TODO 判断是否支持变焦
            if (parameters.isZoomSupported()) {
                //TODO 获得最大变焦
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                camera.setParameters(parameters);
            }
        }
    }

    /**
     * 触摸事件的触发
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean result = false;
        int action = event.getAction();
        float y = event.getY();
        float x = event.getX();
        int width = v.getWidth();//TODO 监听中间的按钮处
        int left = LISTENER_START;
        int right = width - LISTENER_START;
        float downY = 0;
        switch (v.getId()) {
            case R.id.progress_control: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (x > left && x < right) {
                            progressBar.setCancel(false);
                            textView.setVisibility(View.VISIBLE);//TODO 显示上滑取消
                            textView.setText("↑ 上滑取消");
                            downY = y;//TODO 记录按下的Y坐标
                            progressBar.setVisibility(View.VISIBLE);
                            startRecord();//TODO 开始录制
                            progressThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        progress = 0;
                                        isRunning = true;
                                        while (isRunning) {
                                            progress++;
                                            mHandler.obtainMessage(0).sendToTarget();
                                            Thread.sleep(20);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            progressThread.start();
                            result = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (x > left && x < right) {
                            textView.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            //TODO 判断是否为录制结束, 或者为成功录制/时间过短
                            if (!isCancel) {
                                if (progress < 50) {
                                    stopRecordUnSave();//TODO 停止录制不保存
                                    Toast.makeText(this, "时间太短", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                stopRecordSave(); //TODO 停止录制
                            } else {
                                stopRecordUnSave();//TODO 停止录制
                                isCancel = false;
                                Toast.makeText(this, "取消录制", Toast.LENGTH_SHORT).show();
                                progressBar.setCancel(false);
                            }
                            result = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (x > left && x < right) {
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                isCancel = true;
                                progressBar.setCancel(true);
                            }
                        }
                        break;
                }
                break;
            }
        }
        return result;
    }

    private static class MyHandler extends Handler {
        private WeakReference<RecordingActivity> reference;
        private RecordingActivity activity;

        public MyHandler(RecordingActivity activity) {
            reference = new WeakReference<RecordingActivity>(activity);
            this.activity = reference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    activity.progressBar.setProgress(activity.progress);
                    break;
            }
        }
    }

    //TODO 双击手势处理类
    class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            if (mMediaRecorder != null) {
                if (!isZoomIn) {
                    setZoom(20);
                    isZoomIn = true;
                } else {
                    setZoom(0);
                    isZoomIn = false;
                }
            }
            return true;
        }
    }
}
