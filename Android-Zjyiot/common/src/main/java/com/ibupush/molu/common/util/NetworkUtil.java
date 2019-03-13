package com.ibupush.molu.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * 网络相关工具类
 * Created by Shyky on 2017/6/20.
 */
public final class NetworkUtil {
    //    public static final int NETWORK_TYPE_NONE = 10;
    public static final int NETWORK_TYPE_UNKNOWN = 10;
    public static final int NETWORK_TYPE_WIFI = 11;
    public static final int NETWORK_TYPE_2G = 12;
    public static final int NETWORK_TYPE_3G = 13;
    public static final int NETWORK_TYPE_4G = 14;

    private NetworkUtil() {

    }

    /**
     * 检查网络是否连接
     *
     * @param context 全局context
     * @return true 已连接 false 未连接
     */
    public static Boolean checkNetworkConnect(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getNetworkState(@NonNull Context context) {
        return 0;
    }

    public static int getNetworkType(@NonNull Context context) {
        final ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isAvailable()) {
            return NETWORK_TYPE_WIFI;
        } else if (mobile.isAvailable()) {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_IWLAN:
                    return NETWORK_TYPE_WIFI;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NETWORK_TYPE_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NETWORK_TYPE_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NETWORK_TYPE_4G;
                default:
                    return NETWORK_TYPE_UNKNOWN;
            }
        }
        return NETWORK_TYPE_UNKNOWN;
    }

    public static String getNetworkTypeName(@NonNull Context context) {
        final int type = getNetworkType(context);
        switch (type) {
            case NETWORK_TYPE_WIFI:
                return "Wifi";
            case NETWORK_TYPE_2G:
                return "2G";
            case NETWORK_TYPE_3G:
                return "3G";
            case NETWORK_TYPE_4G:
                return "4G";
            default:
                return "Unknown";
        }
    }
}