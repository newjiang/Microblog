package com.example.jiang.microblog.view.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.App;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.base.Constants;
import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.mvp.contract.CommentContract;
import com.example.jiang.microblog.mvp.contract.FavoriteContract;
import com.example.jiang.microblog.mvp.presenter.CommentPresenter;
import com.example.jiang.microblog.mvp.presenter.FavoritePresenter;
import com.example.jiang.microblog.utils.DialogFragmentDataCallback;
import com.example.jiang.microblog.utils.IntentKey;
import com.example.jiang.microblog.utils.TimeFormat;
import com.example.jiang.microblog.view.adapter.NineImageAdapter;
import com.example.jiang.microblog.view.adapter.RetweetedImageAdapter;
import com.example.jiang.microblog.view.comment.adapter.CommentAdapter;
import com.example.jiang.microblog.view.comment.fragment.CommentDialogFragment;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.web.WeiboPageUtils;
import com.zhy.changeskin.SkinManager;

import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends BaseActivity implements
        CommentContract.View, View.OnClickListener, DialogFragmentDataCallback,
        FavoriteContract.View {

    private static final String FRAGMENT = "CommentDialogFragment";

    private CommentContract.Presenter presenter;
    private FavoriteContract.Presenter favoritePresenter;

    private WeiboPageUtils weiboPageUtils;
    private Oauth2AccessToken token;
    private AuthInfo mAuthInfo;
    // 用户头像
    private CircleImageView userProfile;
    // 用户名字
    TextView username;
    // 用户备注信息
    TextView remark;
    // 微博文字内容
    private TextView content;
    // 微博配图
    private NineGridImageView picture;
    // 发布时间
    private TextView time;
    // 来源
    private TextView from;
    // 点赞数
    private TextView like;
    // 转发数
    private TextView redirect;
    // 评论数
    private TextView commentText;
    // 点赞图标
    private ImageView likeImage;
    // 转发图标
    private ImageView redirectImage;
    // 评论图标
    private ImageView commentImage;
    // 收藏图标
    private ImageView favorited;
    // 评论提示文本
    private TextView commentTextView;
    // 微博评论的RecyclerView
    private RecyclerView commentRecyclerview;
    // 评论弹出的Dialog
    private CommentDialogFragment commentDialogFragment;
    // 评论的adapter
    private CommentAdapter adapter;
    // 该微博内容
    private Statuses statuses;
    // 转发微博文字内容
    private TextView retweetedContent;
    // 转发微博配图
    private NineGridImageView retweetedPicture;

    //TODO  该微博评论内容
    private List<CommentsBean> commentsBeen = new ArrayList<>();
    //TODO 点击评论内容的位置
    private int currentPosition;
    //TODO 是否是添加新的评论
    private boolean isAdd = true;
    //TODO 返回的数据是否是List
    private boolean isList = true;
    private ActionBar actionBar;

    private boolean isFavouritesOption = false;
    private boolean isRefresh = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        weiboPageUtils = WeiboPageUtils.getInstance(this, mAuthInfo);
        token = AccessTokenKeeper.readAccessToken(this);
        presenter = new CommentPresenter(this);
        favoritePresenter = new FavoritePresenter(this);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String mid = intent.getStringExtra(IntentKey.MICROBLOG_MID);
        if (mid == null || "".equals(mid)) {
            //从主页打开
            getUserInfo();
            if (commentsBeen.isEmpty()) {
                presenter.getComments(token.getToken(), statuses.getMid(), 1);
            }
        } else {
            //从搜索页打开
            if (commentsBeen.isEmpty()) {
                presenter.getComments(token.getToken(), mid, 1);
            }
        }
    }

    @Override
    public void onSuccess(Object object) {
        // 判断是否是收藏操作，是则跳出
        if (isFavouritesOption) {
            isFavouritesOption = false;
            return;
        }
        //判断是否是初始化
        if (commentsBeen.isEmpty() && isList) {
            Comment comment = (Comment) object;
            commentsBeen = comment.getComments();
            if (statuses == null) {
                statuses = comment.getStatus();
                actionBar.setTitle(statuses.getUser().getName());
            }
            initViews();
            initDatas();
        } else {
            //不是初始化，判断是否是添加评论
            if (isAdd) {
                if (object != null) {
                    //添加评论
                    if (isRefresh) {
                        //清空评论数
                        commentsBeen.clear();
                        adapter.clear();
                        isRefresh = false;
                        //请求获取第一条评论
                        presenter.getComments(token.getToken(), statuses.getMid(), 1);
                    } else {
                        //获取到最新评论数据
                        Comment comment = (Comment) object;
                        commentsBeen.addAll(comment.getComments());
                        //刷新显示
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
                // 删除评论
                CommentsBean bean = null;
                Log.e("收到评论数据", "8");
                try {
                    bean = (CommentsBean) object;
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
                adapter.delete(bean);
            }
        }
    }

    @Override
    public void onError(String result) {
        if ("HTTP 403 Forbidden".equals(result)) {
            Toast.makeText(this, "访问次数已用完", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取传递过来用户数据，减少请求次数
     */
    private void getUserInfo() {
        Intent intent = getIntent();
        String json = intent.getStringExtra(IntentKey.MICROBLOG_JSON);
        Gson gson = new Gson();
        statuses = gson.fromJson(json, Statuses.class);
        actionBar.setTitle(statuses.getUser().getName());
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
                commentDialogFragment.show(getFragmentManager(), FRAGMENT);
            }
        });
        adapter.setDeleteCommentListener(new CommentAdapter.OnDeleteCommentListener() {
            @Override
            public void onDeleteCommentListener(long cid) {
                presenter.destroy(token.getToken(), cid);
                isAdd = false;
                isList = false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_fake_button:
                currentPosition = -1;
                commentDialogFragment.show(getFragmentManager(), FRAGMENT);
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
    public void sendComment(String comment) {
        if (currentPosition == -1) {
            //TODO 添加新评论
            try {
                presenter.create(token.getToken(), comment, statuses.getId());
                isAdd = true;
                isList = false;
                isRefresh = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //TODO　回复
            try {
                presenter.reply(token.getToken(),
                        commentsBeen.get(currentPosition).getId(),
                        statuses.getId(),
                        comment);
                isAdd = true;
                isList = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 初始化微博视图
    private void initMicroblogView() {
        // 用户头像
        userProfile = (CircleImageView) findViewById(R.id.microblog_user_proflie);
        // 用户名字
        username = (TextView) findViewById(R.id.microblog_remark);
        // 用户备注
        remark = (TextView) findViewById(R.id.microblog_username);
        // 微博文字内容
        content = (TextView) findViewById(R.id.microblog_content);
        // 微博配图
        picture = (NineGridImageView) findViewById(R.id.microblog_picture);
        // 发布时间
        time = (TextView) findViewById(R.id.microblog_time);
        // 来源
        from = (TextView) findViewById(R.id.microblog_from);
        // 点赞数
        like = (TextView) findViewById(R.id.attitudes_count);
        // 转发数
        redirect = (TextView) findViewById(R.id.reposts_count);
        // 评论数
        commentText = (TextView) findViewById(R.id.comments_count);
        // 点赞图标
        likeImage = (ImageView) findViewById(R.id.attitudes_count_iv);
        // 转发图标
        redirectImage = (ImageView) findViewById(R.id.reposts_count_iv);
        // 评论图标
        commentImage = (ImageView) findViewById(R.id.comments_count_iv);
        // 收藏图标
        favorited = (ImageView) findViewById(R.id.favorited_iv);
        // 转发微博文字内容
        retweetedContent = (TextView) findViewById(R.id.retweeted_content);
        // 转发微博配图
        retweetedPicture = (NineGridImageView) findViewById(R.id.retweeted_picture);
        initMicroblogData();
    }

    // 初始化微博视图数据
    private void initMicroblogData() {
        // 用户头像
        Glide.with(App.getContext()).load(statuses.getUser().getProfile_image_url()).into(userProfile);
        if (statuses.getUser().getRemark().equals("") || statuses.getUser().getRemark() == null) {
            // 用户备注显示用户的名字
            remark.setText(statuses.getUser().getRemark());
            // 用户名字内容是空
            username.setText(statuses.getUser().getName());
        } else {
            // 用户备注
            remark.setText(statuses.getUser().getName());
            // 用户名字
            username.setText(statuses.getUser().getRemark());
        }
        // 微博文字内容
        content.setText(statuses.getText());
        // 微博内容
        content.setText(statuses.getText());
        // 发布时间
        time.setText(getTimeFormat(statuses.getCreated_at()));
        // 来源
        from.setText(getFormFormat(statuses.getSource()));
        // 点赞数
        like.setText(String.valueOf(statuses.getAttitudes_count()));
        // 转发数
        redirect.setText(String.valueOf(statuses.getReposts_count()));
        // 评论数
        commentText.setText(String.valueOf(statuses.getComments_count()));
        if (statuses.isFavorited()) {
            favorited.setImageResource(R.drawable.ic_favorited);
        } else {
            favorited.setImageResource(R.drawable.ic_favorite_border);
        }
        // 微博配图
        picture.setAdapter(new NineImageAdapter());
        // 微博配图数据源
        picture.setImagesData(statuses.getPic_urls());
        // 设置转发微博配图
        setRetweetedData(statuses);

        favorited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavouritesOption = true;
                Log.e("评论收藏收藏", token.getToken() + "|" + statuses.getId());
                if (statuses.isFavorited()) {
                    favorited.setImageResource(R.drawable.ic_favorite_border);
                    favoritePresenter.destroyFavorites(token.getToken(), statuses.getId());
                    statuses.setFavorited(false);
                } else {
                    favorited.setImageResource(R.drawable.ic_favorited);
                    favoritePresenter.createFavorites(token.getToken(),statuses.getId());
                    statuses.setFavorited(true);
                }
            }
        });
    }

    /**
     * 设置转发的内容
     * @param bean
     */
    private void setRetweetedData(Statuses bean) {
        if (bean.getRetweeted_status() != null) {
            // 转发微博的内容
            retweetedContent.setText(bean.getRetweeted_status().getText());
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
     * 格式转化来源信息
     * @param source
     * @return
     */
    private String getFormFormat(String source) {
        if (source == null || "".equals(source)) {
            return source;
        } else {
            return Jsoup.parse(source).text();
        }
    }

    /**
     * 获取真正的时间并转化格式
     * @param time
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.microblog_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.to_like:
                weiboPageUtils.startWeiboDetailPage(statuses.getMid(), statuses.getUser().getIdstr());
                break;
            case R.id.to_comment:
                weiboPageUtils.commentWeibo(statuses.getIdstr());
                break;
            case R.id.to_share:
                weiboPageUtils.shareToWeibo("转发微博");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
