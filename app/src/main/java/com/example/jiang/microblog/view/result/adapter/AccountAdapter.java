package com.example.jiang.microblog.view.result.adapter;

import android.content.Context;
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

import java.util.List;

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
        ImageView header;
        ImageView gender;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            header = (ImageView) itemView.findViewById(R.id.header);
            gender = (ImageView) itemView.findViewById(R.id.gender);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(HTTP + accounts.get(position).getImageUrl()).into(holder.header);
        if (accounts.get(position).getGender().equals("女")) {
            holder.gender.setImageResource(R.drawable.icon_female);
        } else {
            holder.gender.setImageResource(R.drawable.icon_male);
        }
        holder.name.setText(Html.fromHtml(accounts.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
}
