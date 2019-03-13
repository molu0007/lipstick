package com.ibupush.molu.common.util;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间日期工具类
 *
 * @author Shyky
 * @date 2017/6/20
 */
public final class TimeUtil {
    private TimeUtil() {

    }

    public static String gettime(String data) {
        String sR = "";

        Date date = new Date();

        SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String times = from.format(date);
        try {
            long re_mm = getCompareDate(
                    from.format(new Date((Long.valueOf(data) * 1000))), times);
            if (re_mm <= 0) {
                sR = "1分钟前";
            }
            if (re_mm > 0 && re_mm < 60) {
                sR = String.valueOf(re_mm) + "分钟前";
            }

            if (re_mm < 60 * 24 && re_mm >= 60) {
                sR = String.valueOf(re_mm / 60) + "小时前";
            }

            if (re_mm >= 60 * 24 && re_mm < 60 * 24 * 7) {
                sR = String.valueOf(re_mm / (60 * 24)) + "天前";
            }

            if (re_mm >= 60 * 24 * 7) {
                sR = getPosttime(Long.valueOf(data));
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return sR;
    }

    public static String getPosttime(long data) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(data * 1000L);
        return new SimpleDateFormat("MM-dd HH:mm").format(mCalendar.getTime());
    }

    public static long getCompareDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = formatter.parse(startDate);
        Date date2 = formatter.parse(endDate);
        long l = date2.getTime() - date1.getTime();
        long d = l / (60 * 1000);
        return d;
    }

    public static String onGetTimeAgo(long time) {
        long re_mm = (System.currentTimeMillis() - time) / (60 * 1000);
        String sR = "刚刚";
        if (re_mm <= 0) {
            sR = "刚刚";
        }
        if (re_mm > 0 && re_mm < 60) {
            sR = String.valueOf(re_mm) + "分钟前";
        }

        if (re_mm < 60 * 24 && re_mm >= 60) {
            sR = String.valueOf(re_mm / 60) + "小时前";
        }

        if (re_mm >= 60 * 24 && re_mm < 60 * 24 * 7) {
            sR = String.valueOf(re_mm / (60 * 24)) + "天前";
        }

        if (re_mm >= 60 * 24 * 7 && re_mm < 60 * 24 * 30) {
            sR = String.valueOf(re_mm / (60 * 24 * 7)) + "周前";
        }

        if (re_mm >= 60 * 24 * 30 && re_mm < 60 * 24 * 365) {
            sR = String.valueOf(re_mm / (60 * 24 * 30)) + "个月前";
        }

        if (re_mm >= 60 * 24 * 365) {
            sR = String.valueOf(re_mm / (60 * 24 * 365)) + "年前";
        }
        return sR;
    }

