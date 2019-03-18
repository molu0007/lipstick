package com.qyy.app.lipstick.ui.activity.mall;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.MallApiService;
import com.qyy.app.lipstick.model.response.order.OrderEntry;
import com.qyy.app.lipstick.model.response.order.ServiceInfo;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-18 11:19
 * @name: QrCodeActivity
 */
public class QrCodeActivity extends BaseActivity {
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_weixin_no)
    TextView tvWeixinNo;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    MallApiService mMallApiService;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setCenterTitleText("联系客服");
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceInfo!=null){
                 copyToClipboard(mServiceInfo.getWeixin_id());
                    ToastUtils.showShortToast(QrCodeActivity.this,"复制成功！");
                }

            }
        });
    }
    public  void copyToClipboard(String text) {
        ClipboardManager systemService = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        systemService.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    ServiceInfo mServiceInfo;
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mMallApiService= HttpManager.create(MallApiService.class);
        final Call<RespInfo<ServiceInfo>> call = mMallApiService.getServiceInfo();
        call.enqueue(new NetResponseCall<ServiceInfo>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<ServiceInfo>> call, ServiceInfo data) {
                if (data!=null){
                    mServiceInfo =data;
                    tvWeixinNo.setText("专属客服微信号："+data.getWeixin_id());
                    Glide.with(QrCodeActivity.this).load(data.getKefu()).into(ivQrCode);
                }
            }

            @Override
            protected void onFail(Call<RespInfo<ServiceInfo>> call, int type, String code, String tip) {

            }
        });
    }
}
