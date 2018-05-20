package com.example.jiang.microblog.view.search.adapter;

/**
 * Created by jiang on 2018/4/21.
 */

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

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
        Log.e("SearchNineImgAdapter", list.toString());
        return super.onItemImageLongClick(context, imageView, index, list);
    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {

    }
}
