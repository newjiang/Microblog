package com.example.jiang.microblog.views.comment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.TimeFormat;
import com.example.jiang.microblog.bean.Comment;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by jiang on 2018/4/19.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment.CommentsBean> comments;

    public CommentAdapter(Context context, List<Comment.CommentsBean> comments) {
        this.context = context;
        this.comments = comments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TODO 评论者头像
        ImageView header;
        //TODO 评论者
        TextView commentator;
        //TODO 评论内容
        TextView content;
        //TODO 时间
        TextView time;
        //TODO 来源
        TextView from;
        //TODO 点赞
        ImageView like;

        public ViewHolder(View itemView) {
            super(itemView);
            header = (ImageView) itemView.findViewById(R.id.commentator_header);
            commentator = (TextView) itemView.findViewById(R.id.commentator);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            time = (TextView) itemView.findViewById(R.id.comment_time);
            from = (TextView) itemView.findViewById(R.id.comment_sources);
            commentator = (TextView) itemView.findViewById(R.id.commentator);
            like = (ImageView) itemView.findViewById(R.id.comment_like);
        }
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment.CommentsBean bean = comments.get(position);
        Glide.with(context).load(bean.getUser().getAvatar_hd()).into(holder.header);
        holder.commentator.setText(bean.getUser().getName());
        holder.content.setText(bean.getText());
        holder.time.setText(getTimeFormat(bean.getCreated_at()));
        holder.from.setText(getFormFormat(bean.getSource()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        String f = TimeFormat.format(d);
        if (f.equals(TimeFormat.FLAG)) {
            //TODO 返回真正时间 如2018-1-1 01：01：01
            return format.format(d);
        } else {
            //TODO 返回xx秒前，xx分钟前，xx小时前，昨天
            return f;
        }
    }
}
