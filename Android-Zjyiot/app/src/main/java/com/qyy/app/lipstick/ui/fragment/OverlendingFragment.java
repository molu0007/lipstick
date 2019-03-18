package com.qyy.app.lipstick.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.TimeUtil;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.OrderRechareAdapter;
import com.qyy.app.lipstick.adapter.PollAdapter;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.response.OrderRecord;
import com.qyy.app.lipstick.model.response.home.OverlendingInfo;
import com.qyy.app.lipstick.ui.activity.base.BaseFragment;
import com.qyy.app.lipstick.ui.activity.home.WebViewActivity;
import com.qyy.app.lipstick.views.RecyclerViewDivider;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * @author dengwg
 * @date 2018/3/13
 */
public class OverlendingFragment extends BaseFragment {

    @BindView(R.id.rv_project)
    RecyclerView rvProject;
    private HomeApiService mHomeApiService;

    private CommonAdapter<OverlendingInfo> mInfoCommonAdapter;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_overlending;
    }
    private List<OverlendingInfo> mOverlendingInfos=new ArrayList<>();
    @Override
    protected void initView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initView(container, savedInstanceState);
        rvProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        mInfoCommonAdapter=new CommonAdapter<OverlendingInfo>(getActivity(),R.layout.item_overlend,mOverlendingInfos) {
            @Override
            protected void convert(ViewHolder holder, final OverlendingInfo overlendingInfo, int position) {
                Glide.with(getActivity()).load(overlendingInfo.getDkIcon()).into((ImageView) holder.getView(R.id.iv_icon));
                holder.setText(R.id.tv_name,overlendingInfo.getDkName());
                holder.setText(R.id.tv_limit,overlendingInfo.getDkRange());
                holder.setText(R.id.tv_appley,overlendingInfo.getDkApplyNum()+"人申请");
                holder.setOnClickListener(R.id.tv_get, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url",overlendingInfo.getDkUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        rvProject.addItemDecoration(new RecyclerViewDivider(getActivity(),LinearLayoutManager.VERTICAL,10,getResources().getColor(R.color.color_dddddd)));
        rvProject.setAdapter(mInfoCommonAdapter);
        hideToolbarNavigationIcon();
        setCenterTitleText("超贷");
    }


    @Override
    protected void initData() {
        super.initData();
        mHomeApiService = HttpManager.create(HomeApiService.class);
        getOverLendList();
    }

    private void getOverLendList() {
        final Call<RespInfo<List<OverlendingInfo>>> call = mHomeApiService.getOverlendList();
        call.enqueue(new NetResponseCall<List<OverlendingInfo>>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<List<OverlendingInfo>>> call, List<OverlendingInfo> data) {
                if (data!=null){
                    mOverlendingInfos.clear();
                    mOverlendingInfos.addAll(data);
                    mInfoCommonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFail(Call<RespInfo<List<OverlendingInfo>>> call, int type, String code, String tip) {

            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
    }


}
