package com.example.jiang.microblog.view.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.OnFavouritesListener;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.utils.TimeFormat;
import com.example.jiang.microblog.view.adapter.NineImageAdapter;
import com.example.jiang.microblog.view.adapter.RetweetedImageAdapter;
import com.example.jiang.microblog.view.comment.CommentActivity;
import com.example.jiang.microblog.view.home.WebActivity;
import com.example.jiang.microblog.view.profile.ProfileActivity;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by jiang on 2018/5/23.
 */

public class MicroblogAdapter extends RecyclerView.Adapter<MicroblogAdapter.ViewHolder> {

    private static final String SHORT_URL = "http://t.cn";

    private Context context;
    private List<Statuses> statusesList;
    private OnFavouritesListener onFavouritesListener;

    public MicroblogAdapter(Context context, List<Statuses> statusesList) {
        this.statusesList = statusesList;
        this.context = context;
    }

    public void setOnFavouritesListener(OnFavouritesListener onFavouritesListener) {
        this.onFavouritesListener = onFavouritesListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        // 用户头像
        CircleImageView header;
        // 用户名字
        TextView username;
        // 用户备注信息
        TextView remark;
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
        // 收藏图标
        ImageView favorited;
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
            // 用户备注
            remark = (TextView) view.findViewById(R.id.microblog_username);
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
            // 收藏图标
            favorited = (ImageView) itemView.findViewById(R.id.favorited_iv);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(App.getContext()).load(statusesList.get(position).getUser().getProfile_image_url()).into(holder.header);
        if (statusesList.get(position).getUser().getRemark().equals("") || statusesList.get(position).getUser().getRemark() == null) {
            // 用户备注显示用户的名字
            holder.remark.setText(statusesList.get(position).getUser().getRemark());
            // 用户名字内容是空
            holder.username.setText(statusesList.get(position).getUser().getName());
        } else {
            // 用户备注
            holder.remark.setText(statusesList.get(position).getUser().getName());
            // 用户名字
            holder.username.setText(statusesList.get(position).getUser().getRemark());
        }
        // 微博文字内容
        if (hasWeiboUrl(statusesList.get(position).getText())) {
            int start = statusesList.get(position).getText().lastIndexOf(SHORT_URL);
            int end = statusesList.get(position).getText().length();
            SpannableString spanableInfo = new SpannableString(statusesList.get(position).getText());
            spanableInfo.setSpan(new Clickable(onClickListener), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.content.setText(spanableInfo);
            holder.content.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            holder.content.setText(statusesList.get(position).getText());
        }
        // 发布时间
        holder.time.setText(getTimeFormat(statusesList.get(position).getCreated_at()));
        // 来源
        holder.from.setText(getFormFormat(statusesList.get(position).getSource()));
        // 点赞数
        holder.like.setText(String.valueOf(statusesList.get(position).getAttitudes_count()));
        // 转发数
        holder.redirect.setText(String.valueOf(statusesList.get(position).getReposts_count()));
        // 评论数
        holder.comment.setText(String.valueOf(statusesList.get(position).getComments_count()));
        if (statusesList.get(position).isFavorited()) {
            holder.favorited.setImageResource(R.drawable.ic_favorited);
        } else {
            holder.favorited.setImageResource(R.drawable.ic_favorite_border);
        }
        // 微博配图
        holder.picture.setAdapter(new NineImageAdapter());
        // 微博配图数据源
        holder.picture.setImagesData(statusesList.get(position).getPic_urls());
        // 设置转发微博信息
        setRetweetedData(statusesList.get(position), holder);
        // 设置头像点击事件,根据传递用户的信息
        setHeaderOnClickListener(holder.header, new Gson().toJson(statusesList.get(position).getUser()));

        // 进入微博详情页面
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                Statuses statuses = statusesList.get(position);
                // 通过json传递过去
                intent.putExtra(IntentKey.MICROBLOG_JSON, new Gson().toJson(statuses));
                context.startActivity(intent);
            }
        });
        holder.favorited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFavouritesListener != null) {
                    if (statusesList.get(position).isFavorited()) {
                        holder.favorited.setImageResource(R.drawable.ic_favorite_border);
                        onFavouritesListener.onFavouritesListener(statusesList.get(position),statusesList.get(position).isFavorited());
                    } else {
                        holder.favorited.setImageResource(R.drawable.ic_favorited);
                        onFavouritesListener.onFavouritesListener(statusesList.get(position),statusesList.get(position).isFavorited());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return statusesList.size();
    }
    /**
     * 微博内容是否含有url
     *
     * @param text
     * @return
     */
    protected boolean hasWeiboUrl(String text) {
        if (text.contains(SHORT_URL)) {
            return true;
        }
        return false;
    }

    /**
     * 设置转发的内容
     *
     * @param statuses
     * @param holder
     */
    private void setRetweetedData(Statuses statuses, ViewHolder holder) {

        if (statuses.getRetweeted_status() != null) {
            // 转发微博的内容
            if (statuses.getRetweeted_status().getUser() != null) {
                String name = "@" + statuses.getRetweeted_status().getUser().getName();
                String text = name + statuses.getRetweeted_status().getText();
                SpannableStringBuilder sb = TextColorTools.highlight(text, name);
                holder.retweetedContent.setText(sb);
            } else {
                holder.retweetedContent.setText(statuses.getRetweeted_status().getText());
            }
            // 转发微博的配图
            holder.retweetedPicture.setAdapter(new RetweetedImageAdapter());
            holder.retweetedPicture.setImagesData(statuses.getRetweeted_status().getPic_urls());
            holder.retweetedPicture.setMinimumHeight(holder.retweetedPicture.getHeight());
            holder.retweetedContent.setVisibility(View.VISIBLE);
        } else {
            holder.retweetedContent.setText(null);
            holder.retweetedPicture.setAdapter(new RetweetedImageAdapter());
            holder.retweetedPicture.setImagesData(null);
            holder.retweetedContent.setVisibility(View.GONE);
            holder.retweetedPicture.setMinimumHeight(0);
        }
    }

    /**
     * 点击头像事件
     *
     * @param view
     * @param userJson
     */
    private void setHeaderOnClickListener(View view, final String userJson) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(IntentKey.USER_INFORMATION, userJson);
                context.startActivity(intent);
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(IntentKey.WEIBO_URL, text);
            context.startActivity(intent);
        }
    };

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener listener) {
            mListener = listener;
        }
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(TextColorTools.COLOR);
        }
    }

    /**
     * 格式转化来源信息
     *
     * @param source
     * @return
     */
    private String getFormFormat(String source) {
        if ("".equals(source) || source == null) {
            return source;
        } else {
            return Jsoup.parse(source).text();
        }
    }

    /**
     * 获取真正的时间并转化格式
     *
     * @param time 创建时间
     * @return
     */
    private String getTimeFormat(String time) {
        Date d = new Date(time);
        // 修改显示时间格式 如2018-01-01 00：00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String f = TimeFormat.format(d);
        if (f.equals(TimeFormat.FLAG)) {
            // 返回真正时间 如2018-1-1 00：00
            return format.format(d);
        } else {
            // 返回xx秒前，xx分钟前，xx小时前，昨天
            if (f.equals(TimeFormat.YESTERDAY)) {
                return f + format.format(d);
            } else {
                return f;
            }
        }
    }

}
