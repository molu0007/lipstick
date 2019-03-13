package com.ibupush.molu.common.util;

import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 日志打印工具类
 */
public class LogUtil {

    /**
     * 初始化
     *
     * @param isShowLog 打印开关
     */
    public static void init(final boolean isShowLog) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isShowLog;
            }
        });
    }

    /**
     * 初始化
     *
     * @param isShowLog 打印开关
     * @param tag       全局TAG
     */
    public static void init(final boolean isShowLog, @Nullable String tag) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(1)         // (Optional) How many method line to show. Default 2
                .methodOffset(1)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(tag)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isShowLog;
            }
        });
    }

    public static void v(String message) {
        if (message == null) {
            return;
        }
        Logger.v(message);
    }

    public static void v(String tag, String message) {
        if (message == null) {
            return;
        }
        Logger.t(tag).v(message);
    }

    public static void d(String message) {
        if (message == null) {
            return;
        }
        Logger.d(message);
    }

    public static void d(String tag, String message) {
        if (message == null) {
            return;
        }
        Logger.t(tag).d(message);
    }

    public static void i(String message) {
        if (message == null) {
            return;
        }
        Logger.i(message);
    }

    public static void i(String tag, String message) {
        if (message == null) {
            return;
        }
        Logger.t(tag).i(message);
    }

    public static void w(String message) {
        if (message == null) {
            return;
        }
        Logger.w(message);
    }

    public static void w(String tag, String message) {
        if (message == null) {
            return;
        }
        Logger.t(tag).w(message);
    }

    public static void e(String message) {
        if (message == null) {
            return;
        }
        Logger.e(message);
    }

    public static void e(String tag, String message) {
        if (message == null) {
            return;
        }
        Logger.t(tag).e(message);
    }

    public static void a(String message) {
        if (message == null) {
            return;
        }
        Logger.wtf(message);
    }

    public static void a(String tag, String message) {
        if (message == null) {
            return;
        }
        Logger.t(tag).wtf(message);
    }

    public static void json(String json) {
        if (json == null) {
            return;
        }
        Logger.json(json);
    }

    public static void json(String tag, String json) {
        if (json == null) {
            return;
        }
        Logger.t(tag).json(json);
    }

    public static void s(String message) {
        if (message == null) {
            return;
        }
        System.out.println(message);
    }

}
