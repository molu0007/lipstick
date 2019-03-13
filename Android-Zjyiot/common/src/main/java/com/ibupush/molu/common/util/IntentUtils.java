package com.ibupush.molu.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 意图相关工具类
 * Created by 曾丽 on 2017/08/22
 * @author 曾丽
 */
public class IntentUtils {

    /**
     * 获取打开App的意图
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Intent getLaunchAppIntent(Context context, String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName
     * @return
     */
    public static Intent getAppDetailsSettingIntent(String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拨号意图
     * @param phone 手机号码
     * @return
     */
    public static Intent getDialIntent(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

}
