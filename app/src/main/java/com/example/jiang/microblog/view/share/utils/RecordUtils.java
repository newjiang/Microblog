package com.example.jiang.microblog.view.share.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by jiang on 2018/5/6.
 */

public class RecordUtils implements SurfaceHolder.Callback {
    private static final String TAG = "RecordUtils";
    public static final int MEDIA_AUDIO = 0;//TODO 音频
    public static final int MEDIA_VIDEO = 1;//TODO 视频
    private Activity activity;
    private MediaRecorder mediaRecorder;
    private CamcorderProfile profile;
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private File targetDir;                         //TODO 视频文件目录
    private String targetName;                      //TODO 视频文件名
    private File targetFile;                        //TODO 视频文件
    private int previewWidth, previewHeight;      //TODO 视频分辨率
    private int recorderType;
    private boolean isRecording;        //TODO 是否在录制
    private GestureDetector detector;
    private boolean isZoomIn = false;   //TODO 是否放大
    private int or = 90;
    private int cameraPosition = 1;     //TODO 0=前置摄像头，1=后置摄像头

    public RecordUtils(Activity activity) {
        this.activity = activity;
    }

    public void setRecorderType(int type) {
        this.recorderType = type;
    }

    public void setTargetDir(File file) {
        this.targetDir = file;
    }

    public void setTargetName(String name) {
        this.targetName = name;
    }

    public String getTargetFilePath() {
        return targetFile.getPath();
    }
    public int getPreviewWidth() {
        return previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public boolean deleteTargetFile() {
        if (targetFile.exists()) {
            return targetFile.delete();
        } else {
            return false;
        }
    }

    /**
     * 设置SurfaceView
     * @param view
     */
    public void setSurfaceView(SurfaceView view) {
        this.surfaceView = view;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setFixedSize(previewWidth, previewHeight);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);
        detector = new GestureDetector(activity, new ZoomGestureListener());
        //TODO 监听触动事件
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void record() {
        if (isRecording) {
            try {
                mediaRecorder.stop();  //TODO 停止录制
            } catch (RuntimeException e) {
                Log.d(TAG, "RuntimeException: stop() is called immediately after start()");
                targetFile.delete();
            }
            releaseMediaRecorder(); //TODO 释放MediaRecorder资源
            camera.lock();
            isRecording = false;
        } else {
            startRecord();
        }
    }

    /**
     * 初始化MediaRecorder参数
     * @return
     */
    private boolean prepareRecord() {
        try {
            mediaRecorder = new MediaRecorder();
            if (recorderType == MEDIA_VIDEO) {
                //TODO 录制视频
                camera.unlock();
                mediaRecorder.setCamera(camera);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setProfile(profile);
                //TODO 实际视屏录制后的方向
                if(cameraPosition == 0){
                    mediaRecorder.setOrientationHint(270);
                }else {
                    mediaRecorder.setOrientationHint(or);
                }
            } else if (recorderType == MEDIA_AUDIO) {
                //TODO 录制音频
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            }
            targetFile = new File(targetDir, targetName);
            mediaRecorder.setOutputFile(targetFile.getPath());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MediaRecorder", "Exception prepareRecord: ");
            releaseMediaRecorder();
            return false;
        }
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.e("MediaRecorder", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.e("MediaRecorder", "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    /**
     * 停止录制视频并保存视频
     */
    public void stopRecordSave() {
        if (isRecording) {
            isRecording = false;
            try {
                mediaRecorder.stop();
            } catch (RuntimeException r) {
                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
            } finally {
                releaseMediaRecorder();
            }
        }
    }

    /**
     * 停止录制视频但是不保存视频
     */
    public void stopRecordUnSave() {
        if (isRecording) {
            isRecording = false;
            try {
                mediaRecorder.stop();
            } catch (RuntimeException r) {
                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
                if (targetFile.exists()) {
                    //TODO　不保存直接删掉
                    targetFile.delete();
                }
            } finally {
                releaseMediaRecorder();
            }
            if (targetFile.exists()) {
                //不保存直接删掉
                targetFile.delete();
            }
        }
    }

    /**
     * 开启预览
     * @param holder
     */
    private void startPreView(SurfaceHolder holder) {
        if (camera == null) {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (camera != null) {
            camera.setDisplayOrientation(or);
            try {
                camera.setPreviewDisplay(holder);
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
                List<Camera.Size> mSupportedVideoSizes = parameters.getSupportedVideoSizes();
                Camera.Size optimalSize = CameraHelper.getOptimalVideoSize(mSupportedVideoSizes,
                        mSupportedPreviewSizes, surfaceView.getWidth(), surfaceView.getHeight());
                previewWidth = optimalSize.width;
                previewHeight = optimalSize.height;
                parameters.setPreviewSize(previewWidth, previewHeight);
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

                // 这里是重点，分辨率和比特率
                // 分辨率越大视频大小越大，比特率越大视频越清晰
                // 清晰度由比特率决定，视频尺寸和像素量由分辨率决定
                // 比特率越高越清晰（前提是分辨率保持不变），分辨率越大视频尺寸越大。
                profile.videoFrameWidth = optimalSize.width;
                profile.videoFrameHeight = optimalSize.height;
                // 这样设置 1080p的视频 大小在5M , 可根据自己需求调节
                profile.videoBitRate = 2 * optimalSize.width * optimalSize.height;
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

    /**
     * 释放mediaRecorder资源
     */
    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            //TODO 重置 mediaRecorder配置
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    /**
     * 释放camera资源
     */
    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        startPreView(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            releaseCamera();
        }
        if (mediaRecorder != null) {
            releaseMediaRecorder();
        }
    }

    /**
     * 开始录制
     */
    private void startRecord() {
        if (prepareRecord()) {
            try {
                mediaRecorder.start();
                isRecording = true;
            } catch (RuntimeException r) {
                releaseMediaRecorder();
            }
        }
    }

    //TODO 双击手势事件类
    private class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            if (!isZoomIn) {
                setZoom(20);
                isZoomIn = true;
            } else {
                setZoom(0);
                isZoomIn = false;
            }
            return true;
        }
    }

    private void setZoom(int zoomValue) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.isZoomSupported()) {
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

    private String getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return bitmap2File(media.getFrameAtTime());
    }

    private String bitmap2File(Bitmap bitmap) {
        File thumbFile = new File(targetDir,
                targetName);
        if (thumbFile.exists()) thumbFile.delete();
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(thumbFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return thumbFile.getAbsolutePath();
    }

    public void switchCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    startPreView(surfaceHolder);
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    startPreView(surfaceHolder);
                    cameraPosition = 1;
                    break;
                }
            }
        }
    }
}
