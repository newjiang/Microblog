package com.example.jiang.microblog.view.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.profile.ProfileActivity;

import java.util.List;

import static android.text.Html.fromHtml;

/**
 * Created by jiang on 2018/4/27.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private static final String HTTP = "http:";

    private Context context;
    private List<Account> accounts;

    public AccountAdapter(Context context, List<Account> accounts) {
        this.context = context;
        this.accounts = accounts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView header;
        ImageView gender;
        TextView name;
        TextView description;
        TextView friends;
        TextView followers;
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            header = (ImageView) view.findViewById(R.id.header);
            gender = (ImageView) view.findViewById(R.id.gender);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            friends = (TextView) view.findViewById(R.id.friends);
            followers = (TextView) view.findViewById(R.id.followers);
            status = (TextView) view.findViewById(R.id.status);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(HTTP + accounts.get(position).getImageUrl()).into(holder.header);
        if (accounts.get(position).getGender().equals("女")) {
            holder.gender.setImageResource(R.drawable.icon_female);
        } else {
            holder.gender.setImageResource(R.drawable.icon_male);
        }
        holder.name.setText(fromHtml(accounts.get(position).getName()));
        if (accounts.get(position).getDescription().equals("") || accounts.get(position).getDescription() == null) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setText(fromHtml(accounts.get(position).getDescription()));
        }

        holder.friends.setText(fromHtml("关注（" + accounts.get(position).getFriends()) + ")");
        holder.followers.setText(fromHtml("粉丝（"+accounts.get(position).getFollowers())+"）");
        holder.status.setText(fromHtml("微博（" + accounts.get(position).getStatuses()) + "）");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(Html.fromHtml(accounts.get(position).getName()));
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(IntentKey.USERNAME, username);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
}
