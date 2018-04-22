package com.example.jiang.microblog.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by jiang on 2018/4/15.
 */

public class PictureView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    /**
     * 初始化时缩放的值
     */
    private float mInitScale;
    /**
     * 双击放大值到达的值
     */
    private float mMaxScale;
    /**
     * 双击最小值到达的值
     */
    private float mMidScale;

    private Matrix mScaleMatrix;

    /**
     * 捕获用户多指触控时缩放的比例
     */
    private ScaleGestureDetector mScaleGestureDetector;

    //--------------------双击放大与缩小
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;

    //-----------------自由移动
    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPointCount;
    private float mLastX;
    private float mLastY;
    //系统判断的可move的最小值。
    private float mTouchSlop;
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    public PictureView(Context context) {
        this(context, null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(isAutoScale){
                    return true ;
                }
                float x = e.getX();
                float y = e.getY();
                float scale = getScale();
                if (scale < mMidScale) {
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;
                } else {
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;

                }
                return true;
            }
        });
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    private class AutoScaleRunnable implements Runnable{

        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        //缩放的中心点
        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;
        private float tmpScale;

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale,x,y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale)
                    || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private boolean mOnce;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);

    }

    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            //得到控件的宽和高
            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            //获取图片的width , height.
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;
            /**
             * 如果图片的宽度大于屏幕的宽度，但是高度小于屏幕的高度
             */
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }
            /**
             * 如果图片的高度大于屏幕的高度，但是宽度小于屏幕的宽度
             */
            if (dw < width && dh > height) {
                scale = height * 1.0f / dh;
            }

            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            /**
             * 初始化时缩放的比例
             */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;


            //将图片移到控件的中心。
            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    //缩放比例
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) {
            return true;
        }
        //缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }

        return false;
    }

    /**
     * 获取图片放大缩小以后的宽和高，以及l,r,t,b
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 在缩放的时候进行边界的控制，已经我们的位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float delteX = 0;
        float delteY = 0;

        int width = getWidth();
        int height = getHeight();
        //检查边界，不让留下白边
        if (rect.width() >= width) {
            if (rect.left > 0) {
                delteX = -rect.left;
            }
            if (rect.right < width) {
                delteX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                delteY = -rect.top;
            }
            if (rect.bottom < height) {
                delteY = height - rect.bottom;
            }
        }
        //如果宽度或者高度小于控件的宽或者高，则让其居中
        if (rect.width() < width) {
            delteX = width * 1.0f / 2f - rect.right + rect.width() * 1.0f / 2f;
        }
        if (rect.height() < height) {
            delteY = height * 1.0f / 2f - rect.bottom + rect.height() * 1.0f / 2f;
        }
        mScaleMatrix.postTranslate(delteX, delteY);
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //如果有双击事件，则就不让有其他操作。
        if(mGestureDetector.onTouchEvent(event)){
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);
        float x = 0;
        float y = 0;
        //拿到多点触控的数量
        int pointerCount = event.getPointerCount();
        //获取中心位置
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointCount != pointerCount) {
            mLastPointCount = pointerCount;
            mLastX = x;
            mLastY = y;
            isCanDrag = false;
        }
        RectF rect = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (rect.width() > getWidth() || rect.height() > getHeight()) {
                    if(getParent() instanceof ViewPager){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if (rect.width() > getWidth() || rect.height() > getHeight()) {
                    if(getParent() instanceof ViewPager){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {

                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        //如果宽度小于控件的宽度，不允许横向移动
                        if (rect.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        //如果高度小于控件的宽度，不允许纵向移动
                        if (rect.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
                mLastPointCount = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                mLastPointCount = 0;
                break;
            default:
                break;
        }


        return true;
    }

    /**
     * 当移动时，进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (isCheckTopAndBottom && rect.top > 0) {
            deltaY = -rect.top;
        }
        if (isCheckTopAndBottom && rect.bottom < height) {
            deltaY = height - rect.bottom;
        }
        if (isCheckLeftAndRight && rect.left > 0) {
            deltaX = -rect.left;
        }
        if (isCheckLeftAndRight && rect.right < width) {
            deltaX = width - rect.right;
        }
        mScaleMatrix.postTranslate(deltaX,deltaY);

    }

    /**
     * 判断是否能触发move
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) >= mTouchSlop;
    }
}