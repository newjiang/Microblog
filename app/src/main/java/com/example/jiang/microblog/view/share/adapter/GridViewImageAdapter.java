package com.example.jiang.microblog.view.share.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.jiang.microblog.R;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by jiang on 2018/4/17.
 */

public class GridViewImageAdapter extends BaseAdapter {

    private List<Map<String, Object>> datas;
    private Context context;
    private LayoutInflater inflater;

    //TODO 设置最大上传图片数
    private int maxImages = 9;

    public GridViewImageAdapter(Context context, List<Map<String, Object>> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public int getMaxImages() {
        return maxImages;
    }

    public class ViewHolder {
        public final ImageView imageView;
        public final Button delete;
        public final View view;
        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
            delete = (Button) view.findViewById(R.id.delete);
            this.view = view;
        }
    }

    @Override
    public int getCount() {
        int count = datas == null ? 0 : datas.size();
        if (count >= maxImages) {
            return datas.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (datas != null && position < datas.size()) {
            final File file = new File(datas.get(position).get("path").toString());
            Glide.with(context).load(file).priority(Priority.HIGH).into(holder.imageView);
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.delete.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void notifyDataSetChanged(List<Map<String, Object>> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }
}
