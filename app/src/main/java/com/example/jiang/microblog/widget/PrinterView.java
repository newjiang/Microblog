package com.example.jiang.microblog.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiang on 2018/5/23.
 */

public class PrinterView extends TextView {
    /**
     * 默认打字字符
     */
    private final String DEFAULT_INTERVAL_CHAR = "_";
    /**
     * 默认打字间隔时间
     */
    private final int DEFAULT_TIME_DELAY = 80;
    /**
     * 计时器
     */
    private Timer timer;
    /**
     * 需要打字的文字
     */
    private String printString;
    /**
     * 间隔时间
     */
    private int intervalTime = DEFAULT_TIME_DELAY;
    /**
     * 间隔时间
     */
    private String intervalChar = DEFAULT_INTERVAL_CHAR;
    /**
     * 打字进度
     */
    private int printProgress = 0;


    public PrinterView(Context context) {
        super(context);
    }

    public PrinterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置要打字的文字
     *
     * @param str
     */
    public void setPrintText(String str) {
        setPrintText(str, DEFAULT_TIME_DELAY);
    }

    /**
     * 设置需要打字的文字及打字间隔
     *
     * @param str  打字文字
     * @param time 打字间隔(ms)
     */
    public void setPrintText(String str, int time) {
        setPrintText(str, time, DEFAULT_INTERVAL_CHAR);
    }

    /**
     * 设置需要打字的文字,打字间隔,间隔符号
     *
     * @param str          打字文字
     * @param time         打字间隔(ms)
     * @param intervalChar 间隔符号("_")
     */
    public void setPrintText(String str, int time, String intervalChar) {
        if (strIsEmpty(str) || 0 == time || strIsEmpty(intervalChar)) {
            return;
        }
        this.printString = str;
        this.intervalTime = time;
        this.intervalChar = intervalChar;
    }

    /**
     * 开始打字
     */
    public void startPrint() {
        // 判空处理
        if (strIsEmpty(printString)) {
            if (!strIsEmpty(getText().toString())) {
                this.printString = getText().toString();
            } else {
                return;
            }
        }
        // 重置相关信息
        setText("");
        stopPrint();
        printProgress = 0;
        timer = new Timer();
        timer.schedule(new PrinterTimeTask(), intervalTime, intervalTime);
    }

    /**
     * 停止打字
     */
    public void stopPrint() {
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 判断str是否为空
     *
     * @param str
     * @return
     */
    private boolean strIsEmpty(String str) {
        if (null != str && !"".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打字计时器任务
     */
    class PrinterTimeTask extends TimerTask {

        @Override
        public void run() {
            // 需要刷新页面
            post(new Runnable() {
                @Override
                public void run() {
                    // 如果未显示完,继续显示
                    if (printProgress < printString.length()) {
                        printProgress++;
                        // (printProgress & 1) == 1 等价于printProgress%2!=0
                        setText(printString.substring(0, printProgress) + ((printProgress & 1) == 1 ? intervalChar : ""));
                    } else {
                        // 如果完成打字,显示完整文字
                        setText(printString);
                        if (onStopPrintListener != null) {
                            onStopPrintListener.onStopPrintListener();
                        }
                        stopPrint();
                    }
                }
            });
        }
    }

    public interface OnStopPrintListener {
        void onStopPrintListener();
    }

    private OnStopPrintListener onStopPrintListener;

    public void setOnStopPrintListener(OnStopPrintListener onStopPrintListener) {
        this.onStopPrintListener = onStopPrintListener;
    }
}
