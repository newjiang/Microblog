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
import com.example.jiang.microblog.bean.Weibo;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.view.comment.CommentActivity;
import com.example.jiang.microblog.view.profile.ProfileActivity;
import com.jaeger.ninegridimageview.NineGridImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiang on 2018/4/14.
 */

public class WeiboAdapter extends RecyclerView.Adapter<WeiboAdapter.ViewHolder> {

    private Context context;
    private List<Weibo> weibos;

    public WeiboAdapter(Context context, List<Weibo> weibos) {
        this.context = context;
        this.weibos = weibos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        // 用户头像
        CircleImageView header;
        // 用户名字
        TextView username;
        // 微博文字内容
        TextView content;
        // 微博配图
        NineGridImageView picture;
        // 发布时间
        TextView time;
        // 来源
        TextView from;
        // 点赞数
        TextView like;
        // 转发数
        TextView redirect;
        // 评论数
        TextView comment;
        // 点赞图标
        ImageView likeImage;
        // 转发图标
        ImageView redirectImage;
        // 评论图标
        ImageView commentImage;
        // 微博文字内容
        TextView retweetedContent;
        // 微博配图
        NineGridImageView retweetedPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            // 用户头像
            header = (CircleImageView) view.findViewById(R.id.microblog_user_proflie);
            // 用户名字
            username = (TextView) view.findViewById(R.id.microblog_remark);
            // 微博文字内容
            content = (TextView) view.findViewById(R.id.microblog_content);
            // 微博配图
            picture = (NineGridImageView) view.findViewById(R.id.microblog_picture);
            // 发布时间
            time = (TextView) view.findViewById(R.id.microblog_time);
            // 来源
            from = (TextView) view.findViewById(R.id.microblog_from);
            // 点赞数
            like = (TextView) view.findViewById(R.id.attitudes_count);
            // 转发数
            redirect = (TextView) view.findViewById(R.id.reposts_count);
            // 评论数
            comment = (TextView) view.findViewById(R.id.comments_count);
            // 点赞图标
            likeImage = (ImageView) view.findViewById(R.id.attitudes_count_iv);
            // 转发图标
            redirectImage = (ImageView) view.findViewById(R.id.reposts_count_iv);
            // 评论图标
            commentImage = (ImageView) view.findViewById(R.id.comments_count_iv);
            // 转发微博文字内容
            retweetedContent = (TextView) view.findViewById(R.id.retweeted_content);
            // 转发微博配图
            retweetedPicture = (NineGridImageView) view.findViewById(R.id.retweeted_picture);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_microblog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 用户头像
        Glide.with(context).load("http:" + weibos.get(position).getHeader()).into(holder.header);
        // 用户名字
        holder.username.setText(weibos.get(position).getName());
        // 微博文字内容
        holder.content.setText(Html.fromHtml(weibos.get(position).getContent()));
        // 微博配图
        holder.picture.setAdapter(new SearchNineImgAdapter());
        // 微博配图数据源
        holder.picture.setImagesData(weibos.get(position).getPictureList());
        // 发布时间
        holder.time.setText(weibos.get(position).getTime());
        // 来源
        holder.from.setText(Html.fromHtml(weibos.get(position).getFrom()));
        // 点赞数
        holder.like.setText(weibos.get(position).getLike());
        // 转发数
        holder.redirect.setText(weibos.get(position).getRedirect());
        // 评论数
        holder.comment.setText(weibos.get(position).getComment());
        // 点赞图标
        // 转发图标
        // 评论图标
        if (weibos.get(position).getRetweetedContent().equals("")) {
            holder.retweetedContent.setVisibility(View.GONE);
        } else {
            // 转发微博文字内容
            holder.retweetedContent.setText(Html.fromHtml(weibos.get(position).getRetweetedUser() + weibos.get(position).getRetweetedContent()));
        }
        if (weibos.get(position).getRetweetedPicture().equals("")) {
            holder.retweetedPicture.setVisibility(View.GONE);
        } else {
            // 转发微博配图
            holder.retweetedPicture.setAdapter(new SearchNineImgAdapter());
            // 微博配图数据源
            holder.retweetedPicture.setImagesData(weibos.get(position).getRetweetedPicture());
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra(IntentKey.MICROBLOG_MID, weibos.get(position).getMid());
                context.startActivity(intent);
            }
        });
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(IntentKey.USERNAME, weibos.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weibos.size();
    }
}
