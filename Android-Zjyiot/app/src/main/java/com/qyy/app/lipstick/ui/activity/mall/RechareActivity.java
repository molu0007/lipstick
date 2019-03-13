package com.qyy.app.lipstick.ui.activity.mall;

import android.os.Bundle;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.MallApiService;
import com.qyy.app.lipstick.model.response.order.RechareGoods;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * <p>类说明</p>
 *
 * 充值
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-12 15:08
 * @name: RechareActivity
 */
public class RechareActivity extends BaseActivity{
    private MallApiService mMallApiService;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_rechare;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setCenterTitleText("充值");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mMallApiService= HttpManager.create(MallApiService.class);
        Call<RespInfo<List<RechareGoods>>> call=mMallApiService.getRechargeList();
        call.enqueue(new NetResponseCall<List<RechareGoods>>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<List<RechareGoods>>> call, List<RechareGoods> data) {

            }

            @Override
            protected void onFail(Call<RespInfo<List<RechareGoods>>> call, int type, String code, String tip) {

            }
        });
    }
}
