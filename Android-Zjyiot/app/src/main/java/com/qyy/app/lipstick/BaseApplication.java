package com.qyy.app.lipstick;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;

import com.tencent.bugly.Bugly;

import java.lang.ref.WeakReference;

import com.qyy.app.lipstick.intercept.HeadersInterceptor;
import com.qyy.app.lipstick.utils.PrefsUtil;

/**
 * @author dengwg
 * @date 2018/3/13
 */
public class BaseApplication extends Application{
    public  static String accoundId= "";
    public  static String iccid= "";
    public  static String cardNo= "";
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<>(getApplicationContext());
        accoundId= PrefsUtil.getAccountId(getAppContext());
        initBugly();
        initNet();
        initLog();
    }

    private void initBugly() {
        Bugly.init(getApplicationContext(), "c08fa87ca9", false);
        }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static void setAccoundId(String accoundId){
        BaseApplication.accoundId=accoundId;
    }
    public static void setIccid(String iccid){
        BaseApplication.iccid=iccid;
    }
    private void initLog() {
        // 控制KLog日志打印
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            LogUtil.init(true, "测试版（debug），测试服务器环境");
        } else if (BuildConfig.BUILD_TYPE.equals("develop")) {
            LogUtil.init(true, "开发版（develop），正式服务器环境");
        } else if (BuildConfig.BUILD_TYPE.equals("release")) {
            // 正式版不要打印Log日志
            LogUtil.init(false);
        }
    }
    /**
     * 初始化网络设置
     */
    private void initNet() {
        HttpManager.setBaseUrl(Config.URL_BASE);
        HttpManager.setHeaderIntercepter(new HeadersInterceptor(getApplicationContext()));
    }
    private static WeakReference<Context> mContext;
    public static Context getAppContext() {
        return mContext.get();
    }
}