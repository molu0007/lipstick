package com.ibupush.molu.common.util;

import android.support.annotation.NonNull;

/**
 * 数值类型数据工具类
 *
 * @author Shyky
 * @date 2017/7/6
 */
public final class NumberUtil {
    private NumberUtil() {

    }

    public static int toInt(@NonNull String value) {
        return toInt(value, false);
    }

    public static int toInt(@NonNull String value, boolean throwsException) {
        if (TextUtil.isEmpty(value)) {
            if (throwsException)
                throw new NullPointerException("Value can not be empty.");
            else return 0;
        }
        return Integer.parseInt(value);
    }

    public static int toInt(@NonNull String value, int defaultValue) {
        if (TextUtil.isEmpty(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public static long toLong(@NonNull String value) {
        if (TextUtil.isEmpty(value))
            throw new NullPointerException("Value can not be empty.");
        return Long.parseLong(value);
    }

    public static float toFloat(@NonNull String value) {
        if (TextUtil.isEmpty(value))
            throw new NullPointerException("Value can not be empty.");
        try {
            return Float.parseFloat(value.trim());
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    public static double toDouble(@NonNull String value) {
        if (TextUtil.isEmpty(value))
            throw new NullPointerException("Value can not be empty.");
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}