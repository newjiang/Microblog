package com.example.jiang.microblog.view.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.History;
import com.example.jiang.microblog.view.search.SearchActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by jiang on 2018/4/26.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historys;
    private Context context;

    public HistoryAdapter(Context context, List<History> historys) {
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
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, final int position) {
        holder.content.setText(historys.get(position).getHistory());
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
     * 添加历史记录
     * @param history
     */
    public void addHistory(History history) {
        historys.add(0, history);
        history.save();
        notifyItemInserted(0);
        if (historys.size() > 5) {
            historys.remove(historys.size() - 1);
        }
        showTips();
        notifyDataSetChanged();

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
        List<History> all = DataSupport.findAll(History.class);
        if (all.size() > 5) {
            historys.add(all.get(4));
        }
        notifyDataSetChanged();

    }
    /**
     * 清除所有的历史记录
     */
    public void clearHistory() {
        historys.clear();
        showTips();
        notifyDataSetChanged();
    }

    public void showTips(){
        SearchActivity activity = (SearchActivity) context;
        if (historys.isEmpty()) {
            activity.getClearText().setVisibility(View.GONE);
            activity.getAllHistory().setVisibility(View.GONE);
        } else {
            activity.getClearText().setVisibility(View.VISIBLE);
            activity.getAllHistory().setVisibility(View.VISIBLE);
        }
    }

}
