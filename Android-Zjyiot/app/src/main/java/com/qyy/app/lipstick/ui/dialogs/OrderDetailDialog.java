package com.qyy.app.lipstick.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.CallDelegate;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.ToastUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.Interface.DialogCloseInterface;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.response.OrderDetail;
import com.qyy.app.lipstick.model.response.PayInfo;

import retrofit2.Call;

/**
 * @author dengwg
 * @date 2018/3/19
 */

public class OrderDetailDialog extends DialogFragment implements CallDelegate ,DialogCloseInterface{

    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.tv_card_no)
    TextView mTvCardNo;
    @BindView(R.id.tv_package_name)
    TextView mTvPackageName;
    @BindView(R.id.tv_order_type)
    TextView mTvOrderType;
    @BindView(R.id.tv_buy_num)
    TextView mTvBuyNum;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.tv_buy_price)
    TextView mTvBuyPrice;
    @BindView(R.id.bt_wx_pay)
    Button mBtWxPay;
    @BindView(R.id.bt_aliy_pay)
    Button mBtAliyPay;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    Unbinder unbinder;
    HomeApiService mHomeApiService;
    DialogInterface mDialogInterface;
    private LayoutInflater inflater;

    public static OrderDetailDialog  newInstance(OrderDetail resultBean) {

        OrderDetailDialog orderDetailDialog=new OrderDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable ("OrderDetail",resultBean);
        orderDetailDialog.setArguments(bundle);
        return orderDetailDialog;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        final Window window = getDialog().getWindow();
//        View view = inflater.inflate(R.layout.dialog_order_detail, container);
//        unbinder = ButterKnife.bind(this, view);
//        window.setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent90)));//注意此处
//        Dialog dialog=getDialog();
//        if (dialog!=null){
//            DisplayMetrics dm = new DisplayMetrics();
//            //设置弹框的占屏宽        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//            dialog.getWindow().setLayout(dm.widthPixels, dm.heightPixels);
//        }
//        mHomeApiService= HttpManager.create(HomeApiService.class);
//        initData();
//
//        return view;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),R.style.full_dialog_style);
        inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialog_order_detail,null);
        unbinder = ButterKnife.bind(this, view);
        mHomeApiService= HttpManager.create(HomeApiService.class);
        initData();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        mHomeApiService= HttpManager.create(HomeApiService.class);
        initData();
        return dialog;
    }

    private OrderDetail mOrderDetail;
    private void initData() {
        mOrderDetail= (OrderDetail) getArguments().getSerializable("OrderDetail");
        if (mOrderDetail!=null){
            refreshUi(mOrderDetail);
        }
    }

    /**
     * 获取订单支付信息
     */
    private void getOrderPayInfo(final String payType) {
        Call<RespInfo<PayInfo>> call=mHomeApiService.getPayInfo(BaseApplication.iccid,
                BaseApplication.cardNo, BaseApplication.accoundId,mOrderDetail.getIbuOrderNo(),payType);
        call.enqueue(new NetResponseCall<PayInfo>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<PayInfo>> call, PayInfo data) {
                if (data!=null){
                    PayQrCodeDialog payQrCodeDialog = PayQrCodeDialog.newInstance(payType,String.valueOf(price),data.getPayInfo(),data.getOrderNo());

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    payQrCodeDialog.show(ft,"PayQrCodeDialog");
                    payQrCodeDialog.setDialogCloseInterface(OrderDetailDialog.this);
                }
            }

            @Override
            protected void onFail(Call<RespInfo<PayInfo>> call, int type, String code, String tip) {
                if (getActivity()!=null){
                    ToastUtils.showLongToast(getActivity(),tip);
                }

            }
        });
    }
    float price;
    private void refreshUi(OrderDetail orderDetail) {
         price=Float.valueOf(orderDetail.getGoodsAmountDis().toString());
        DecimalFormat fnum =new DecimalFormat("##0.00");
        String   pric=fnum.format(Math.abs(price));
        mTvOrderPrice.setText(orderDetail.getOrderAmount().toString());
        mTvBuyPrice.setText(pric);
        mTvCardNo.setText(BaseApplication.iccid);
        mTvOrderNo.setText(orderDetail.getClientOrderNo());
        mTvPackageName.setText(orderDetail.getFlowCardName());
        if (orderDetail.getOrderType().equals("1")){//1：套餐续费,2:套餐变更,3:购买流量包,4：购买短信包,5:购卡
            mTvOrderType.setText("套餐续费");
        }else if (orderDetail.getOrderType().equals("2")){
            mTvOrderType.setText("套餐变更");
        } else if (orderDetail.getOrderType().equals("3")){
            mTvOrderType.setText("购买流量包");
        }else if (orderDetail.getOrderType().equals("4")){
            mTvOrderType.setText("购买短信包");
        }else if (orderDetail.getOrderType().equals("5")){
            mTvOrderType.setText("购卡");
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_wx_pay, R.id.bt_aliy_pay,R.id.iv_close})
    public void onViewClicked(View view) {
        String payType = null;
        switch (view.getId()) {
            case R.id.bt_wx_pay:
                payType="wechat";
                getOrderPayInfo(payType);
                break;
            case R.id.bt_aliy_pay:
                payType="alipay";
                getOrderPayInfo(payType);
                break;
            case R.id.iv_close:
                getDialog().dismiss();
                break;
            default:
                break;
        }

    }

    @Override
    public boolean isHostDestroyed() {
        return false;
    }

    @Override
    public void dimss() {
        getDialog().dismiss();
    }
}
