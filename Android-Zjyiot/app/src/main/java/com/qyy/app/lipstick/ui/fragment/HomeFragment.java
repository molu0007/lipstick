package com.qyy.app.lipstick.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allure.lbanners.LMBanners;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.JsonUtil;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.home.UrlImgAdapter;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.event.EventType;
import com.qyy.app.lipstick.model.response.home.GameBean;
import com.qyy.app.lipstick.model.response.home.GoodsBean;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.model.response.home.UserInfo;
import com.qyy.app.lipstick.ui.activity.WebViewGameActivity;
import com.qyy.app.lipstick.ui.activity.base.BaseFragment;
import com.qyy.app.lipstick.ui.activity.home.MainActivity;
import com.qyy.app.lipstick.ui.activity.home.WebViewActivity;
import com.qyy.app.lipstick.ui.activity.mall.RechareActivity;
import com.qyy.app.lipstick.ui.dialogs.CustomDialog;
import com.qyy.app.lipstick.utils.PrefsUtil;
import com.qyy.app.lipstick.views.UPMarqueeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    TextView tvChallenge;
    HomeApiService mHomeApiService;
    @BindView(R.id.iv_game)
    ImageView ivGame;
    Unbinder unbinder;

    private CommonAdapter<GoodsBean> mAdapter;
    private CustomDialog customDialogGoods;
    private long gameId;

    protected int getContentViewId() {
        return R.layout.fragment_home;
    }
    //申请录音权限
    private static final int GET_RECODE_AUDIO = 1;
    private static String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };
    /*
     * 申请录音权限*/
    public static void verifyAudioPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);//权限返回码为1
        }

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
        initGoodDialog();
        verifyAudioPermissions(getActivity());
    }

    CustomDialog customDialogTip;
    private View initHeaderView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header, null);
        vpHeader = headerView.findViewById(R.id.banners);
        upmvTextview = headerView.findViewById(R.id.upmv_textview);
        tvPoint = headerView.findViewById(R.id.tv_point);
        tvChallenge = headerView.findViewById(R.id.tv_challenge);
        tvChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity())
                        .view(R.layout.layout_challenge)
                        .addViewOnclick(R.id.iv_close, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialogTip.dismiss();
                            }
                        })
                        .cancelTouchout(true);

                customDialogTip = new CustomDialog(builder);
                customDialogTip.show();
            }
        });
        PrefsUtil.savaInteger(PrefsUtil.JIFEN, mGoodsList.getJifen());
        tvPoint.setText("余额：" + mGoodsList.getJifen() + "个积分");
        //参数设置
        vpHeader.isGuide(false);//是否为引导页
        vpHeader.setAutoPlay(true);//自动播放
        vpHeader.setVertical(false);//是否可以垂直
        vpHeader.setScrollDurtion(222);//两页切换时间
        vpHeader.setCanLoop(true);//循环播放
        vpHeader.hideIndicatorLayout();
        vpHeader.setAdapter(new UrlImgAdapter(getActivity()), mGoodsList.getBanner());
        upmvTextview.setViews(getViews());
        return headerView;
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */

    private List<View> getViews() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < mGoodsList.getNotice().size(); i = i + 2) {
            //设置滚动的单个布局
            //初始化布局的控件
            TextView tv1 = new TextView(getActivity());
            tv1.setPadding(30, 0, 0, 0);
            Drawable drawableLeft = getResources().getDrawable(
                    R.drawable.sy_tz);

            tv1.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            tv1.setCompoundDrawablePadding(5);
            tv1.setGravity(Gravity.CENTER | Gravity.LEFT);
            //进行对控件赋值
            tv1.setText(mGoodsList.getNotice().get(i).toString());

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
            protected void convert(ViewHolder holder, final GoodsBean goodsBean, int position) {
                Glide.with(getActivity()).load(goodsBean.getPrimaryPicUrl()).into((ImageView) holder.getView(R.id.iv_goods));
                holder.setText(R.id.tv_buy_price, "专柜价  ￥" + goodsBean.getCounterPrice());
                holder.setText(R.id.tv_buy_name, goodsBean.getName());
                holder.setText(R.id.tv_buy_brand, goodsBean.getBrand());
                holder.setOnClickListener(R.id.tv_integral, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showGoodDialog(goodsBean);
                    }
                });
                holder.setText(R.id.tv_integral, "" + goodsBean.getMarketPrice() + "积分购买");
            }
        };
        HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(initHeaderView());
        swipeRefresh.setAdapter(mHeaderAndFooterWrapper);

    }



    ImageView mImageViewGoods;
    TextView mTvName;
    TextView mTvColor,mTvBrand,mTvPrice;
    View mVColor;
    Button mBtTopUp,mBtChallenge;
    private void initGoodDialog() {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_good_datial, null);
        mImageViewGoods=view.findViewById(R.id.iv_goods);
        mTvName=view.findViewById(R.id.tv_name);
        mTvColor=view.findViewById(R.id.tv_color);
        mTvBrand=view.findViewById(R.id.tv_brand);
        mTvPrice=view.findViewById(R.id.tv_price);
        mVColor=view.findViewById(R.id.view_color);
        mBtTopUp=view.findViewById(R.id.bt_top_up);
        mBtChallenge=view.findViewById(R.id.bt_challenge);
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity())
                .view(view)
                .addViewOnclick(R.id.iv_close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialogGoods.dismiss();
                    }
                })
                .addViewOnclick(R.id.bt_top_up, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getActivity()).toUp();
                        customDialogGoods.dismiss();
                    }
                })
                .addViewOnclick(R.id.bt_challenge, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       initGame(gameId);
                    }
                })
                .cancelTouchout(true);
        customDialogGoods = new CustomDialog(builder);
    }
    void  initGame(long id){
       Call<RespInfo<GameBean>> call=mHomeApiService.initGame(id);
       call.enqueue(new NetResponseCall<GameBean>(this) {
           @Override
           protected void onSuccess(Call<RespInfo<GameBean>> call, GameBean data) {
               LogUtil.d(data.toString());
               getData();
               try {
                   verifyAudioPermissions(getActivity());
                   Intent intent=new Intent(getActivity(), WebViewGameActivity.class);
                   intent.putExtra("url",data.getGame_url());
                   startActivity(intent);
               } catch (Exception e) {
                   e.printStackTrace();
               }

           }

           @Override
           protected void onFail(Call<RespInfo<GameBean>> call, int type, String code, String tip) {
            ToastUtils.showShortToast(getActivity(),tip);
           }
       });
    }
    private void showGoodDialog(GoodsBean goodsBean) {
        gameId=goodsBean.getGid();
        Glide.with(getActivity()).load(goodsBean.getPrimaryPicUrl()).into(mImageViewGoods);
        mTvName.setText(goodsBean.getName());
        mTvBrand.setText(goodsBean.getBrand());
        mTvPrice.setText("专柜价 ￥"+goodsBean.getCounterPrice());
        mBtChallenge.setText(goodsBean.getMarketPrice()+"积分挑战");
        mTvColor.setText(goodsBean.getColorName());
        try {
            mVColor.setBackgroundColor(Color.parseColor("#"+goodsBean.getColorValue()));
        }catch (Exception e){

        }
        customDialogGoods.show();
    }

    @Override
    protected void initListener() {
        super.initListener();

    }
    @Override
    protected void onMessageReceived(EventType what, Object event) {
        super.onMessageReceived(what, event);
        if (what==EventType.REFRESH_JIFEN){
            PrefsUtil.savaInteger(PrefsUtil.JIFEN, (int) ((UserInfo)event).getJifen());
            tvPoint.setText("余额：" + ((UserInfo)event).getJifen() + "个积分");
        }
    }

    @OnClick(R.id.iv_game)
    public void onViewClicked() {
        verifyAudioPermissions(getActivity());
        Intent intent=new Intent(getActivity(), WebViewGameActivity.class);
        intent.putExtra("url","https://api.jucaib.com/taste.html?h5=1");
        startActivity(intent);
    }
}
