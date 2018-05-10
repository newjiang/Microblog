package com.example.jiang.microblog.view.setting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jiang.microblog.R;

import java.io.File;
import java.util.List;

/**
 * Created by jiang on 2018/5/10.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    private int current;
    private int split;

    private int count = 1;

    public SettingAdapter(Context context, List<String> list, int current, int split) {
        this.context = context;
        this.list = list;
        this.current = current;
        this.split = split;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox check;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            check = (CheckBox) itemView.findViewById(R.id.check);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_ring, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(getRingName(list.get(position)));

        holder.check.setChecked(position == current);
        if (position == split) {
            holder.check.setVisibility(View.INVISIBLE);
            holder.name.setGravity(Gravity.CENTER);
        } else {
            holder.check.setVisibility(View.VISIBLE);
            holder.name.setGravity(Gravity.LEFT);
        }
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.check.setChecked(true);
                current = position;
                notifyDataSetChanged();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(list.get(position));
                }
            }
        });
    }

    private String getRingName(String ring) {
        if (ring.contains("ogg")) {
            int start = ring.lastIndexOf(File.separator) + 1;
            int end = ring.indexOf(".");
            return ring.substring(start, end);
        }
        return ring;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onItemClickListener(String name);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
