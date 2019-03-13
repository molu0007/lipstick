package com.qyy.app.lipstick.utils;

/**
 * @author dengwg
 * @date 2018/3/15
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.qyy.app.lipstick.BaseApplication;

/**
 * 资源文件工具类
 * Created by 曾丽 on 2017/7/24.
 */
public class ResourceUtil {

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return BaseApplication.getAppContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到Resource对象
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 得到String.xml中定义的字符
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中定义的字符数组
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到color.xml中定义的颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到xml中定义的整数
     */
    public static int getInt(int resId) {
        return getResources().getInteger(resId);
    }

    /**
     * 得到xml中定义的整数数组
     */
    public static int[] getIntArray(int resId) {
        return getResources().getIntArray(resId);
    }

    /**
     * 获取TypedArray
     *
     * @param resID
     * @return
     */
    public static TypedArray getTypeArrya(int resID) {
        return getResources().obtainTypedArray(resID);
    }

    /**
     * 得到xml中定义的boolean
     */
    public static boolean getBoolean(int resId) {
        return getResources().getBoolean(resId);
    }

    /**
     * dip-->px
     *
     * @param dip
     * @return
     */
    public static int dip2Px(int dip) {
        // px/dp = density;
        // px和dp比例关系
        float density = getResources().getDisplayMetrics().density;

        // ppi
        // float densityDpi = getResources().getDisplayMetrics().densityDpi;

        // LogUtils.s("density: " + density);
        // LogUtils.s("densityDpi: " + densityDpi);
        return (int) (density * dip + .5f);
        // dp = px/(ppi/160)

        // 320x480 ppi = 160 1px = 1dp
        // 480x800 ppi = 240 1.5px = 1dp
        // 1280 x720 ppi = 320 2px = 1dp
    }

    /**
     * px-->dp
     *
     * @param px
     * @return
     */
    public static int px2Dip(int px) {
        // px/dp = density;
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + .5f);
    }

}
