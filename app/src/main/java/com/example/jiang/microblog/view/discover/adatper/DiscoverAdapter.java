package com.example.jiang.microblog.view.discover.adatper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.ListUtils;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.utils.TimeFormat;
import com.example.jiang.microblog.view.adapter.NineImageAdapter;
import com.example.jiang.microblog.view.adapter.RetweetedImageAdapter;
import com.example.jiang.microblog.view.comment.CommentActivity;
import com.example.jiang.microblog.view.profile.ProfileActivity;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiang on 2018/4/14.
 */

public abstract class DiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    protected final List<Statuses> beanList;

    public DiscoverAdapter(Context context, List<Statuses> data) {
        this.context = context;
        this.beanList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 这个方法是用于绑定holder的,一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 在这里设置数据
        ((InnerHolder) holder).setData(beanList.get(position), position);
    }


    /**
     * 反回条目的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (beanList != null) {
            return beanList.size()+1;
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
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

        // 微博文字内容
        TextView retweetedContent;
        // 微博配图
        NineGridImageView retweetedPicture;

        // 位置
        private int mPosition;

        public InnerHolder(View itemView) {
            super(itemView);
            // 用户头像
            header = (CircleImageView) itemView.findViewById(R.id.microblog_user_proflie);
            // 用户名字
            username = (TextView) itemView.findViewById(R.id.microblog_remark);
            // 用户备注
            remark = (TextView) itemView.findViewById(R.id.microblog_username);
            // 微博文字内容
            content = (TextView) itemView.findViewById(R.id.microblog_content);
            // 微博配图
            picture = (NineGridImageView) itemView.findViewById(R.id.microblog_picture);
            // 发布时间
            time = (TextView) itemView.findViewById(R.id.microblog_time);
            // 来源
            from = (TextView) itemView.findViewById(R.id.microblog_from);
            // 点赞数
            like = (TextView) itemView.findViewById(R.id.attitudes_count);
            // 转发数
            redirect = (TextView) itemView.findViewById(R.id.reposts_count);
            // 评论数
            comment = (TextView) itemView.findViewById(R.id.comments_count);
            // 点赞图标
            likeImage = (ImageView) itemView.findViewById(R.id.attitudes_count_iv);
            // 转发图标
            redirectImage = (ImageView) itemView.findViewById(R.id.reposts_count_iv);
            // 评论图标
            commentImage = (ImageView) itemView.findViewById(R.id.comments_count_iv);
            // 转发微博文字内容
            retweetedContent = (TextView) itemView.findViewById(R.id.retweeted_content);
            // 转发微博配图
            retweetedPicture = (NineGridImageView) itemView.findViewById(R.id.retweeted_picture);

            // 进入微博详情页面
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    Statuses statuses = beanList.get(mPosition);
                    // 通过json传递过去
                    intent.putExtra(IntentKey.MICROBLOG_JSON, new Gson().toJson(statuses));
                    context.startActivity(intent);
                }
            });
        }

        /**
         * 这个方法用于设置数据
         */
        public void setData(final Statuses bean, int position) {
            this.mPosition = position;
            // 用户头像
            Glide.with(App.getContext()).load(bean.getUser().getProfile_image_url()).into(header);
            if (bean.getUser().getRemark().equals("") || bean.getUser().getRemark() == null) {
                // 用户备注显示用户的名字
                remark.setText(bean.getUser().getRemark());
                // 用户名字内容是空
                username.setText(bean.getUser().getName());
            } else {
                // 用户备注
                remark.setText(bean.getUser().getName());
                // 用户名字
                username.setText(bean.getUser().getRemark());
            }
            // 微博文字内容
            content.setText(bean.getText());
            // 微博内容
            content.setText(bean.getText());
            // 发布时间
            time.setText(getTimeFormat(bean.getCreated_at()));
            // 来源
            from.setText(getFormFormat(bean.getSource()));
            // 点赞数
            like.setText(String.valueOf(bean.getAttitudes_count()));
            // 转发数
            redirect.setText(String.valueOf(bean.getReposts_count()));
            // 评论数
            comment.setText(String.valueOf(bean.getComments_count()));
            // 微博配图
            picture.setAdapter(new NineImageAdapter());
            // 微博配图数据源
            picture.setImagesData(beanList.get(position).getPic_urls());
            setRetweetedData(bean);
            // 设置头像点击事件,根据传递用户的信息
            setHeaderOnClickListener(header, new Gson().toJson(bean.getUser()));
        }

        /**
         * 设置转发的内容
         *
         * @param bean
         */
        private void setRetweetedData(Statuses bean) {
            if (bean.getRetweeted_status() != null) {
                // 转发微博的内容
                String name = "@" + bean.getRetweeted_status().getUser().getName();
                String text =  name + bean.getRetweeted_status().getText();
                SpannableStringBuilder sb = TextColorTools.highlight(text, name);
                retweetedContent.setText(sb);
                // 转发微博的配图
                retweetedPicture.setAdapter(new RetweetedImageAdapter());
                retweetedPicture.setImagesData(bean.getRetweeted_status().getPic_urls());
                retweetedPicture.setMinimumHeight(retweetedPicture.getHeight());
                retweetedContent.setVisibility(View.VISIBLE);
            } else {
                retweetedContent.setText(null);
                retweetedPicture.setAdapter(new RetweetedImageAdapter());
                retweetedPicture.setImagesData(null);
                retweetedContent.setVisibility(View.GONE);
                retweetedPicture.setMinimumHeight(0);
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
    }

    /**
     * 格式转化来源信息
     *
     * @param source
     * @return
     */
    private String getFormFormat(String source) {
        return Jsoup.parse(source).text();
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

    /**
     * 添加新的元素并刷新
     *
     * @param newList
     * @param flag
     */
    public void add(List<Statuses> newList, boolean flag) {
        ListUtils.add(beanList, newList, flag);
        notifyDataSetChanged();
    }

}