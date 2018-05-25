package com.example.jiang.microblog.view.share.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by jiang on 2018/5/24.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private static final int MAX_IMAGES = 9;

    private Context context;
    private List<Map<String, Object>> datas;

    public PictureAdapter(Context context, List<Map<String, Object>> datas) {
        this.context = context;
        this.datas = datas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (datas != null && position < datas.size()) {
            final File file = new File(datas.get(position).get("path").toString());
            Glide.with(context).load(file).into(holder.picture);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    if (onDeleteListener != null) {
                        onDeleteListener.onDeleteListener(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (datas.size() < MAX_IMAGES) {
            return datas.size();
        } else {
            return MAX_IMAGES;
        }
    }

    public interface onDeleteListener {
        void onDeleteListener(int position);
    }

    private onDeleteListener onDeleteListener;

    public void setOnDeleteListener(onDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

}
