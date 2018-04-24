package com.example.jiang.microblog.view.comment;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.mvp.contract.CommentContract;
import com.example.jiang.microblog.mvp.presenter.CommentPresenter;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TimeFormat;
import com.example.jiang.microblog.view.adapter.NineImageAdapter;
import com.example.jiang.microblog.view.adapter.RetweetedImageAdapter;
import com.example.jiang.microblog.view.comment.adapter.CommentAdapter;
import com.example.jiang.microblog.view.comment.fragment.CommentDialogFragment;
import com.example.jiang.microblog.view.comment.fragment.DialogFragmentDataCallback;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity implements
        CommentContract.View, View.OnClickListener, DialogFragmentDataCallback {

    private CommentContract.Presenter presenter;

    //TODO 用户头像
    private CircleImageView userProfile;
    //TODO 用户名字
    TextView username;
    //TODO 用户备注信息
    TextView remark;
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
    //TODO 评论弹出的Dialog
    private CommentDialogFragment commentDialogFragment;
    //TODO 评论的adapter
    private CommentAdapter adapter;
    //TODO 该微博内容
    private Microblog.StatusesBean bean;
    //TODO 转发微博文字内容
    private TextView retweetedContent;
    //TODO 转发微博配图
    private NineGridImageView retweetedPicture;
    //TODO 该微博评论内容
    private List<Comment.CommentsBean> commentsBeen;
    //TODO 点击评论内容的位置
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        //TODO 获取传递过来的数据，减少请求次数
        String json = intent.getStringExtra(IntentKey.MICROBLOG_JSON);
        Gson gson = new Gson();
        bean = gson.fromJson(json, Microblog.StatusesBean.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(bean.getUser().getName());

        commentsBeen = new ArrayList<>();
        presenter = new CommentPresenter(this);
        if (commentsBeen.isEmpty()) {
            presenter.getComments(App.getToken().getToken(), bean.getMid(), 1);
        }
        initViews();
    }

    private void initViews() {
        initMicroblogView();
        commentTextView = (TextView) findViewById(R.id.tv_comment_fake_button);
        commentTextView.setOnClickListener(this);
        commentRecyclerview = (RecyclerView) findViewById(R.id.comment_recyclerview);
        commentDialogFragment = new CommentDialogFragment();
    }


    private void initDatas() {
        initMicroblogData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecyclerview.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(CommentActivity.this, commentsBeen);
        commentRecyclerview.setAdapter(adapter);
        commentRecyclerview.setFocusable(false);
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void OnItemclick(View view, int position) {
                currentPosition = commentsBeen.size() - position - 1;
                commentDialogFragment.show(getFragmentManager(), "CommentDialogFragment");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_fake_button:
                currentPosition = -1;
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

    @Override //TODO 模拟评论
    public void sendComment(String comment) {
        Comment.CommentsBean addComment = new Comment.CommentsBean();
        //TODO 头像
        addComment.setText(comment);
        Date date = new Date();
        //TODO 时间
        addComment.setCreated_at(new Date().toString());
        //TODO 来源
        Comment.CommentsBean.UserBeanX userBeanX = new Comment.CommentsBean.UserBeanX();
        //TODO 名字
        userBeanX.setName("嗯嗯");
        //TODO 头像
        userBeanX.setAvatar_hd("http://ww1.sinaimg.cn/crop.0.0.640.640.640/549d0121tw1egm1kjly3jj20hs0hsq4f.jpg");
        addComment.setUser(userBeanX);

        if (currentPosition == -1) {
            addComment.setSource("评论");
            commentsBeen.add(addComment);
            adapter.notifyDataSetChanged();
            commentRecyclerview.scrollToPosition(0);
            Toast.makeText(CommentActivity.this, comment, Toast.LENGTH_SHORT).show();
        } else {
            addComment.setSource("回复");
            commentsBeen.add(addComment);
            adapter.notifyDataSetChanged();
            commentRecyclerview.scrollToPosition(0);
            Comment.CommentsBean commentsBean = commentsBeen.get(currentPosition);
            String s = commentsBean.getText() + "-->" + comment;
            Toast.makeText(CommentActivity.this, s, Toast.LENGTH_SHORT).show();
        }
//TODO 模拟评论++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }

    @Override
    public void onSuccess(Object object) {
        Comment comment = (Comment) object;
        commentsBeen = comment.getComments();
        initDatas();
        Gson gson = new Gson();
        String toJson = gson.toJson(comment);
        Log.e("onSuccess", toJson);
    }

    @Override
    public void onError(String result) {
        Log.e("onError", result);
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

    //TODO 初始化微博视图
    private void initMicroblogView() {
        //TODO 用户头像
        userProfile = (CircleImageView) findViewById(R.id.microblog_user_proflie);
        //TODO 用户名字
        username = (TextView) findViewById(R.id.microblog_remark);
        //TODO 用户备注
        remark = (TextView) findViewById(R.id.microblog_username);
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
        //TODO 转发微博文字内容
        retweetedContent = (TextView) findViewById(R.id.retweeted_content);
        //TODO 转发微博配图
        retweetedPicture = (NineGridImageView) findViewById(R.id.retweeted_picture);
    }
    //TODO 初始化微博视图数据
    private void initMicroblogData() {
        //TODO 用户头像
        Glide.with(App.getContext()).load(bean.getUser().getProfile_image_url()).into(userProfile);
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
        //TODO 设置转发微博配图
        setRetweetedData(bean);
    }
    //TODO 设置转发的内容
    private void setRetweetedData(Microblog.StatusesBean bean) {
        if (bean.getRetweeted_status() != null) {
            //TODO 转发微博的内容
            retweetedContent.setText(bean.getRetweeted_status().getText());
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
    //TODO 格式转化来源信息
    private String getFormFormat(String source) {
        return Jsoup.parse(source).text();
    }
    //TODO 获取真正的时间并转化格式
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
}
