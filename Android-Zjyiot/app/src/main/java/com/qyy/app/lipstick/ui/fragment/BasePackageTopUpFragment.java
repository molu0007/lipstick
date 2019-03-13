package com.qyy.app.lipstick.ui.fragment;

import com.qyy.app.lipstick.ui.activity.base.BaseFragment;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public abstract class BasePackageTopUpFragment extends BaseFragment {
    /**
     * 页码
     */
    private int mPageNo;
    @Override
    protected int getContentViewId() {
        return setContentViewId();
    }
    protected abstract int  setContentViewId();
    @Override
    protected void initData() {
        super.initData();

    }


}
