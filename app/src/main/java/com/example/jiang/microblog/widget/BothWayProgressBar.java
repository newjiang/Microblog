package com.example.jiang.microblog.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jiang on 2018/5/3.
 */

public class BothWayProgressBar extends View {

    private Context context;

    private boolean isCancel = false;//TODO 是否取消

    private Paint recordPaint;  //TODO 录制的画笔
    private Paint cancelPaint;  //TODO 取消的画笔

    private int visibility;     //TODO 显示
    private int progress;       //TODO　进度

    private OnProgressEndListener onProgressEndListener;

    public BothWayProgressBar(Context context) {
        super(context, null);
    }

    public BothWayProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public interface OnProgressEndListener {
        void onProgressEndListener();
    }

    private void init() {
        visibility = View.INVISIBLE;
        recordPaint = new Paint();
        cancelPaint = new Paint();
        recordPaint.setColor(Color.GREEN);//TODO 录制时显示绿色
        cancelPaint.setColor(Color.RED);  //TODO 取消时显示红色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (visibility == View.VISIBLE) {
            int h = getHeight();//TODO 获取高度
            int w = getWidth(); //TODO 获取宽度
            int m = w / 2;      //TODO 获取中间值
            if (progress < m) {
                //TODO 画出进度条
                canvas.drawRect(progress, 0, w - progress, h, isCancel ? cancelPaint : recordPaint);
            } else {
                if (onProgressEndListener != null) {
                    onProgressEndListener.onProgressEndListener();
                }
            }
        } else {
            //TODO 黑色
            canvas.drawColor(Color.argb(0, 0, 0, 0));
        }
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * 设置录制状态是否取消
     *
     * @param isCancel
     */
    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
        invalidate();
    }

    /**
     * 设置是否可见
     *
     * @param visibility
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
        invalidate();
    }

    /**
     * 对进度条结束后进行监听
     *
     * @param onProgressEndListener
     */
    public void setOnProgressEndListener(OnProgressEndListener onProgressEndListener) {
        this.onProgressEndListener = onProgressEndListener;
    }
}
