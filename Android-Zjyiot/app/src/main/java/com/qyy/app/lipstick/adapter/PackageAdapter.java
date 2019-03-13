package com.qyy.app.lipstick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public class PackageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public PackageAdapter(FragmentManager fm,  List<Fragment> mFragments) {
        super(fm);
        this.mFragments=mFragments;
    }

    @Override
    public Fragment getItem(int position) {//必须实现
//        Fragment fragment=PackageTopUpFragment.newInstance(position);
        return mFragments.get(position);
    }

    @Override
    public int getCount() {//必须实现
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {//选择性实现
        return mFragments.get(position).getClass().getSimpleName();
    }
}

