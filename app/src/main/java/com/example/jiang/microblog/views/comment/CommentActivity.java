package com.example.jiang.microblog.views.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.base.IntentKey;
import com.example.jiang.microblog.base.TimeFormat;
import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.json.CommentJson;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.views.activity.ShowPictureActivity;
import com.example.jiang.microblog.views.comment.adapter.CommentAdapter;
import com.example.jiang.microblog.views.comment.fragment.CommentDialogFragment;
import com.example.jiang.microblog.views.comment.fragment.DialogFragmentDataCallback;
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

public class CommentActivity extends AppCompatActivity implements
        MicroblogContract.View, View.OnClickListener, DialogFragmentDataCallback {
    //TODO 用户头像
    private CircleImageView userProfile;
    //TODO 用户名字
    private TextView username;
    //TODO 删除
    private TextView delete;
    //TODO 微博文字内容
    private TextView content;
    //TODO 微博配图
    private NineGridImageView picture;
    //TODO 发布时间
    private TextView time;
    //TODO 来源
    private TextView from;
    //TODO 点赞数
    private TextView like;
    //TODO 转发数
    private TextView redirect;
    //TODO 评论数
    private TextView comment;
    //TODO 点赞图标
    private ImageView likeImage;
    //TODO 转发图标
    private ImageView redirectImage;
    //TODO 评论图标
    private ImageView commentImage;
    //TODO 评论提示文本
    private TextView commentTextView;
    //TODO 微博评论的RecyclerView
    private RecyclerView commentRecyclerview;

    private CommentAdapter adapter;

    //TODO 该微博内容
    private Microblog.StatusesBean bean;
    //TODO 该微博评论内容
    private List<Comment.CommentsBean> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        String json = intent.getStringExtra(IntentKey.MICROBLOG_JSON);
        Gson gson = new Gson();
        bean = gson.fromJson(json, Microblog.StatusesBean.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        initDatas();
    }

    private void initDatas() {
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
        picture.setImagesData(bean.getPic_urls());

        comments = new ArrayList<>();
        Gson gson = new Gson();
        Comment comment = gson.fromJson(CommentJson.JSON, Comment.class);
        comments = comment.getComments();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecyclerview.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(CommentActivity.this, comments);
        commentRecyclerview.setAdapter(adapter);
    }

    private void initViews() {
        commentTextView = (TextView) findViewById(R.id.tv_comment_fake_button);
         //TODO 用户头像
        userProfile = (CircleImageView) findViewById(R.id.microblog_user_proflie);
         //TODO 用户名字
        username = (TextView) findViewById(microblog_user_name);
         //TODO 删除
        delete = (TextView) findViewById(R.id.microblog_content_delete);
         //TODO 微博文字内容
        content = (TextView) findViewById(R.id.microblog_content);
         //TODO 微博配图
        picture = (NineGridImageView) findViewById(R.id.microblog_picture);
         //TODO 发布时间
        time = (TextView) findViewById(R.id.microblog_time);
         //TODO 来源
        from = (TextView) findViewById(R.id.microblog_from);
         //TODO 点赞数
        like = (TextView) findViewById(R.id.attitudes_count);
         //TODO 转发数
        redirect = (TextView) findViewById(R.id.reposts_count);
         //TODO 评论数
        comment = (TextView) findViewById(R.id.comments_count);
         //TODO 点赞图标
        likeImage = (ImageView) findViewById(R.id.attitudes_count_iv);
         //TODO 转发图标
        redirectImage = (ImageView) findViewById(R.id.reposts_count_iv);
         //TODO 评论图标
        commentImage = (ImageView) findViewById(R.id.comments_count_iv);

        commentTextView.setOnClickListener(this);

        commentRecyclerview = (RecyclerView) findViewById(R.id.comment_recyclerview);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_fake_button:
                CommentDialogFragment commentDialogFragment = new CommentDialogFragment();
                commentDialogFragment.show(getFragmentManager(), "CommentDialogFragment");
                break;
            default:
                break;
        }
    }

    @Override
    public String getCommentText() {
        return commentTextView.getText().toString();
    }

    @Override
    public void setCommentText(String commentTextTemp) {
        Log.e("setCommentText", commentTextTemp);
        commentTextView.setText(commentTextTemp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(String result) {

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
