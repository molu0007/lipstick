package com.qyy.app.lipstick.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.event.EventType;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.model.response.home.UserInfo;
import com.qyy.app.lipstick.ui.activity.base.BaseFragment;
import com.qyy.app.lipstick.ui.activity.login.LoginActivity;
import com.qyy.app.lipstick.ui.activity.mall.OrderActivity;
import com.qyy.app.lipstick.ui.activity.mall.QrCodeActivity;
import com.qyy.app.lipstick.ui.activity.mall.RechareActivity;
import com.qyy.app.lipstick.utils.PrefsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author dengwg
 * @date 2018/3/13
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView mTextViewName;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.ll_rechare)
    LinearLayout llRechare;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.ll_out_login)
    LinearLayout llOutLogin;
    HomeApiService mHomeApiService;
    UserInfo mUserInfo;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData() {
        super.initData();

        mHomeApiService= HttpManager.create(HomeApiService.class);
        final retrofit2.Call<RespInfo<UserInfo>> call = mHomeApiService.getUserInfo();
        call.enqueue(new NetResponseCall<UserInfo>(this) {
            @Override
            protected void onSuccess(retrofit2.Call<RespInfo<UserInfo>> call, UserInfo data) {
                if (data!=null){
                    Glide.with(getActivity()).load(data.getAvatar()).into(ivHeader);
                    mTextViewName.setText(data.getNickname());
                    mUserInfo=data;
                    tvBalance.setText("积分余额："+data.getJifen()+"");
                }
            }

            @Override
            protected void onFail(retrofit2.Call<RespInfo<UserInfo>> call, int type, String code, String tip) {

            }
        });
    }

    @Override
    protected void onMessageReceived(EventType what, Object event) {
        super.onMessageReceived(what, event);
        if (what==EventType.REFRESH_JIFEN){
            tvBalance.setText("积分余额："+((UserInfo)event).getJifen()+"");
        }
    }

    @OnClick({R.id.ll_order, R.id.ll_rechare, R.id.ll_service, R.id.ll_out_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_order:
                Intent intentOrder=new Intent(getActivity(), OrderActivity.class);
                startActivity(intentOrder);
                break;
            case R.id.ll_rechare:
                Intent intentRechare=new Intent(getActivity(), RechareActivity.class);
                if (mUserInfo!=null)
                intentRechare.putExtra("Balance",mUserInfo.getJifen());
                startActivity(intentRechare);
                break;
            case R.id.ll_service:
                Intent intentService=new Intent(getActivity(), QrCodeActivity.class);
                startActivity(intentService);
                break;
            case R.id.ll_out_login:
                PrefsUtil.setToken("");
                getActivity().finish();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
