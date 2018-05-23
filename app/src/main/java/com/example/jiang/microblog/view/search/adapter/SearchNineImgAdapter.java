package com.example.jiang.microblog.view.search.adapter;

/**
 * Created by jiang on 2018/4/21.
 */

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.activity.ShowPictureActivity;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 微博九宫格图片适配器
 */
public class SearchNineImgAdapter extends NineGridImageViewAdapter<String> {

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load("http:" + url).into(imageView);
    }

    @Override
    protected boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<String> list) {
        return super.onItemImageLongClick(context, imageView, index, list);
    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String url : list) {
            String square = "http:" + url;
            String bmiddle = square.replace("square", "bmiddle");
            stringList.add(bmiddle);
        }
        Intent intent = new Intent(context, ShowPictureActivity.class);
        intent.putStringArrayListExtra(IntentKey.MICROBLOG_PICTURE, stringList);
        intent.putExtra(IntentKey.MICROBLOG_PICTURE_NUMBER, index);
        context.startActivity(intent);
    }
}
