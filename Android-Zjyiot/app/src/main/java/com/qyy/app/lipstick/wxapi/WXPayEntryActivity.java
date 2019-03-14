package com.qyy.app.lipstick.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ibupush.molu.common.util.LogUtil;
import com.qyy.app.lipstick.Contans;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-14 14:17
 * @name: WXPayEntryActivity
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    //TODO　这里需要替换你的APP_ID
    private String APP_ID = Contans.WX_APP_ID; //这里需要替换你的APP_ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //			AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //			builder.setTitle(R.string.app_tip);
            //			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
            //			builder.show();

            //以下是自定义微信支付广播的发送，微信支付广播请自己定义

            Intent intent = new Intent();
            intent.setAction(WeChatPayReceiver.ACTION_PAY_RESULT);
            intent.putExtra("result", resp.errCode);
            sendBroadcast(intent);

            finish();
        }
    }
}