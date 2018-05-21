package com.example.jiang.microblog.view.profile.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseFragment;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.PicUrlsBean;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.mvp.contract.MicroblogContract;
import com.example.jiang.microblog.mvp.presenter.MicroblogPresenter;
import com.example.jiang.microblog.view.profile.AlbumAdapter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class AlbumFragment extends BaseFragment implements MicroblogContract.View {

    private static final String TAG = AlbumFragment.class.getSimpleName();

    private MicroblogContract.Presenter presenter;

    private Oauth2AccessToken token;

    private RecyclerView recyclerView;
    private ProgressBar loadingBar;
    private AlbumAdapter albumAdapter;

    private List<PicUrlsBean> picUrlsBeen = new ArrayList<>();
    @Override
    public View initView() {
        token = AccessTokenKeeper.readAccessToken(context);
        presenter = new MicroblogPresenter(this, context);
        View view = View.inflate(context, R.layout.fragment_album, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        return view;
    }

    @Override
    public void initData() {
        if (picUrlsBeen.isEmpty()) {
            presenter.user_timeline(token.getToken(), 1, 2);
        }
    }

    @Override
    public void onSuccess(Object object) {
        Microblog microblog = (Microblog) object;
        List<Statuses> list = microblog.getStatuses();
        for (Statuses s : list) {
            if (s.getPic_urls() != null) {
                picUrlsBeen.addAll(s.getPic_urls());
            }
        }
        Log.e("picUrlsBeen", picUrlsBeen.toString());
        initRecyclerView();
    }

    private void initRecyclerView() {
        albumAdapter = new AlbumAdapter(context, picUrlsBeen);
        recyclerView.setAdapter(albumAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String result) {
        Log.e("MicroblogFragment-E", result);
    }

}
