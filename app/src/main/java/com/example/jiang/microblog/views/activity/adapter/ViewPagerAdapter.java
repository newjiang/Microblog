package com.example.jiang.microblog.views.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jiang.microblog.base.BaseFragment;

import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
