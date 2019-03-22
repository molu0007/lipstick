package com.qyy.app.lipstick.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.Contans;
import com.qyy.app.lipstick.event.EmptyEvent;
import com.qyy.app.lipstick.event.EventManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.qyy.app.lipstick.event.EventType.WX_LOGIN_SUCCESS;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_OK;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_USER_CANCEL;


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
        LogUtil.d("错误码 : " + resp.errCode + "");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case ERR_USER_CANCEL:
                if (ERR_USER_CANCEL  == resp.getType()) ToastUtils.showShortToast(getApplicationContext(),"登录取消");
                else ToastUtils.showShortToast(getApplicationContext(),"登录失败");
                break;
            case ERR_OK :
                //拿到了微信返回的code,立马再去请求access_token
                String code = ((SendAuth.Resp) resp).code;

                //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                break;
            case  ConstantsAPI.COMMAND_PAY_BY_WX:
                //以下是自定义微信支付广播的发送，微信支付广播请自己定义

                Intent intent = new Intent();
                intent.setAction(WeChatPayReceiver.ACTION_PAY_RESULT);
                intent.putExtra("result", resp.errCode);
                sendBroadcast(intent);

                finish();
                break;
        }

    }
}