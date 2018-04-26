package com.example.jiang.microblog.view.search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;

import java.util.List;
import java.util.Random;

/**
 * Created by jiang on 2018/4/26.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private int[] colors = new int[]{
            Color.BLACK, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.RED, Color.GREEN,
            Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.BLUE};

    private Context context;
    private List<String> recommends;

    public RecommendAdapter(Context context, List<String> recommends) {
        this.context = context;
        this.recommends = recommends;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recommmendText;

        public ViewHolder(View itemView) {
            super(itemView);
            recommmendText = (TextView) itemView.findViewById(R.id.recommend_text);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.recommmendText.setText(recommends.get(position));
        Random random = new Random();
        holder.recommmendText.setTextColor(colors[random.nextInt(10)]);
        holder.recommmendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, recommends.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return recommends.size();
    }
}
