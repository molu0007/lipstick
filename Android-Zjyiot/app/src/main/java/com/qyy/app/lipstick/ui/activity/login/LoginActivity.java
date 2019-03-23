package com.qyy.app.lipstick.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.DeviceUtil;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.BaseApplication;
import com.qyy.app.lipstick.Contans;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.BehaviorApiService;
import com.qyy.app.lipstick.api.LoginApiService;
import com.qyy.app.lipstick.event.EventType;
import com.qyy.app.lipstick.model.request.BehaviorEnty;
import com.qyy.app.lipstick.model.response.login.LoginMoudle;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.qyy.app.lipstick.ui.activity.home.MainActivity;
import com.qyy.app.lipstick.ui.activity.home.WebViewActivity;
import com.qyy.app.lipstick.utils.PrefsUtil;
import com.qyy.app.lipstick.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

import static com.qyy.app.lipstick.event.EventType.WX_LOGIN_SUCCESS;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_send_code)
    Button btSendCode;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_login)
    Button tvLogin;
    @BindView(R.id.tv_weixin)
    TextView tvWeixin;
    private MyCountDownTimer myCountDownTimer;
    LoginApiService mLoginApiService;
    private BehaviorApiService mBehaviorApiService;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        hideToolbarNavigationIcon();
        setCenterTitleText("登录");
        setCenterTitleTextColor(R.color.white);
        //new倒计时对象,总共的时间,每隔多少秒更新一次时间
        myCountDownTimer = new MyCountDownTimer(60000,1000);
        mLoginApiService=HttpManager.create(LoginApiService.class);
        mBehaviorApiService=HttpManager.create(BehaviorApiService.class);
        String account=PrefsUtil.getString(PrefsUtil.ACCOUNT);
        etAccount.setText(account==null?"":account);
    }

    @OnClick({R.id.bt_send_code, R.id.tv_agreement, R.id.tv_login, R.id.tv_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_send_code:
                sendCode();
                break;
            case R.id.tv_agreement:
                Intent intent=new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("url","https://api.jucaib.com/xieyi.html");
                startActivity(intent);

                break;
            case R.id.tv_login:
//                toMain();
                toLogin();
                break;
            case R.id.tv_weixin:
                wxLogin();
                break;
        }
    }
    public void wxLogin() {
        if (!BaseApplication.mWxApi.isWXAppInstalled()) {
            ToastUtils.showShortToast(this,"您还未安装微信客户端");
            return;
        }

        BaseApplication.mWxApi.registerApp(Contans.WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_xb_live_state";//官方说明：用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        BaseApplication.mWxApi.sendReq(req);
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
    }

    private void sendCode() {
        String account=etAccount.getText().toString();
        if (account!=null&&!"".equals(account)){
            myCountDownTimer.start();
            Call<RespInfo<String>> call=mLoginApiService.getSmsCode(etAccount.getText().toString());
            call.enqueue(new NetResponseCall<String>(this) {
                @Override
                protected void onSuccess(Call<RespInfo<String>> call, String data) {
                    ToastUtils.showShortToast(getApplicationContext(),data);
                }

                @Override
                protected void onFail(Call<RespInfo<String>> call, int type, String code, String tip) {
                    ToastUtils.showShortToast(getApplicationContext(),tip);
                }
            });
        }else {
            ToastUtils.showShortToast(this,"请输入正确的手机号！");
        }
    }

    private void toLogin() {
        if (etAccount.getText().toString()!=null&&etCode.getText().toString()!=null&&!"".equals(etCode.getText().toString())&&!"".equals(etAccount.getText().toString())) {
            final Call<RespInfo<LoginMoudle>> call = mLoginApiService.login(etAccount.getText().toString(),etCode.getText().toString());
            call.enqueue(new NetResponseCall<LoginMoudle>(this) {
                @Override
                protected void onSuccess(Call<RespInfo<LoginMoudle>> call, LoginMoudle data) {
                  PrefsUtil.savaString(PrefsUtil.ACCOUNT,etAccount.getText().toString());
                  PrefsUtil.setToken(data.getToken());
                  toMain();
                }

                @Override
                protected void onFail(Call<RespInfo<LoginMoudle>> call, int type, String code, String tip) {
                    ToastUtils.showShortToast(getApplicationContext(),tip);
                }
            });

        }
    }
//    private void uploadBehavior(BehaviorEnty behaviorEnty) {
//        Call<RespInfo<Object>> call =mBehaviorApiService.uploadBehavior(behaviorEnty);
//        call.enqueue(new NetResponseCall<Object>(this) {
//            @Override
//            protected void onSuccess(Call<RespInfo<Object>> call, Object data) {
//
//            }
//
//            @Override
//            protected void onFail(Call<RespInfo<Object>> call, int type, String code, String tip) {
//
//            }
//        });
//    }
    @Override
    protected void onMessageReceived(EventType what, Object data) {
        super.onMessageReceived(what, data);
        if (what==WX_LOGIN_SUCCESS){
            if (WXEntryActivity.resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                final Call<RespInfo<LoginMoudle>> call = mLoginApiService.loginWX((String) data);
                call.enqueue(new NetResponseCall<LoginMoudle>(this) {
                    @Override
                    protected void onSuccess(Call<RespInfo<LoginMoudle>> call, LoginMoudle data) {
                        PrefsUtil.savaString(PrefsUtil.ACCOUNT,etAccount.getText().toString());
                        PrefsUtil.savaInteger(PrefsUtil.JIFEN,data.getUser().getJifen());
                        PrefsUtil.setToken(data.getToken());
                        toMain();
                    }

                    @Override
                    protected void onFail(Call<RespInfo<LoginMoudle>> call, int type, String code, String tip) {
                        ToastUtils.showShortToast(getApplicationContext(),tip);
                    }
                });
            }
            ToastUtils.showShortToast(this,"登录成功！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void toMain() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btSendCode.setClickable(false);
            btSendCode.setText(l/1000+"秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btSendCode.setText("重新获取");
            //设置可点击
            btSendCode.setClickable(true);
        }
    }

}

