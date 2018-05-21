package com.example.jiang.microblog.view.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
 * Created by jiang on 2018/4/14.
 */

public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String SHORT_URL = "http://t.cn";

    private Context context;
    protected final List<Statuses> beanList;

    public RecyclerViewBaseAdapter(Context context, List<Statuses> data) {
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
        //TODO 在这里设置数据
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
            return beanList.size() + 1;
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        //TODO 用户头像
        CircleImageView header;
        //TODO 用户名字
        TextView username;
        //TODO 用户备注信息
        TextView remark;
        //TODO 微博文字内容
        TextView content;
        //TODO 微博配图
        NineGridImageView picture;
        //TODO 发布时间
        TextView time;
        //TODO 来源
        TextView from;
        //TODO 点赞数
        TextView like;
        //TODO 转发数
        TextView redirect;
        //TODO 评论数
        TextView comment;
        //TODO 点赞图标
        ImageView likeImage;
        //TODO 转发图标
        ImageView redirectImage;
        //TODO 评论图标
        ImageView commentImage;

        //TODO 微博文字内容
        TextView retweetedContent;
        //TODO 微博配图
        NineGridImageView retweetedPicture;

        //TODO 位置
        private int mPosition;

        public InnerHolder(View itemView) {
            super(itemView);
            //TODO 用户头像
            header = (CircleImageView) itemView.findViewById(R.id.microblog_user_proflie);
            //TODO 用户名字
            username = (TextView) itemView.findViewById(R.id.microblog_remark);
            //TODO 用户备注
            remark = (TextView) itemView.findViewById(R.id.microblog_username);
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

            //TODO 进入微博详情页面
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    Statuses statuses = beanList.get(mPosition);
                    //TODO 通过json传递过去
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
            //TODO 用户头像
            Glide.with(App.getContext()).load(bean.getUser().getProfile_image_url()).into(header);
            if (bean.getUser().getRemark().equals("") || bean.getUser().getRemark() == null) {
                //TODO 用户备注显示用户的名字
                remark.setText(bean.getUser().getRemark());
                //TODO 用户名字内容是空
                username.setText(bean.getUser().getName());
            } else {
                //TODO 用户备注
                remark.setText(bean.getUser().getName());
                //TODO 用户名字
                username.setText(bean.getUser().getRemark());
            }
            //TODO 微博文字内容
            if (hasWeiboUrl(bean.getText())) {
                int start = bean.getText().lastIndexOf(SHORT_URL);
                int end = bean.getText().length();
                SpannableString spanableInfo = new SpannableString(bean.getText());
                spanableInfo.setSpan(new Clickable(onClickListener), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setText(spanableInfo);
                content.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                content.setText(bean.getText());
            }
            //TODO 发布时间
            time.setText(getTimeFormat(bean.getCreated_at()));
            //TODO 来源
            from.setText(getFormFormat(bean.getSource()));
            //TODO 点赞数
            like.setText(String.valueOf(bean.getAttitudes_count()));
            //TODO 转发数
            redirect.setText(String.valueOf(bean.getReposts_count()));
            //TODO 评论数
            comment.setText(String.valueOf(bean.getComments_count()));
            //TODO 微博配图
            picture.setAdapter(new NineImageAdapter());
            //TODO 微博配图数据源
            picture.setImagesData(beanList.get(position).getPic_urls());

            //TODO 设置转发微博信息
            setRetweetedData(bean);
            //TODO 设置头像点击事件,根据传递用户的信息
            setHeaderOnClickListener(header, new Gson().toJson(bean.getUser()));
        }

        /**
         * 设置转发的内容
         *
         * @param bean
         */
        private void setRetweetedData(Statuses bean) {
            if (bean.getRetweeted_status() != null) {
                //TODO 转发微博的内容
                if (bean.getRetweeted_status().getUser() != null) {
                    String name = "@" + bean.getRetweeted_status().getUser().getName();
                    String text = name + bean.getRetweeted_status().getText();
                    SpannableStringBuilder sb = TextColorTools.highlight(text, name);
                    retweetedContent.setText(sb);
                } else {
                    retweetedContent.setText(bean.getRetweeted_status().getText());
                }
                //TODO 转发微博的配图
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

        class Clickable extends ClickableSpan{
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
    }
    /**
     * 微博内容是否含有url
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
        //TODO 修改显示时间格式 如2018-01-01 00：00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String f = TimeFormat.format(d);
        if (f.equals(TimeFormat.FLAG)) {
            //TODO 返回真正时间 如2018-1-1 00：00
            return format.format(d);
        } else {
            //TODO 返回xx秒前，xx分钟前，xx小时前，昨天
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
