package com.qyy.app.lipstick.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.qyy.app.lipstick.ui.activity.base.BaseFragment;
import com.qyy.app.lipstick.ui.fragment.MyFragment;
import com.qyy.app.lipstick.ui.fragment.FragmentHelper;
import com.qyy.app.lipstick.ui.fragment.HomeFragment;
import com.qyy.app.lipstick.ui.fragment.OverlendingFragment;

public class MainActivity extends BaseActivity {
    MainFragmentHelper mFragmentHelper;
    private RadioGroup mRadioGroup;



    @Override
    protected void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_tab_home:
                        mFragmentHelper.toggleFragment(TAG_HOME);
                        break;
                    case R.id.rb_tab_top_up:
                        mFragmentHelper.toggleFragment(TAG_TOP_UP);
                        break;
                    case R.id.rb_tab_about:
                        mFragmentHelper.toggleFragment(TAG_ABOUT);
                        break;
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRadioGroup= (RadioGroup) findViewById(R.id.rg_tab);
        mFragmentHelper = new MainFragmentHelper(getSupportFragmentManager(), R.id.fl_main);
        mFragmentHelper.toggleFragment(TAG_HOME);
    }



    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }



    /**
     * 主界面的fragment帮助类
     */
    /**
     * 发现
     */
    public static final String TAG_HOME = "TAG_HOME";
    /**
     * 场馆
     */
    public static final String TAG_TOP_UP = "TAG_TOP_UP";
    /**
     * 赛事
     */
    public static final String TAG_ABOUT = "TAG_ABOUT";
    private class MainFragmentHelper extends FragmentHelper {

        public MainFragmentHelper(FragmentManager fm, int container) {
            super(fm, container);
        }

        @Override
        protected Fragment getFragmentByTag(String tag) {
            BaseFragment fragment = null;
            switch (tag) {
                case TAG_HOME:
                    fragment = new HomeFragment();
                    break;
                case TAG_TOP_UP://圈子
                    fragment = new OverlendingFragment();
                    break;
                case TAG_ABOUT:
                    fragment = new MyFragment();
                    break;
            }
            return fragment;
        }
    }

}
