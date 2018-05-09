package com.example.jiang.microblog.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiang on 2018/4/14.
 */

public abstract class BaseFragment extends Fragment{

    protected Context context;

    /**
     * 当该类被系统创建的时候被回调
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 抽象类，子类实现，实现子类所需要的效果
     * @return
     */
    public abstract View initView() ;

    /**
     * Activity创建时回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 子类需要联网请求数据时，重写该方法，用于联网请求
     */
    public void initData() {

    }
}
