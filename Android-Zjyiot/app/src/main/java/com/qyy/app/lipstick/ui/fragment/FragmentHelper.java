package com.qyy.app.lipstick.ui.fragment;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author dengwg
 * @date 2018/3/13
 */

public abstract class FragmentHelper {

    private String mCurShow = "";

    private FragmentManager mFm;

    private int mContainer;

    public FragmentHelper(FragmentManager fm, @LayoutRes int container) {
        this.mFm = fm;
        mContainer = container;
    }

    /**
     * 切换要显示的fragment
     *
     * @param tag 标签
     */
    public void toggleFragment(String tag) {
        if(tag.equals(mCurShow))
            return;
        FragmentTransaction ft = mFm.beginTransaction();
        //隐藏当前显示的fragment
        Fragment curFragment = mFm.findFragmentByTag(mCurShow);
        if(curFragment!=null){
            ft.hide(curFragment);
        }
        Fragment fragment = mFm.findFragmentByTag(tag);
        if (fragment == null) {
            //如果栈内没有-->添加
            ft.add(mContainer, getFragmentByTag(tag), tag);
        } else {
            //如果站内有-->显示
            ft.show(fragment);
        }
        ft.commitAllowingStateLoss();
        mCurShow = tag;
    }

    /**
     * 获取当前显示的fragmetn标签
     * @return
     */
    public String getCurShow() {
        return mCurShow;
    }

    /**
     * 根据tag创建fragment
     *
     * @param tag
     * @return
     */
    protected abstract Fragment getFragmentByTag(String tag);

}
