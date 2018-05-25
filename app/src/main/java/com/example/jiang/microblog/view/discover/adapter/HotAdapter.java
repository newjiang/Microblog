package com.example.jiang.microblog.view.discover.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.discover.activity.ResultActivity;

import java.util.List;
import java.util.Random;

/**
 * Created by jiang on 2018/4/26.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {

    private int[] colors = new int[]{
            Color.BLACK, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.RED, Color.GREEN,
            Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.BLUE};

    private Context context;
    private List<Hot> hots;
    private boolean isShowCount;

    public HotAdapter(Context context, List<Hot> hots, boolean isShowCount) {
        this.context = context;
        this.hots = hots;
        this.isShowCount = isShowCount;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView degree;
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.hot_title);
            degree = (TextView) itemView.findViewById(R.id.degree);
            count = (TextView) itemView.findViewById(R.id.count);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_hots, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.title.setText(hots.get(position).getTitle());
        Random random = new Random();
        holder.title.setTextColor(colors[random.nextInt(10)]);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra(IntentKey.SEARCH_CONTENT, hots.get(position).getTitle());
                context.startActivity(intent);
            }
        });

        holder.degree.setText(hots.get(position).getDegree());
        if (hots.get(position).getDegree().equals("热")) {
            holder.degree.setBackgroundResource(R.drawable.degree_hot_background);
        }else if (hots.get(position).getDegree().equals("新")) {
            holder.degree.setBackgroundResource(R.drawable.degree_new_background);
        } else if (hots.get(position).getDegree().equals("荐")) {
            holder.degree.setBackgroundResource(R.drawable.degree_recmmend_background);
        } else {
            //TODO 设置透明
            holder.degree.setBackgroundColor(0x00000000);
        }
        if (isShowCount) {
            holder.count.setText(hots.get(position).getCount());
            holder.count.setVisibility(View.VISIBLE);

        } else {
            holder.count.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return hots.size();
    }
}
