package com.example.jiang.microblog.view.comment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.utils.TimeFormat;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by jiang on 2018/4/19.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    //TODO at字符
    private static final char AT = '@';
    //TODO at字符串
    private static final String AT_STRING = "@";
    //TODO 冒号字符
    private static final char COLON = ':';
    //TODO 冒号字符串
    private static final String COLON_STIRNG= ":";

    private Context context;

    private List<Comment.CommentsBean> comments;

    //TODO 声明一个接口的引用
    private OnItemClickListener onItemClickListener;

    //TODO 构造函数
    public CommentAdapter(Context context, List<Comment.CommentsBean> comments) {
        this.context = context;
        this.comments = comments;
    }

    //设置监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TODO 评论者头像
        CircleImageView header;
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
            header = (CircleImageView) itemView.findViewById(R.id.commentator_header);
            commentator = (TextView) itemView.findViewById(R.id.commentator);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            time = (TextView) itemView.findViewById(R.id.comment_time);
            from = (TextView) itemView.findViewById(R.id.comment_sources);
            commentator = (TextView) itemView.findViewById(R.id.commentator);
            like = (ImageView) itemView.findViewById(R.id.comment_like);
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemclick(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.ViewHolder holder, final int position) {
        //TODO 按照时间顺序显示，list倒序
        Comment.CommentsBean bean = comments.get(comments.size() - position - 1);

        Glide.with(context).load(bean.getUser().getAvatar_hd()).into(holder.header);
        holder.commentator.setText(bean.getUser().getName());

        //TODO 关键字高亮
        if (bean.getText().contains(AT_STRING) && bean.getText().contains(COLON_STIRNG)) {
            SpannableStringBuilder sb = TextColorTools.highlight(bean.getText(), getKeyWord(bean.getText()));
            holder.content.setText(sb);
        } else {
            holder.content.setText(bean.getText());
        }
        holder.time.setText(getTimeFormat(bean.getCreated_at()));
        holder.from.setText(getFormFormat(bean.getSource()));

    }

    /**
     * 获取关键字
     *
     * @param str
     * @return
     */
    public String getKeyWord(String str) {
        int start = str.indexOf(AT);
        int end = str.indexOf(COLON);
        String key = str.substring(start, end);
        return key;
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
        //TODO FIXME 判断是否为null
        if (source == null) {
            return null;
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

            if (f.equals(TimeFormat.YESTERDAY)) {
                //TODO 返回昨天 yyyy-MM-dd HH:mm
                return f + format.format(d);
            } else {
                //TODO 返回xx秒前，xx分钟前，xx小时前
                return f;
            }
        }
    }

    //TODO 点击事件监听器
    public interface OnItemClickListener {
        void OnItemclick(View view, int position);
    }
}
