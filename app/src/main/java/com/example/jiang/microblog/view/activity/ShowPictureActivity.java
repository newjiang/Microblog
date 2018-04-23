package com.example.jiang.microblog.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.widget.PictureView;

import java.util.ArrayList;

public class ShowPictureActivity extends BaseActivity {

    private ViewPager viewPager;
    //TODO 显示的图片源
    private ImageView[] imageViews;
    //TODO 图片源的集合
    private ArrayList<String> picList;
    //TODO 是否第一次打开
    private boolean isFirst = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
        //TODO 背景设置为黑色
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.BLACK);
            getWindow().setStatusBarColor(Color.BLACK);
        }
        ActionBar ber = getSupportActionBar();
        if (ber != null) {
            ber.hide();
        }

        setContentView(R.layout.activity_show_picture);

        viewPager = (ViewPager) findViewById(R.id.show_picture_view_pager);

        Intent intent = getIntent();
        picList = intent.getStringArrayListExtra(IntentKey.MICROBLOG_PICTURE);
        final int index = intent.getIntExtra(IntentKey.MICROBLOG_PICTURE_NUMBER, 0);

        imageViews = new ImageView[picList.size()];

        viewPager.setCurrentItem(index);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return picList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup containr, int position) {
                PictureView picture = new PictureView(getApplicationContext());
                Glide.with(ShowPictureActivity.this).load(getLargeImage(position, index)).into(picture);
                containr.addView(picture);
                imageViews[position] = picture;
                return picture;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViews[position]);
            }
        });

        //TODO 显示当前的图片
        viewPager.setCurrentItem(index);
    }

    /**
     * 获取原图的URL
     * @param position  位置
     * @param index      第一次打开时的位置
     * @return
     */
    private String getLargeImage(int position, int index) {
        String largeImage;
        if (isFirst) {
            largeImage = picList.get(index).replaceAll("thumbnail", "large");
            isFirst = false;
        } else {
            largeImage = picList.get(position).replaceAll("thumbnail", "large");
        }
        return largeImage;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }
}



