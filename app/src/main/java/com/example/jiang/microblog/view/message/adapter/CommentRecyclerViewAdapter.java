package com.example.jiang.microblog.view.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TimeFormat;
import com.example.jiang.microblog.view.comment.CommentActivity;
import com.example.jiang.microblog.view.message.ReplyActivity;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiang on 2018/4/14.
 */

public abstract class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    protected final List<CommentsBean> beanList;

    public CommentRecyclerViewAdapter(Context context, List<CommentsBean> data) {
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
            return beanList.size() + 1;
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        // 位置
        private int mPosition;
        CircleImageView header;
        ImageView reply;
        ImageView picture;
        TextView name;
        TextView time;
        TextView from;
        TextView content;
        TextView user;
        TextView weiboContent;

        public InnerHolder(View itemView) {
            super(itemView);
            header = (CircleImageView) itemView.findViewById(R.id.header);
            reply = (ImageView) itemView.findViewById(R.id.reply);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            from = (TextView) itemView.findViewById(R.id.from);
            content = (TextView) itemView.findViewById(R.id.content);
            user = (TextView) itemView.findViewById(R.id.user);
            weiboContent = (TextView) itemView.findViewById(R.id.weibo_content);
            // 进入微博详情页面
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    Statuses statuses = beanList.get(mPosition).getStatus();
                    // 通过json传递过去
                    intent.putExtra(IntentKey.MICROBLOG_JSON, new Gson().toJson(statuses));
                    context.startActivity(intent);
                }
            });
        }

        /**
         * 这个方法用于设置数据
         */
        public void setData(final CommentsBean bean, final int position) {
            this.mPosition = position;
            Glide.with(context).load(bean.getUser().getAvatar_large()).into(header);
            if (bean.getStatus().getPic_urls() == null) {
                Glide.with(context).load(bean.getStatus().getUser().getAvatar_large()).into(picture);
            } else {
                if (bean.getStatus().getPic_urls().size() > 0) {
                    Glide.with(context).load(bean.getStatus().getPic_urls().get(0).getThumbnail_pic()).into(picture);
                } else {
                    Glide.with(context).load(bean.getStatus().getUser().getAvatar_large()).into(picture);
                }
            }
            name.setText(bean.getUser().getName());
            time.setText(getTimeFormat(bean.getCreated_at()));
            from.setText(getFormFormat(bean.getSource()));
            content.setText(bean.getText());
            user.setText(bean.getStatus().getUser().getName());
            weiboContent.setText(bean.getStatus().getText());

            reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReplyActivity.class);
                    intent.putExtra(IntentKey.COMMENT_CONTENT, new Gson().toJson(beanList.get(position)));
                    context.startActivity(intent);
                }
            });
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

}
