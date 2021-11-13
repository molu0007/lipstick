package com.qyy.app.lipstick.ui.activity.home;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.ToastUtils;

import butterknife.BindView;
import retrofit2.Call;

import com.qyy.app.lipstick.BaseApplication;

import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.BehaviorApiService;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.qyy.app.lipstick.ui.activity.login.LoginActivity;
import com.qyy.app.lipstick.utils.PrefsUtil;

/**
 * @author dengwg
 * @date 2018/3/23
 */
public class StartActivity extends BaseActivity {

    ImageView imageView;
    TextView textView;
  
    @Override
    protected int getContentViewId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        String url=PrefsUtil.getString(PrefsUtil.SCREEN_IMAG_URL,null);
        imageView= (ImageView) findViewById(R.id.iv_splash);
        if (url!=null){
            Glide.with(this).load(url).into(imageView);
        }
        textView= (TextView) findViewById(R.id.tv_count_dowon);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMainActivity();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=PrefsUtil.getString(PrefsUtil.SCREEN_IMAG_CLICLK_LINK,null);
                if (url!=null){
                    Intent intent=new Intent(StartActivity.this, WebViewActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            }
        });
        timerStart();
        recordBehaver();
    }

    private void recordBehaver() {
        BehaviorApiService behaviorApiService = HttpManager.create(BehaviorApiService.class);
        final Call<RespInfo<Object>> call = behaviorApiService.uploadBehaviorLog("20","android","launch","","");
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


    private void intentMainActivity(){
        Intent intent=new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 倒数计时器
     */
    private CountDownTimer timer = new CountDownTimer(5 * 1000, 1000) {
        /**
         * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText("跳过 "+millisUntilFinished/1000);
        }

        /**
         * 倒计时完成时被调用
         */
        @Override
        public void onFinish() {
            intentMainActivity();
        }
    };


    /**
     * 取消倒计时
     */
    public void timerCancel() {
        timer.cancel();
    }

    /**
     * 开始倒计时
     */
    public void timerStart() {
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
