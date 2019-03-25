package com.qyy.app.lipstick.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.JsonUtil;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.Contans;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.adapter.GradViewPackageAdapter;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.api.MallApiService;
import com.qyy.app.lipstick.event.EventManager;
import com.qyy.app.lipstick.event.EventType;
import com.qyy.app.lipstick.event.MessageEvent;
import com.qyy.app.lipstick.model.response.PackageInfo;
import com.qyy.app.lipstick.model.response.PackageList;
import com.qyy.app.lipstick.model.response.home.UserInfo;
import com.qyy.app.lipstick.model.response.order.AlipayResult;
import com.qyy.app.lipstick.model.response.order.RechareGoods;
import com.qyy.app.lipstick.model.response.order.WxPayInfo;
import com.qyy.app.lipstick.ui.activity.base.BaseFragment;
import com.qyy.app.lipstick.ui.activity.mall.RechareActivity;
import com.qyy.app.lipstick.ui.dialogs.PaySelectDialog;
import com.qyy.app.lipstick.utils.PrefsUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import io.github.mayubao.pay_library.AliPayReq2;
import io.github.mayubao.pay_library.PayAPI;
import io.github.mayubao.pay_library.WechatPayReq;
import retrofit2.Call;

import static com.qyy.app.lipstick.event.EventType.REFRESH_JIFEN;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public class TopUpFragment extends BaseFragment {

    @BindView(R.id.rv_rechare)
    RecyclerView rvRechare;
    @BindView(R.id.bt_rechare)
    Button btRechare;
    @BindView(R.id.tv_balance)
    TextView mTextView;
    private MallApiService mMallApiService;
    protected CommonAdapter<RechareGoods> mRechareGoodsCommonAdapter;
    private List<RechareGoods> mRechareGoodsList = new ArrayList<>();
    private static int checkPos;
    private long blance;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_rechare;
    }

    @Override
    protected void initView(ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.initView( container,   savedInstanceState);
        setCenterTitleText("充值");
        hideToolbarNavigationIcon();
        blance =PrefsUtil.getInteger(PrefsUtil.JIFEN,0);
        mTextView.setText("当前积分："+blance);
        rvRechare.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRechareGoodsCommonAdapter = new CommonAdapter<RechareGoods>(getActivity(), R.layout.item_rechare, mRechareGoodsList) {
            @Override
            protected void convert(ViewHolder holder, RechareGoods rechareGoods, final int position) {
                if (checkPos==position){
                    holder.setBackgroundRes(R.id.ll_contair,R.drawable.cz_xz);
                    holder.setTextColor(R.id.tv_mony,getResources().getColor(R.color.color_fc598d));
                    holder.setTextColor(R.id.tv_integral,getResources().getColor(R.color.color_fc598d));
                }else {
                    holder.setTextColor(R.id.tv_mony,getResources().getColor(R.color.color_333333));
                    holder.setTextColor(R.id.tv_integral,getResources().getColor(R.color.color_333333));
                    holder.setBackgroundRes(R.id.ll_contair,R.drawable.shape_fafafa_bg);
                }
                holder.setText(R.id.tv_mony,"￥"+rechareGoods.getPayNum()+"");
                holder.setText(R.id.tv_integral,rechareGoods.getJifen()+"积分（首充翻倍）");
                holder.setOnClickListener(R.id.ll_contair, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPos=position;
                        mRechareGoodsCommonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        rvRechare.setAdapter(mRechareGoodsCommonAdapter);
        btRechare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaySelectDialog paySelectDialog=new PaySelectDialog();
                paySelectDialog.setOnSelectListener(new PaySelectDialog.OnSelectListener() {
                    @Override
                    public void onSelect(int pos) {
                        switch (pos){
                            case 0:
                                getPayParameter("wxpay");
                                break;
                            case 1:
                                getPayParameter("alipay");
                                break;
                        }
                    }
                });
                paySelectDialog.show(getChildFragmentManager(),"Rechare");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mMallApiService = HttpManager.create(MallApiService.class);
        Call<RespInfo<List<RechareGoods>>> call = mMallApiService.getRechargeList();
        call.enqueue(new NetResponseCall<List<RechareGoods>>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<List<RechareGoods>>> call, List<RechareGoods> data) {
                if (data!=null){
                    mRechareGoodsList.clear();
                    mRechareGoodsList.addAll(data);
                    mRechareGoodsCommonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFail(Call<RespInfo<List<RechareGoods>>> call, int type, String code, String tip) {

            }
        });
    }

    private void getPayParameter(final String payType){
        if (mRechareGoodsList.size()==0){
            return;
        }
        Call<RespInfo<Object>> call = mMallApiService.getPayParameter(mRechareGoodsList.get(checkPos).getId(),payType);
        call.enqueue(new NetResponseCall<Object>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<Object>> call, Object data) {
                if(payType.equals("alipay")){
                    //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
                    try {

                        doAlipay((String) data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    String  str=JsonUtil.toJsonString(data);
                    Gson gson=new Gson();
                    WxPayInfo wxPayInfo=gson.fromJson(str,WxPayInfo.class);
                    doWXpay(wxPayInfo);
                }

            }

            @Override
            protected void onFail(Call<RespInfo<Object >> call, int type, String code, String tip) {
                ToastUtils.showShortToast(getActivity(),tip);
            }
        });
    }

    private void doWXpay( WxPayInfo wxPayInfo) {
//1.创建微信支付请求
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(getActivity()) //activity实例
                .setAppId(Contans.WX_APP_ID) //微信支付AppID
                .setPartnerId(wxPayInfo.getPartnerid())//微信支付商户号
                .setPrepayId(wxPayInfo.getPrepayid())//预支付码
                .setPackageValue("Sign=WXPay")//"Sign=WXPay"
                .setNonceStr(wxPayInfo.getNoncestr())
                .setTimeStamp(wxPayInfo.getTimestamp())//时间戳
                .setSign(wxPayInfo.getSign())//签名
                .create();
        //2.发送微信支付请求
        PayAPI.getInstance().sendPayRequest(wechatPayReq);
    }

    private void doAlipay(String signAliOrderInfo) {
        //3.发送支付宝支付请求
        final AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                .with(getActivity())//Activity实例
                .setRawAliPayOrderInfo(null)//支付宝支付订单信息
                .setSignedAliPayOrderInfo(signAliOrderInfo) //设置 商户私钥RSA加密后的支付宝支付订单信息
                .create()//
                .setOnAliPayListener(null);//
        PayAPI.getInstance().sendPayRequest(aliPayReq);

        //关于支付宝支付的回调
        aliPayReq.setOnAliPayListener(new AliPayReq2.OnAliPayListener() {
            @Override
            public void onPaySuccess(String resultInfo) {
                LogUtil.d(resultInfo);
                Gson gson =new Gson();
                AlipayResult alipayResult=gson.fromJson(resultInfo,AlipayResult.class);
                if (alipayResult!=null){
                    String orderId=alipayResult.getAlipay_trade_app_pay_response().getOut_trade_no();
                    getPayResult(orderId);
                    refreshJiFen();
                }
            }

            @Override
            public void onPayFailure(String resultInfo) {
                LogUtil.d(resultInfo);
            }

            @Override
            public void onPayConfirmimg(String resultInfo) {
                LogUtil.d(resultInfo);
            }

            @Override
            public void onPayCheck(String status) {
                LogUtil.d(status);
            }
        });
    }

    private void getPayResult(String orderId) {
        Call<RespInfo<Object>> call =mMallApiService.getPayResult(orderId);
        call.enqueue(new NetResponseCall<Object>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<Object>> call, Object data) {
                LogUtil.d(data.toString());
            }

            @Override
            protected void onFail(Call<RespInfo<Object>> call, int type, String code, String tip) {

            }
        });
    }

    /**
     * 微信支付成功消息
     * @param what 消息标识
     * @param data 消息体
     */
    @Override
    protected void onMessageReceived(EventType what, Object data) {
        super.onMessageReceived(what, data);
        if (what==EventType.WX_PAYSUCCESS){
//            getPayResult(data);da
            refreshJiFen();
        }

    }

    void refreshJiFen(){
        HomeApiService mHomeApiService = HttpManager.create(HomeApiService.class);
        final retrofit2.Call<RespInfo<UserInfo>> call = mHomeApiService.getUserInfo();
        call.enqueue(new NetResponseCall<UserInfo>(this) {
            @Override
            protected void onSuccess(retrofit2.Call<RespInfo<UserInfo>> call, UserInfo data) {
                if (data!=null){
                    EventManager.post(new MessageEvent<UserInfo>(REFRESH_JIFEN,data));
                    mTextView.setText("当前积分："+data.getJifen());
                }
            }

            @Override
            protected void onFail(retrofit2.Call<RespInfo<UserInfo>> call, int type, String code, String tip) {

            }
        });
    }
}
