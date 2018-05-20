package com.example.jiang.microblog.view.search.adapter;

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
import com.example.jiang.microblog.bean.Weibo;
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

        CircleImageView header;     //TODO 用户头像
        TextView username;          //TODO 用户名字
        TextView content;           //TODO 微博文字内容
        NineGridImageView picture;  //TODO 微博配图
        TextView time;              //TODO 发布时间
        TextView from;              //TODO 来源
        TextView like;              //TODO 点赞数
        TextView redirect;          //TODO 转发数
        TextView comment;           //TODO 评论数
        ImageView likeImage;        //TODO 点赞图标
        ImageView redirectImage;    //TODO 转发图标
        ImageView commentImage;     //TODO 评论图标
        TextView retweetedContent;  //TODO 微博文字内容
        NineGridImageView retweetedPicture;//TODO 微博配图

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO 用户头像
            header = (CircleImageView) itemView.findViewById(R.id.microblog_user_proflie);
            //TODO 用户名字
            username = (TextView) itemView.findViewById(R.id.microblog_remark);
            //TODO 微博文字内容
            content = (TextView) itemView.findViewById(R.id.microblog_content);
            //TODO 微博配图
            picture = (NineGridImageView) itemView.findViewById(R.id.microblog_picture);
            //TODO 发布时间
            time = (TextView) itemView.findViewById(R.id.microblog_time);
            //TODO 来源
            from = (TextView) itemView.findViewById(R.id.microblog_from);
            //TODO 点赞数
            like = (TextView) itemView.findViewById(R.id.attitudes_count);
            //TODO 转发数
            redirect = (TextView) itemView.findViewById(R.id.reposts_count);
            //TODO 评论数
            comment = (TextView) itemView.findViewById(R.id.comments_count);
            //TODO 点赞图标
            likeImage = (ImageView) itemView.findViewById(R.id.attitudes_count_iv);
            //TODO 转发图标
            redirectImage = (ImageView) itemView.findViewById(R.id.reposts_count_iv);
            //TODO 评论图标
            commentImage = (ImageView) itemView.findViewById(R.id.comments_count_iv);
            //TODO 转发微博文字内容
            retweetedContent = (TextView) itemView.findViewById(R.id.retweeted_content);
            //TODO 转发微博配图
            retweetedPicture = (NineGridImageView) itemView.findViewById(R.id.retweeted_picture);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_microblog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO 用户头像
        Glide.with(context).load("http:" + weibos.get(position).getHeader()).into(holder.header);
        //TODO 用户名字
        holder.username.setText(weibos.get(position).getName());
        //TODO 微博文字内容
        holder.content.setText(Html.fromHtml(weibos.get(position).getContent()));
//TODO 微博配图
        holder.picture.setAdapter(new SearchNineImgAdapter());
        //TODO 微博配图数据源
        holder.picture.setImagesData(weibos.get(position).getPictureList());
        //TODO 发布时间
        holder.time.setText(weibos.get(position).getTime());
        //TODO 来源
        holder.from.setText(Html.fromHtml(weibos.get(position).getFrom()));
        //TODO 点赞数
        holder.like.setText(weibos.get(position).getLike());
        //TODO 转发数
        holder.redirect.setText(weibos.get(position).getRedirect());
        //TODO 评论数
        holder.comment.setText(weibos.get(position).getComment());
        //TODO 点赞图标
        //TODO 转发图标
        //TODO 评论图标
        if (weibos.get(position).getRetweetedContent().equals("")) {
            holder.retweetedContent.setVisibility(View.GONE);
        } else {
            //TODO 转发微博文字内容
            holder.retweetedContent.setText(Html.fromHtml(weibos.get(position).getRetweetedUser() + weibos.get(position).getRetweetedContent()));
        }
        if (weibos.get(position).getRetweetedPicture().equals("")) {
            holder.retweetedPicture.setVisibility(View.GONE);
        } else {
            //TODO 转发微博配图
            holder.retweetedPicture.setAdapter(new SearchNineImgAdapter());
            //TODO 微博配图数据源
            holder.retweetedPicture.setImagesData(weibos.get(position).getRetweetedPicture());
        }
    }

    @Override
    public int getItemCount() {
        return weibos.size();
    }
}
