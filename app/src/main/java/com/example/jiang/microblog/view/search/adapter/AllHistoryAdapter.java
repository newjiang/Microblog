package com.example.jiang.microblog.view.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.search.activity.AllHistoryActivity;
import com.example.jiang.microblog.view.search.activity.ResultActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by jiang on 2018/4/26.
 */

public class AllHistoryAdapter extends RecyclerView.Adapter<AllHistoryAdapter.ViewHolder> {

    private List<History> historys;
    private Context context;

    public AllHistoryAdapter(Context context, List<History> historys) {
        this.historys = historys;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        ImageView clearIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.history_content);
            clearIcon = (ImageView) itemView.findViewById(R.id.clear_icon);
        }
    }

    @Override
    public AllHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllHistoryAdapter.ViewHolder holder, final int position) {
        holder.content.setText(historys.get(position).getHistory());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,historys.get(position).getHistory(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra(IntentKey.SEARCH_CONTENT, historys.get(position).getHistory());
                context.startActivity(intent);
            }
        });
        holder.clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeHistory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historys.size();
    }


    /**
     * 删除对应的历史记录
     * @param position
     */
    public void removeHistory(int position) {
        historys.get(position).delete();
        historys.remove(position);
        notifyItemRemoved(position);
        showTips();
        notifyDataSetChanged();

    }
    /**
     * 清除所有的历史记录
     */
    public void clearHistory() {
        historys.clear();
        DataSupport.deleteAll(History.class);
        showTips();
        notifyDataSetChanged();
    }

    public void showTips(){
        AllHistoryActivity activity = (AllHistoryActivity) context;
        if (historys.isEmpty()) {
            activity.getClearHistory().setVisibility(View.GONE);
        } else {
            activity.getClearHistory().setVisibility(View.VISIBLE);
        }
    }

}
