package com.ibupush.molu.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Shyky on 2017/7/8.
 */
public final class DensityUtil {
    private DensityUtil() {

    }

    public static DisplayMetrics getDisplayMetrics(@NonNull Activity activity) {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getScreenWidthPixels(@NonNull Activity activity) {
        return getDisplayMetrics(activity).widthPixels;
    }

    public static int getScreenHeightPixels(@NonNull Activity activity) {
        return getDisplayMetrics(activity).heightPixels;
    }

    public static Display getDefaultDisplay(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay();
    }

    public static int getScreenWidth(@NonNull Context context) {
        Point size = new Point();
        getDefaultDisplay(context).getSize(size);
        return size.x;
    }

    public static int getScreenWidth(@NonNull Activity activity) {
        return getDisplayMetrics(activity).widthPixels;
    }

    public static int getScreenHeight(@NonNull Context context) {
        Point size = new Point();
        getDefaultDisplay(context).getSize(size);
        return size.y;
    }

    public static int getScreenHeight(@NonNull Activity activity) {
        return getDisplayMetrics(activity).heightPixels;
    }

    public static String getScreenResolution(@NonNull Activity activity) {
        return getScreenWidth(activity) + "*" + getScreenHeight(activity);
    }

    public static int dp2px(@NonNull Activity activity, float dp) {
        final float scale = getDisplayMetrics(activity).density;
        return (int) (dp * scale + 0.5f);
    }
}