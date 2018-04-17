package com.example.jiang.microblog.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by jiang on 2018/4/14.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * 启动Activity添加到ActivityController中
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("BaseActivity——add",getClass().getSimpleName());
        ActivityController.add(this);
    }

    /**
     * 结束Activity从ActivityController中移除
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("BaseActivity——delete",getClass().getSimpleName());
        ActivityController.remove(this);
    }

}



