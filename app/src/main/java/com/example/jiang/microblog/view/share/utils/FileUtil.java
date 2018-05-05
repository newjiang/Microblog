package com.example.jiang.microblog.view.share.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by jiang on 2018/5/6.
 */

public class FileUtil {

    public static File getExternalStorageDirectory(Context context) {
        File cacheDir = null;
        //TODO 存储媒体已经挂载?
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //TODO 视频存储根目录下
            cacheDir = new File(Environment.getExternalStorageDirectory(), "/microblog/video/");
        } else {
            cacheDir = new File(context.getFilesDir(), Environment.DIRECTORY_PICTURES);
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }
}
