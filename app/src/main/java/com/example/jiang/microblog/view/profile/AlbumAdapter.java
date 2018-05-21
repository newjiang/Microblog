package com.example.jiang.microblog.view.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.PicUrlsBean;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.activity.ShowPictureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/5/21.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    //TODO 小图
    private static final String THUMBNAIL = "thumbnail";
    //TODO 中图
    private static final String BMIDDLE = "bmiddle";

    private Context context;
    private List<PicUrlsBean> picUrlsBeanList = new ArrayList<>();

    public AlbumAdapter(Context context, List<PicUrlsBean> picUrlsBeanList) {
        this.context = context;
        this.picUrlsBeanList = picUrlsBeanList;
        Log.e("AlbumAdapter", picUrlsBeanList.toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView album;

        public ViewHolder(View itemView) {
            super(itemView);
            album = (ImageView) itemView.findViewById(R.id.album_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //FIXME 主页显示thumbnail级别的图改为bmiddle级别
        String bmiddle_pic = picUrlsBeanList.get(position).getThumbnail_pic().replaceAll(THUMBNAIL, BMIDDLE);
        Glide.with(context).load(bmiddle_pic).into(holder.album);

        holder.album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> stringList = new ArrayList<>();
                for (PicUrlsBean x : picUrlsBeanList) {
                    stringList.add(x.getThumbnail_pic());
                }
                Intent intent = new Intent(context, ShowPictureActivity.class);
                intent.putStringArrayListExtra(IntentKey.MICROBLOG_PICTURE, stringList);
                intent.putExtra(IntentKey.MICROBLOG_PICTURE_NUMBER, position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return picUrlsBeanList.size();
    }

}
