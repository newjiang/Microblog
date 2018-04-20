package com.example.jiang.microblog.views.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TimeFormat;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.views.activity.ShowPictureActivity;
import com.example.jiang.microblog.views.comment.CommentActivity;
import com.example.jiang.microblog.views.profile.ProfileActivity;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.jiang.microblog.R.id.microblog_user_name;

/**
 * Created by jiang on 2018/4/14.
 */

public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    protected final List<Microblog.StatusesBean> mData;

    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewBaseAdapter(Context context, List<Microblog.StatusesBean> data) {
        this.context = context;
        this.mData = data;
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
        ((InnerHolder) holder).setData(mData.get(position), position);
    }


    /**
     * 反回条目的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //TODO 设置一个监听,其实,就是要设置一个接口,一个回调的接口
        this.mOnItemClickListener = listener;
    }


    /**
     * 编写回调的步骤
     * 1、创建这个接口
     * 2、定义借口内部的方法
     * 3、提供设置接口的方法(其实是外部实现)
     * 4、接口方法的调用
     */


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        //TODO 用户头像
        CircleImageView userProfile;
        //TODO 用户名字
        TextView username;
        //TODO 删除
        TextView delete;
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

        //TODO 位置
        private int mPosition;

        public InnerHolder(View itemView) {
            super(itemView);
            //TODO 用户头像
            userProfile = (CircleImageView) itemView.findViewById(R.id.microblog_user_proflie);
            //TODO 用户名字
            username = (TextView) itemView.findViewById(microblog_user_name);
            //TODO 删除
            delete = (TextView) itemView.findViewById(R.id.microblog_content_delete);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case microblog_user_name:
                            Log.e("vvvvvv", "microblog_user_name");
                            break;
                    }
                }
            });
        }

        /**
         * 这个方法用于设置数据
         */
        public void setData(final Microblog.StatusesBean bean, int position) {

            this.mPosition = position;
            //TODO 开始设置数据
            //TODO 用户头像
            Glide.with(App.getContext()).load(bean.getUser().getProfile_image_url()).into(userProfile);
            //TODO 用户名字
            username.setText(bean.getUser().getName());
            //TODO 微博文字内容
            content.setText(bean.getText());
            //TODO 是否删除
            delete.setText("");
            //TODO 微博内容
            content.setText(bean.getText());
            //TODO 发布时间
            time.setText(getTimeFormat(bean.getCreated_at())); //TODO holder.time.setText(bean.getCreated_at())
            //TODO 来源
            from.setText(getFormFormat(bean.getSource())); //TODO holder.from.setText(bean.getSource());
            //TODO 点赞数
            like.setText(String.valueOf(bean.getAttitudes_count()));
            //TODO 转发数
            redirect.setText(String.valueOf(bean.getReposts_count()));
            //TODO 评论数
            comment.setText(String.valueOf(bean.getComments_count()));
            //TODO 微博配图
            picture.setAdapter(new RecyclerViewBaseAdapter.NineImageAdapter());
            //TODO 微博配图数据源
            picture.setImagesData(mData.get(position).getPic_urls());

            //TODO 设置进入微博详情点击事件,根据微博内容
            setItemOnClickListener(username, bean);
            setItemOnClickListener(content, bean);
            setItemOnClickListener(time, bean);
            setItemOnClickListener(from, bean);
            setItemOnClickListener(comment, bean);
            setItemOnClickListener(commentImage, bean);

            //TODO 设置头像点击事件,根据用户id
            setProfileOnClickListener(userProfile, bean.getUser().getId());
        }

        /**
         * 设置控件的点击事件(进入微博详情，包括评论)
         * @param view
         * @param statuses
         */
        private void setItemOnClickListener(View view, final Microblog.StatusesBean statuses) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    //TODO 通过json传递过去
                    intent.putExtra(IntentKey.MICROBLOG_JSON, new Gson().toJson(statuses));
                    context.startActivity(intent);
                }
            });
        }

        /**
         * 点击头像事件
         * @param view
         * @param id
         */
        private void setProfileOnClickListener(View view, final long id) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra(IntentKey.ACCOUNT_ID, id);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String f = TimeFormat.format(d);
        if (f.equals(TimeFormat.FLAG)) {
            //TODO 返回真正时间 如2018-1-1 01：01：01
            return format.format(d);
        } else {
            //TODO 返回xx秒前，xx分钟前，xx小时前，昨天
            return f;
        }
    }

    /**
     * 九宫格图片适配器
     */
    public class NineImageAdapter extends NineGridImageViewAdapter<Microblog.StatusesBean.PicUrlsBeanX> {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, Microblog.StatusesBean.PicUrlsBeanX picUrlsBeanX) {
            //FIXME 主页显示thumbnail级别的图改为bmiddle级别
            String bmiddle_pic = picUrlsBeanX.getThumbnail_pic().replaceAll("thumbnail", "bmiddle");
            Glide.with(context).load(bmiddle_pic).into(imageView);
        }


        @Override
        protected boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<Microblog.StatusesBean.PicUrlsBeanX> list) {
            return super.onItemImageLongClick(context, imageView, index, list);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<Microblog.StatusesBean.PicUrlsBeanX> list) {
            ArrayList<String> stringList = new ArrayList<>();
            for (Microblog.StatusesBean.PicUrlsBeanX x : list) {
                stringList.add(x.getThumbnail_pic());
            }
            Intent intent = new Intent(context, ShowPictureActivity.class);
            intent.putStringArrayListExtra(IntentKey.MICROBLOG_PICTURE, stringList);
            intent.putExtra(IntentKey.MICROBLOG_PICTURE_NUMBER, index);
            context.startActivity(intent);
        }
    }

}
