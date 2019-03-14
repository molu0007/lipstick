package com.qyy.app.lipstick.ui.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.Contans;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.MallApiService;
import com.qyy.app.lipstick.model.response.order.AlipayResult;
import com.qyy.app.lipstick.model.response.order.RechareGoods;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.qyy.app.lipstick.ui.dialogs.PaySelectDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mayubao.pay_library.AliPayReq;
import io.github.mayubao.pay_library.AliPayReq2;
import io.github.mayubao.pay_library.PayAPI;
import retrofit2.Call;

/**
 * <p>类说明</p>
 * <p>
 * 充值
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-12 15:08
 * @name: RechareActivity
 */
public class RechareActivity extends BaseActivity {
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
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setCenterTitleText("充值");
        Intent intent=getIntent();
        blance =intent.getLongExtra("Balance",0);
        mTextView.setText("当前积分："+blance);
        rvRechare.setLayoutManager(new GridLayoutManager(this,2));
        mRechareGoodsCommonAdapter = new CommonAdapter<RechareGoods>(this, R.layout.item_rechare, mRechareGoodsList) {
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
                paySelectDialog.show(getSupportFragmentManager(),"Rechare");
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
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

    private void getPayParameter(String payType){
        if (mRechareGoodsList.size()==0){
            return;
        }
        Call<RespInfo<String>> call = mMallApiService.getPayParameter(mRechareGoodsList.get(checkPos).getId(),payType);
        call.enqueue(new NetResponseCall<String>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<String>> call, String data) {
                //1.创建支付宝支付订单的信息
//                String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
//                        .setPartner(Contans.WX_APP_ID) //商户PID || 签约合作者身份ID
//                        .setSeller("1111")  // 商户收款账号 || 签约卖家支付宝账号
//                        .setOutTradeNo("464434ada01310") //设置唯一订单号
//                        .setSubject("") //设置订单标题
//                        .setBody("254") //设置订单内容
//                        .setPrice("0.1") //设置订单价格
//                        .setCallbackUrl(callbackUrl) //设置回调链接
//                        .createOrderInfo(); //创建支付宝支付订单信息


                //2.签名  支付宝支付订单的信息 ===>>>  商户私钥签名之后的订单信息
                //TODO 这里需要从服务器获取用商户私钥签名之后的订单信息
                String signAliOrderInfo =data;

                //3.发送支付宝支付请求
                final AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                        .with(RechareActivity.this)//Activity实例
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

            @Override
            protected void onFail(Call<RespInfo<String >> call, int type, String code, String tip) {
                ToastUtils.showShortToast(RechareActivity.this,tip);
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
}
