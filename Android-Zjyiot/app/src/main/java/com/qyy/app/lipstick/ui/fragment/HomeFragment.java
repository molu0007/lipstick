package com.qyy.app.lipstick.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.home.UrlImgAdapter;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.response.home.GoodsBean;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.ui.activity.base.BaseFragment;
import com.qyy.app.lipstick.views.GridDivider;
import com.qyy.app.lipstick.views.RecyclerViewDivider;
import com.qyy.app.lipstick.views.UPMarqueeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

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
public class HomeFragment extends BaseFragment {

    GoodsList mGoodsList;
    @BindView(R.id.swipeRefresh)
    RecyclerView swipeRefresh;
//    @BindView(R.id.upmv_textview)
    UPMarqueeView upmvTextview;
//    @BindView(R.id.banners)
    LMBanners vpHeader;
//    @BindView(R.id.tv_point)
    TextView tvPoint;
    HomeApiService mHomeApiService;

    private CommonAdapter<GoodsBean> mAdapter;

    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initView(container, savedInstanceState);
        mHomeApiService = HttpManager.create(HomeApiService.class);
        swipeRefresh.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        swipeRefresh.addItemDecoration(new GridDivider(mActivity,15,getResources().getColor(R.color.transparent)));
        mGoodsList = new GoodsList();
        setCenterTitleText("首页");
        hideToolbarNavigationIcon();
        //网络图片

    }

    private View initHeaderView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header, null);
        vpHeader=headerView.findViewById(R.id.banners);
        upmvTextview=headerView.findViewById(R.id.upmv_textview);
        tvPoint=headerView.findViewById(R.id.tv_point);
        //参数设置
        vpHeader.isGuide(true);//是否为引导页
        vpHeader.setAutoPlay(true);//自动播放
        vpHeader.setVertical(false);//是否可以垂直
        vpHeader.setScrollDurtion(222);//两页切换时间
        vpHeader.setCanLoop(true);//循环播放
        vpHeader.hideIndicatorLayout();
        vpHeader.setAdapter(new UrlImgAdapter(getActivity()),mGoodsList.getBanner());
        upmvTextview.setViews(getViews());
        return headerView;
    }
    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */

    private  List<View> getViews() {
        List<View> views=new ArrayList<>();
        for (int i = 0; i < mGoodsList.getNotice().size(); i = i + 2) {
            //设置滚动的单个布局
            //初始化布局的控件
            TextView tv1 = new TextView(getActivity());
            tv1.setPadding(30,0,0,0);
            Drawable drawableLeft = getResources().getDrawable(
                    R.drawable.sy_tz);

            tv1.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            tv1.setCompoundDrawablePadding(5);
            tv1.setGravity(Gravity.CENTER|Gravity.LEFT);
            //进行对控件赋值
            tv1.setText( mGoodsList.getNotice().get(i).toString());

            //添加到循环滚动数组里面去

            views.add(tv1);
        }
        return views;
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void getData() {
        final Call<RespInfo<GoodsList>> call = mHomeApiService.getGoodsList();
        call.enqueue(new NetResponseCall<GoodsList>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<GoodsList>> call, GoodsList data) {
                LogUtil.d(data.toString());
                mGoodsList = data;
                if (mGoodsList != null) {
                    refresh();
                    vpHeader.setAdapter(new UrlImgAdapter(getActivity()), mGoodsList.getBanner());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFail(Call<RespInfo<GoodsList>> call, int type, String code, String tip) {
                ToastUtils.showLongToast(getActivity(), tip);
            }
        });
    }

    private void refresh() {
        mAdapter = new CommonAdapter<GoodsBean>(getActivity(), R.layout.item_home_goods, mGoodsList.getGoods()) {
            @Override
            protected void convert(ViewHolder holder, GoodsBean goodsBean, int position) {
                Glide.with(getActivity()).load(goodsBean.getPrimaryPicUrl()).into((ImageView) holder.getView(R.id.iv_goods));
                holder.setText(R.id.tv_buy_price,"专柜价  ￥"+goodsBean.getCounterPrice());
                holder.setText(R.id.tv_buy_name,goodsBean.getName());
                holder.setText(R.id.tv_buy_brand,goodsBean.getBrand());
                holder.setText(R.id.tv_integral,""+goodsBean.getMarketPrice()+"积分购买");
            }
        };
        HeaderAndFooterWrapper  mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(initHeaderView());
        swipeRefresh.setAdapter(mHeaderAndFooterWrapper);
    }


    @Override
    protected void initListener() {
        super.initListener();

    }


}
