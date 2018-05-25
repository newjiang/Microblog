package com.example.jiang.microblog.view.comment.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.CommentsBean;
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

    // at字符
    private static final char AT = '@';
    // at字符串
    private static final String AT_STRING = "@";
    // 冒号字符
    private static final char COLON = ':';
    // 冒号字符串
    private static final String COLON_STIRNG= ":";

    private Context context;

    private List<CommentsBean> beanList;

    // 声明一个接口的引用
    private OnItemClickListener onItemClickListener;

    // 构造函数
    public CommentAdapter(Context context, List<CommentsBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    //设置监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加新评论
     *
     * @param comment
     */
    public void add(CommentsBean comment) {
        beanList.add(comment);
        notifyDataSetChanged();
    }

    /**
     * 删除新评论
     *
     * @param comment
     */
    public void delete(CommentsBean comment) {
        if (comment != null) {
            for (int i = 0; i < beanList.size(); i++) {
                if (comment.getId() == beanList.get(i).getId()) {
                    beanList.remove(beanList.size() - i - 1);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void clear() {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 评论者头像
        CircleImageView header;
        // 评论者
        TextView commentator;
        // 评论内容
        TextView content;
        // 时间
        TextView time;
        // 来源
        TextView from;

        public ViewHolder(View itemView) {
            super(itemView);
            header = (CircleImageView) itemView.findViewById(R.id.commentator_header);
            commentator = (TextView) itemView.findViewById(R.id.commentator);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            time = (TextView) itemView.findViewById(R.id.comment_time);
            from = (TextView) itemView.findViewById(R.id.comment_sources);
            commentator = (TextView) itemView.findViewById(R.id.commentator);
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
        // 按照时间顺序显示，list倒序
        CommentsBean bean = beanList.get(beanList.size() - position - 1);

        Glide.with(context).load(bean.getUser().getAvatar_hd()).into(holder.header);
        holder.commentator.setText(bean.getUser().getName());

        // 关键字高亮
        if (bean.getText().contains(AT_STRING) && bean.getText().contains(COLON_STIRNG)) {
            SpannableStringBuilder sb = TextColorTools.highlight(bean.getText(), getKeyWord(bean.getText()));
            holder.content.setText(sb);
        } else {
            holder.content.setText(bean.getText());
        }
        holder.time.setText(getTimeFormat(bean.getCreated_at()));
        holder.from.setText(getFormFormat(bean.getSource()));
        holder.content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("删除该评论?");
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
                        if (deleteCommentListener != null) {
                            deleteCommentListener.onDeleteCommentListener(beanList.get(position).getId());
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });


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
        return beanList.size();
    }

    /**
     * 格式转化来源信息
     *
     * @param source
     * @return
     */
    private String getFormFormat(String source) {
        // FIXME 判断是否为null
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
        // 修改显示时间格式 如2018-01-01 00：00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String f = TimeFormat.format(d);
        if (f.equals(TimeFormat.FLAG)) {
            // 返回真正时间 如2018-1-1 00：00
            return format.format(d);
        } else {

            if (f.equals(TimeFormat.YESTERDAY)) {
                // 返回昨天 yyyy-MM-dd HH:mm
                return f + format.format(d);
            } else {
                // 返回xx秒前，xx分钟前，xx小时前
                return f;
            }
        }
    }

    // 点击事件监听器
    public interface OnItemClickListener {
        void OnItemclick(View view, int position);
    }

    //删除评论的接口
    public interface OnDeleteCommentListener{
        void onDeleteCommentListener(long cid);
    }

    public OnDeleteCommentListener deleteCommentListener;

    public void setDeleteCommentListener(OnDeleteCommentListener deleteCommentListener) {
        this.deleteCommentListener = deleteCommentListener;
    }
}
