package com.example.jiang.microblog.utils;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.example.jiang.microblog.R;

import java.io.IOException;

/**
 * Created by jiang on 2018/5/10.
 */

public class BeeAndVibrateManager {
    private static boolean shouldPlayBeep = true;

    /**
     * @param context      Context实例
     * @param milliseconds 震动时长 , 单位毫秒
     */
    public static void vibrate(Context context, long milliseconds) {

        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * @param context  Context实例
     * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]单位是毫秒
     * @param isRepeat true-> 反复震动，false-> 只震动一次
     */
    public static void vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    public static void playBee(final Context context, final PlayerCompleteListener listener) {
        AudioManager audioService = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            shouldPlayBeep = false;//检查当前是否是静音模式
        }

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                player.seekTo(0);
            }
        });

        AssetFileDescriptor file = context.getResources().openRawResourceFd(R.raw.ring1);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(),
                    file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(0, 1);
            mediaPlayer.prepare();
        } catch (IOException ioe) {
            mediaPlayer = null;
        }

        if (shouldPlayBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                listener.onCompletion(mp);
            }
        });
    }

    public static void playBeeAndVibrate(final Context context, long milliseconds, PlayerCompleteListener listener) {
        //震动
        vibrate(context, milliseconds);
        //提示音
        playBee(context, listener);
    }

    //MediaPlayer播放完毕监听
    public interface PlayerCompleteListener {
        void onCompletion(MediaPlayer mp);
    }
}
