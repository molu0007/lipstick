package com.ibupush.molu.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

/**
 * Created by Shyky on 2017/6/26.
 */
public final class ResourcesUtil {
    private ResourcesUtil() {

    }

    public static Resources getResources(@NonNull Context context) {
        return context.getResources();
    }

    public static String getResourceName(@NonNull Context context, @IdRes int resId) {
        try {
            final String name = getResources(context).getResourceName(resId);
            return name.substring(name.lastIndexOf("/") + 1, name.length());
        } catch (NotFoundException e) {
            return null;
        }
    }

    public static boolean exists(@NonNull Context context, @IdRes int resId) {
        return getResourceName(context, resId) != null;
    }

    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(resId);
        } else {
            return getResources(context).getDrawable(resId);
        }
    }

    public static float getDimension(@NonNull Context context, @DimenRes int resId) {
        return getResources(context).getDimension(resId);
    }

    public static int getDimensionPixelSize(@NonNull Context context, @DimenRes int resId) {
        return getResources(context).getDimensionPixelSize(resId);
    }

    public static int getColor(@NonNull Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColor(resId);
        } else {
            return getResources(context).getColor(resId);
        }
    }

    public static String[] getStringArray(@NonNull Context context, @ArrayRes int resId) {
        return getResources(context).getStringArray(resId);
    }

    public static int getResourceIdByName(@NonNull Context context, @NonNull String resName) {
        return getResources(context).getIdentifier(resName, "id", context.getPackageName());
    }
}