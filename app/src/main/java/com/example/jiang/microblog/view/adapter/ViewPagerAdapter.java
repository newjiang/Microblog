package com.example.jiang.microblog.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jiang.microblog.base.BaseFragment;

import java.util.List;

/**
 * Created by jiang on 2018/4/14.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> navList;

    private List<BaseFragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, List<String> navList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.navList = navList;
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
        return navList.get(position);
    }
}
