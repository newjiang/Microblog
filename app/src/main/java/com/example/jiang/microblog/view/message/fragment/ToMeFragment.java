package com.example.jiang.microblog.view.message.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Comment;
import com.example.jiang.microblog.bean.CommentsBean;
import com.example.jiang.microblog.mvp.contract.CommentContract;
import com.example.jiang.microblog.mvp.presenter.CommentPresenter;
import com.example.jiang.microblog.view.message.adapter.CommentListViewAdapter;
import com.example.jiang.microblog.view.message.adapter.CommentRecyclerViewAdapter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class ToMeFragment extends BaseFragment implements CommentContract.View {
    private static final String TAG = ToMeFragment.class.getSimpleName();
    private CommentPresenter presenter;
    private Oauth2AccessToken token;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar loadingBar;
    private CommentRecyclerViewAdapter adapter;
    private List<CommentsBean> commentsBeen = new ArrayList<>();

    private CommentListViewAdapter.LoaderMoreHolder loaderHolder;

    private boolean isDown = true;          //TODO 判断是否下拉操作
    private boolean isRefreshing = false;  //TODO 是否正在刷新
    private int page = 2;                    //TODO 上拉操作的起始页

    @Override
    public View initView() {
        Log.e(TAG, TAG + " ...init ...");
        token = AccessTokenKeeper.readAccessToken(context);
        presenter = new CommentPresenter(this, context);
        View view = View.inflate(context, R.layout.fragment_at_me_weibos, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        //TODO 下拉刷新
        downPullUpdate();
        return view;
    }

    @Override
    public void initData() {
        if (commentsBeen.isEmpty()) {
            Log.e(TAG,"第一次请求");
            presenter.toMeComment(token.getToken(), 1);
        }
    }

    @Override
    public void onSuccess(Object object) {
        Log.e("onSuccess","onSuccess");
        Comment comment = (Comment) object;
        List<CommentsBean> comments = comment.getComments();
        if (commentsBeen.isEmpty()) {
            commentsBeen = comments;
            setListView();
        }
    }

    @Override
    public void onError(String result) {
        Log.e("onError",result);
    }

    private void setListView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        adapter = new CommentListViewAdapter(context, commentsBeen);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        loadingBar.setVisibility(View.GONE);
        handlerUpPullUpdate();
    }

    //TODO 下拉刷新
    private void downPullUpdate() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setEnabled(true);
        refreshLayout.setProgressViewOffset(true, 0, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    presenter.getAtMeComment(token.getToken(), 1);
                    isRefreshing = true;
                    isDown = true;
                }
            }
        });
    }

    //TODO 上拉刷新
    private void handlerUpPullUpdate() {
        if (adapter instanceof CommentListViewAdapter) {
            ((CommentListViewAdapter) adapter).setOnRefreshListener(new CommentListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(CommentListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    loaderHolder = loaderMoreHolder;
                    if (!isRefreshing) {
                        presenter.getAtMeComment(token.getToken(), page);
                        isRefreshing = true;
                        isDown = false;
                        page++;
                    }
                }
            });
        }
    }
}
