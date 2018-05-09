package com.example.jiang.microblog.view.share.at.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiang on 2018/5/3.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<User> users;

    public FriendAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView header;
        TextView name;
        TextView description;
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            header = (CircleImageView) itemView.findViewById(R.id.header);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            checkBox = (CheckBox) itemView.findViewById(R.id.select_friend);
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_friend_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(users.get(position).getAvatar_large()).into(holder.header);
        holder.name.setText(users.get(position).getName());
        holder.description.setText(users.get(position).getDescription());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemOnClickListener(v, position, holder.checkBox.isChecked());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public interface OnItemOnClickListener {
        public void onItemOnClickListener(View view, int position, boolean isCheck);
    }
    private  OnItemOnClickListener clickListener;

    public void setClickListener(OnItemOnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