    /**
     * yyyy.MM.dd转为yyyy-MM-dd
     *
     * @param source
     * @return
     */
    public static String getYMDStr(String source) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);

        try {
            Date dateParse = sdf.parse(source);
            return format.format(dateParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 毫秒转为mm分ss秒
     *
     * @param time
     * @return
     */
    public static String getMMSS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm分ss秒", Locale.CHINA);
        try {
            Date dateParse = new Date(time);
            return sdf.format(dateParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatTime(long timeMillis, @NonNull String formatStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr, Locale.CHINA);
            return simpleDateFormat.format(new Date(timeMillis));
        } catch (Exception e) {
            throw new IllegalArgumentException("formatStr格式化参数不对，请检查！");
        }
    }

    /**
     * Returns the current time in milliseconds.
     *
     * @return current time in milliseconds
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前系统时间，如：2017-11-23 15:25:15
     *
     * @return yyyy-MM-dd HH:mm:ss格式的系统时间
     */
    public static String getCurrentTime() {
        return formatTime(getCurrentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前系统时间
     *
     * @param formatStr 时间格式化字符串
     * @return 如果时间格式化字符串不为空，返回指定格式的当前系统时间，否则返回yyyy-MM-dd HH:mm:ss格式的系统时间
     */
    public static String getCurrentTime(@NonNull String formatStr) {
        if (TextUtil.isEmpty(formatStr))
            return formatTime(getCurrentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        return formatTime(getCurrentTimeMillis(), formatStr);
    }

    /**
     * 获取当前系统时间的年份，如：2017
     *
     * @return 年份
     */
    public static int getCurrentYear() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.MONTH);
    }

    public static int getCurrentDayOfMonth() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDayOfWeek() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getCurrentWeekOfMonth() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public static int getCurrentHour() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.HOUR);
    }

    public static int getCurrentMinute() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getCurrentSecond() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.SECOND);
    }

    public static int getYear(long timeMillis) {
        return NumberUtil.toInt(formatTime(timeMillis, "yyyy"));
    }

    public static int getMonth(long timeMillis) {
        return NumberUtil.toInt(formatTime(timeMillis, "MM"));
    }

    public static int getDayOfMonth(long timeMillis) {
        return NumberUtil.toInt(formatTime(timeMillis, "dd"));
    }

    public static String getTime(long timeMillis) {
        return getTime(timeMillis, null);
    }

    /**
     * 根据传入的时间戳和本地时间比较，默认返回yyyy-MM-dd HH:mm格式的时间，如：今天12:50、昨天13:40、2013-12-20 12:30
     *
     * @param timeMillis 时间戳
     * @param formatStr  格式化字符串
     * @return 默认返回yyyy-MM-dd HH:mm格式的时间，如果格式化字符串不为空，则返回指定格式的时间
     */
    public static String getTime(long timeMillis, @NonNull String formatStr) {
        final int day = getDayOfMonth(timeMillis);
        final int currentDay = getCurrentDayOfMonth();
        final StringBuilder time = new StringBuilder();
        if (day == currentDay - 2) {
            time.append("前天");
        } else if (day == currentDay - 1) {
            time.append("昨天");
        } else if (day == currentDay) {
            time.append("今天");
        } else if (day == currentDay + 1) {
            time.append("明天");
        } else if (day == currentDay + 2) {
            time.append("后天");
        } else {
            if (TextUtil.isEmpty(formatStr)) {
                time.append(formatTime(timeMillis, "yyyy-MM-dd HH:mm"));
            } else {
                time.append(formatTime(timeMillis, formatStr));
            }
            return time.toString();
        }
        return time.append(formatTime(timeMillis, "HH:mm")).toString();
    }

    public static String getWeek(long timeMillis) {
        final String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final StringBuilder str = new StringBuilder();
        int currentDay = getCurrentDayOfMonth();
        if (currentDay - 7 == day) {
            str.append("上");
        } else if (day == currentDay) {
            str.append("本");
        } else if (currentDay + 7 == day) {
            str.append("下");
        }
        if (week < 0) {
            week = 0;
        }
        str.append(weekOfDays[week]);
        return str.toString();
    }

    /**
     * 毫秒转为2017/03/23 13:00:20
     *
     * @param time
     * @return
     */
    public static String getTimeStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
        try {
            Date dateParse = new Date(time);
            return sdf.format(dateParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 毫秒转为2017/03/23
     *
     * @param time
     * @return
     */
    public static String getHHMMDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        try {
            Date dateParse = new Date(time);
            return sdf.format(dateParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 毫秒转为2017.03.23
     *
     * @param time
     * @return
     */
    public static String getYYMMDD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        try {
            Date dateParse = new Date(time);
            return sdf.format(dateParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 毫秒转为HH:mm
     *
     * @param time
     * @return
     */
    public static String getHHmm(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        try {
            Date dateParse = new Date(time);
            return sdf.format(dateParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 距离今年多少年
     *
     * @param timeMillis 毫秒数
     * @return 多少年
     */
    public static int getYearAgo(long timeMillis) {
        String year = formatTime(timeMillis, "yyyy");
        return getCurrentYear() - NumberUtil.toInt(year);
    }

    /**
     * 判断指定的时间是否是当前系统时间
     *
     * @param timeMillis 毫秒数
     * @return 是今天返回true，否则返回fasle
     */
    public static boolean isToday(long timeMillis) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        // 当前系统时间的月份和日期与指定时间的月份和日期相等则是同一天
        return getCurrentMonth() == calendar.get(Calendar.MONTH)
                && getCurrentDayOfMonth() == calendar.get(Calendar.DAY_OF_MONTH);
    }
}