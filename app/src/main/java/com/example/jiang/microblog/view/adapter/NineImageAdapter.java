package com.example.jiang.microblog.view.adapter;

/**
 * Created by jiang on 2018/4/21.
 */

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.bean.PicUrlsBean;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.activity.ShowPictureActivity;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 微博九宫格图片适配器
 */
public class NineImageAdapter extends NineGridImageViewAdapter<PicUrlsBean> {

    //TODO 小图
    private static final String THUMBNAIL = "thumbnail";
    //TODO 中图
    private static final String BMIDDLE = "bmiddle";

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, PicUrlsBean picUrlsBeanX) {
        //FIXME 主页显示thumbnail级别的图改为bmiddle级别
        String bmiddle_pic = picUrlsBeanX.getThumbnail_pic().replaceAll(THUMBNAIL, BMIDDLE);
        Glide.with(context).load(bmiddle_pic).into(imageView);
    }

    @Override
    protected boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<PicUrlsBean> list) {
        return super.onItemImageLongClick(context, imageView, index, list);
    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List<PicUrlsBean> list) {
        ArrayList<String> stringList = new ArrayList<>();
        for (PicUrlsBean x : list) {
            stringList.add(x.getThumbnail_pic());
        }
        Intent intent = new Intent(context, ShowPictureActivity.class);
        intent.putStringArrayListExtra(IntentKey.MICROBLOG_PICTURE, stringList);
        intent.putExtra(IntentKey.MICROBLOG_PICTURE_NUMBER, index);
        context.startActivity(intent);
    }
}
