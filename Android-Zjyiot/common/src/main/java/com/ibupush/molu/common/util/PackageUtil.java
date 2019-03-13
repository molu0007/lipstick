package com.ibupush.molu.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * app包相关工具类
 *
 * @author Shyky
 * @date 2017/7/28
 */
public final class PackageUtil {
    private PackageUtil() {

    }

    /**
     * 检测指定的包名是否已经安装在系统上
     *
     * @param context     应用程序上下文
     * @param packageName app包名
     * @return true为已安装，false为未安装
     */
    public static boolean isInstalled(@NonNull Context context, @NonNull String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        final List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (packages != null && !packages.isEmpty()) {
            for (int i = 0; i < packages.size(); i++) {
                String pn = packages.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @param cx 上下文
     * @return 版本号
     */
    public static String getVersionName(Context cx) {
        String packName = cx.getPackageName();
        PackageInfo pinfo = null;
        try {
            pinfo = cx.getPackageManager().getPackageInfo(packName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pinfo != null) return pinfo.versionName;
        else return null;
    }
}