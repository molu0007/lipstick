package com.qyy.app.lipstick.ui.activity.home;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.ibupush.molu.common.util.ToastUtils;

import butterknife.BindView;

import com.qyy.app.lipstick.BaseApplication;

import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.api.HomeApiService;
import com.qyy.app.lipstick.ui.activity.base.BaseActivity;
import com.qyy.app.lipstick.ui.activity.login.LoginActivity;

/**
 * @author dengwg
 * @date 2018/3/23
 */
public class StartActivity extends BaseActivity {
    @BindView(R.id.imageView)
    ImageView mImageView;
    HomeApiService mHomeApiService;
    private PackageManager packageManager;
    private ComponentName defaultComponent;
    private ComponentName changeComponent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        intentMainActivity();
    }

    /**
     * 获取设备iccid权限
     */
    TelephonyManager tm;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getAccountId();
            } else {
                intentMainActivity();
                ToastUtils.showShortToast(BaseApplication.getAppContext(), "权限已拒绝");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void intentMainActivity(){
        Intent intent=new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(StartActivity.this);
        normalDialog.setIcon(R.mipmap.ic_launcher1);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("未找到SIM卡!");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        intentMainActivity();
                    }
                });
        // 显示
        normalDialog.show();
    }
    /**
     * @param accountId
     */
    private void switchIcon(int accountId) {
        packageManager = getApplicationContext().getPackageManager();
        //拿到当前App_StartActivity组件
        defaultComponent = new ComponentName(getBaseContext(),getResources().getString(R.string.default_cls) );  //拿到默认的组件
        switch (accountId){
            case 123:
                //拿到我注册的别名changeLogo组件
                changeComponent = new ComponentName(getBaseContext(),getResources().getString(R.string.component_cls));
                break;
        }
        if (changeComponent!=null){
            disableComponent(defaultComponent);
            enableComponent(changeComponent);
        }
    }
    /**
     * 启用组件
     *
     * @param componentName
     */
    private void enableComponent(ComponentName componentName) {
        try {
            int state = packageManager.getComponentEnabledSetting(componentName);
            if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                //已经启用
                return;
            }
            packageManager.setComponentEnabledSetting(componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }catch (Exception e){

        }
    }

    /**
     * 禁用组件
     *
     * @param componentName
     */
    private void disableComponent(ComponentName componentName) {
        try {
            int state = packageManager.getComponentEnabledSetting(componentName);
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                //已经禁用
                return;
            }
            packageManager.setComponentEnabledSetting(componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }catch (Exception e){

        }
    }
}
