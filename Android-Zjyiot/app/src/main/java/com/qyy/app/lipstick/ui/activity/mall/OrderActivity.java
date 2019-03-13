package com.qyy.app.lipstick.ui.activity.mall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.MallApiService;
import com.qyy.app.lipstick.model.response.home.GoodsBean;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.model.response.order.OrderEntry;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * <p>类说明</p>
 * 我的订单
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-12 15:10
 * @name: OrderActivity
 */
public class OrderActivity extends BaseActivity {
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;

    List<OrderEntry.ListBean> mOrderEntries=new ArrayList<>();
    CommonAdapter<OrderEntry.ListBean> mCommonAdapter;
    MallApiService mMallApiService;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setCenterTitleText("我的订单");
        mCommonAdapter=new CommonAdapter<OrderEntry.ListBean>(this,R.layout.item_order,mOrderEntries) {
            @Override
            protected void convert(ViewHolder holder, OrderEntry.ListBean goodsBean, int position) {
                holder.setText(R.id.tv_date,goodsBean.getAddTime());
                Glide.with(OrderActivity.this).load(goodsBean.getGood().getPrimaryPicUrl()).into((ImageView) holder.getView(R.id.iv_goods));
                holder.setText(R.id.tv_goods_name,goodsBean.getGood().getName());
                holder.setText(R.id.tv_goods_des,goodsBean.getGood().getBrand());
                holder.setText(R.id.tv_goods_price,"专柜价"+goodsBean.getGood().getCounterPrice()+"");
                //,0领取口红,1代发货,2已发货,3确认收货
                if (goodsBean.getOrderStatus()==0){
                    holder.setBackgroundRes(R.id.tv_state,R.drawable.shape_bt_bg_1);
                    holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.white));
                    holder.setText(R.id.tv_state,"领取口红");
                }else if (goodsBean.getOrderStatus()==1){
                    holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.color_666666));
                    holder.setText(R.id.tv_state,"代发货");
                    holder.setBackgroundRes(R.id.tv_state,R.drawable.shape_bt_bg);
                }else if (goodsBean.getOrderStatus()==2){
                    holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.color_666666));
                    holder.setBackgroundRes(R.id.tv_state,R.drawable.shape_bt_bg);
                    holder.setText(R.id.tv_state,"已发货");
                }else {
                    holder.setTextColor(R.id.tv_state,getResources().getColor(R.color.color_666666));
                    holder.setBackgroundRes(R.id.tv_state,R.drawable.shape_bt_bg);
                    holder.setText(R.id.tv_state,"确认收货");
                }
            }
        };
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(mCommonAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mMallApiService= HttpManager.create(MallApiService.class);
        getOrderList();
    }

    private void getOrderList() {
        final Call<RespInfo<OrderEntry>> call = mMallApiService.getOrderList();
        call.enqueue(new NetResponseCall<OrderEntry>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<OrderEntry>> call, OrderEntry data) {
                LogUtil.d(data.toString());
                if (data!=null&&data.getList()!=null){
                    mOrderEntries.clear();
                    mOrderEntries.addAll(data.getList());
                    mCommonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFail(Call<RespInfo<OrderEntry>> call, int type, String code, String tip) {

            }
        });
    }
}
