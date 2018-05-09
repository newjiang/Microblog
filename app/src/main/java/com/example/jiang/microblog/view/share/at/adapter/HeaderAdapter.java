package com.example.jiang.microblog.view.share.at.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiang on 2018/5/3.
 */

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder> {

    private Context context;
    private List<User> headers;

    public HeaderAdapter(Context context, List<User> headers) {
        this.context = context;
        this.headers = headers;
    }

    /**
     * 获得选择@好友集合
     *
     * @return
     */
    public List<User> getHeaders() {
        return headers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView header;
        public ViewHolder(View itemView) {
            super(itemView);
            header = (CircleImageView) itemView.findViewById(R.id.at_header);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(headers.get(position).getAvatar_large()).into(holder.header);
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }

    /**
     * 添加新元素
     *
     * @param user
     */
    public void add(User user) {
        headers.add(user);
        notifyDataSetChanged();
    }

    /**
     * 移除元素
     *
     * @param user
     */
    public void remove(User user) {
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getId() == user.getId()) {
                headers.remove(i);
            }
        }
        notifyDataSetChanged();
    }


}
