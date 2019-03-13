package com.qyy.app.lipstick.ui.dialogs;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.CallDelegate;
import com.ibupush.molu.common.net.HttpManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.Interface.DialogCloseInterface;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.response.Status;
import com.qyy.app.lipstick.utils.QRUtils;

import retrofit2.Call;

/**
 * @author dengwg
 * @date 2018/3/19
 */

public class PayQrCodeDialog extends DialogFragment implements CallDelegate {

    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_pay_price)
    TextView mTvPayPrice;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.bt_accomplish)
    Button mBtAccomplish;
    Unbinder unbinder;
    HomeApiService mHomeApiService;
    private String mPrice;
    private String mPayType;
    private String mQrCode;
    private String mOrderNo;
    private LayoutInflater inflater;

    public static PayQrCodeDialog newInstance(String payType, String price,String qrCode,String OrderNo) {
        PayQrCodeDialog payQrCodeDialog=new PayQrCodeDialog();
        Bundle bundle=new Bundle();
        bundle.putString("mPrice",price);
        bundle.putString("mPayType",payType);
        bundle.putString("mQrCode",qrCode);
        bundle.putString("mOrderNo",OrderNo);
       payQrCodeDialog.setArguments(bundle);
       return payQrCodeDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),R.style.full_dialog_style);
        inflater=LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.dialog_qr_code,null);
        unbinder = ButterKnife.bind(this, view);
        mHomeApiService= HttpManager.create(HomeApiService.class);
        initData();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        mHomeApiService= HttpManager.create(HomeApiService.class);
        mPayType=  getArguments().getString("mPayType");
        mPrice=getArguments().getString("mPrice");
        mQrCode=getArguments().getString("mQrCode");
        mOrderNo=getArguments().getString("mOrderNo");
        mTvPayPrice.setText("¥ "+mPrice);
        initData();
        if (mPayType.equals("alipay")) {
            mTvPayType.setText("请使用支付宝扫一扫");
        }else {
            mTvPayType.setText("请使用微信扫一扫");
        }
        return dialog;
    }

    private void initData() {
        try {
            Bitmap bitmap= QRUtils.encodeToQR(mQrCode,200);
                    mIvQrCode.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"二维码生成失败！",Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
             handler.sendEmptyMessageAtTime(0,5000);
    }

    @Override
    public boolean isHostDestroyed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        handler.removeCallbacksAndMessages(null);
        handler=null;
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
           getOrderStatus();
        }
    };
    public void getOrderStatus(){
        Call<RespInfo<Status>> call=mHomeApiService.getOrderStatus(BaseApplication.iccid,BaseApplication.cardNo,BaseApplication.accoundId,mOrderNo);
        call.enqueue(new NetResponseCall<Status>(this) {

            @Override
            protected void onSuccess(Call<RespInfo<Status>> call, Status data) {
                if (data.getStatus().equals("10")||data.getStatus().equals("30")){
                    if (mDialogInterface!=null){
                        mDialogInterface.dimss();
                        PayQrCodeDialog.this.getDialog().dismiss();
                    }
                }else {
                    if (handler!=null){
                        handler.sendEmptyMessageDelayed(0,3000);
                    }
                }
            }

            @Override
            protected void onFail(Call<RespInfo<Status>> call, int type, String code, String tip) {

            }
        });
    }
    @OnClick(R.id.bt_accomplish)
    public void onViewClicked() {
        dismiss();
    }

    DialogCloseInterface mDialogInterface;
    public void setDialogCloseInterface(DialogCloseInterface DialogCloseInterface) {
        mDialogInterface = DialogCloseInterface;
    }
}
