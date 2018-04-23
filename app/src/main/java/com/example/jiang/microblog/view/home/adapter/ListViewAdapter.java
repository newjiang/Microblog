package com.example.jiang.microblog.view.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Microblog;

import java.util.List;

public class ListViewAdapter extends RecyclerViewBaseAdapter {

    private Context context;

    //TODO 普通的条目类型
    public static final int TYPE_NORMAL = 0;
    //TODO 加载更多
    public static final int TYPE_LOADER_MORE = 1;

    private OnRefreshListener refreshListener;

    public ListViewAdapter(Context context, List<Microblog.StatusesBean> data) {
        super(context,data);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = getSubView(parent, viewType);
        if (viewType == TYPE_NORMAL) {
            return new InnerHolder(view);
        } else {
            return new LoaderMoreHolder(view);
        }
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view;
        //TODO 根据类型来创建view
        if (viewType == TYPE_NORMAL) {
            //TODO 加载微博内容
            view = View.inflate(parent.getContext(), R.layout.home_microblog_view, null);
        } else {
            //TODO 这个是加载更多的
            view = View.inflate(parent.getContext(), R.layout.item_load_layout, null);
        }
        return view;
    }

    /**
     * 这个方法是用于绑定holder的,一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof InnerHolder) {
            //TODO 在这里设置数据
            ((InnerHolder) holder).setData(beanList.get(position), position);
        } else if (getItemViewType(position) == TYPE_LOADER_MORE && holder instanceof LoaderMoreHolder) {
            ((LoaderMoreHolder) holder).update(LoaderMoreHolder.LOADER_STATE_LOADING);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //TODO 最后一个则返回加载更多
            return TYPE_LOADER_MORE;
        }
        return TYPE_NORMAL;
    }

    /**
     * 设置刷的监听的接口
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.refreshListener = listener;
    }

    //TODO 定义接口
    public interface OnRefreshListener {
        void onUpPullRefresh(LoaderMoreHolder loaderMoreHolder);
    }

    public class LoaderMoreHolder extends RecyclerView.ViewHolder {

        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;

        private LinearLayout linearLayout;
        private TextView mReLoad;

        public LoaderMoreHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.loading);
            mReLoad = (TextView) itemView.findViewById(R.id.reload);

            mReLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 这里面要去触发加载数据
                    update(LOADER_STATE_LOADING);
                }
            });
        }


        public void update(int state) {

            //TODO 重置控件的状态
            linearLayout.setVisibility(View.GONE);
            mReLoad.setVisibility(View.GONE);

            switch (state) {
                case LOADER_STATE_LOADING:
                    linearLayout.setVisibility(View.VISIBLE);
                    //TODO 触发加载数据
                    startLoaderMore();
                    break;

                case LOADER_STATE_RELOAD:
                    mReLoad.setVisibility(View.VISIBLE);
                    break;

                case LOADER_STATE_NORMAL:
                    linearLayout.setVisibility(View.GONE);
                    mReLoad.setVisibility(View.GONE);
                    break;
            }
        }

        private void startLoaderMore() {
            if (refreshListener != null) {
                refreshListener.onUpPullRefresh(this);
            }
        }
    }
}
