package com.qyy.app.lipstick.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qyy.app.lipstick.BaseApplication;

/**
 * Created by zjh on 2017/6/28.
 * SharePreference工具类
 */

public class PrefsUtil {
    private static SharedPreferences mInstance = null;

    /**
     * 用户账号
     */
    public static final String ACCOUNT = "accountId";
    /**
     * 用户账号
     */
    public static final String Token = "token";

    public static String getToken() {
        if (token==null){
            return getString(Token);
        }
        return token;
    }

    public static void setToken(String token) {
        PrefsUtil.token = token;
        savaString(Token,token);
    }

    private static String token;
    // double check and lock
    private static SharedPreferences getInstance(Context ctx) {
        if (mInstance == null) {
            synchronized (PrefsUtil.class) {
                if (mInstance == null) {
                    mInstance = PreferenceManager.getDefaultSharedPreferences(ctx
                            .getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private static SharedPreferences get() {
        return getInstance(BaseApplication.getAppContext().getApplicationContext());
    }

    /**
     * 保存布尔值
     *
     * 
     * @param key   键
     * @param value 值
     */
    public static void saveBoolean( String key, boolean value) {
        SharedPreferences sp = get();
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 读取布尔值
     *
     * 
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static boolean getBoolean( String key, boolean defaultValue) {
        SharedPreferences p = get();
        boolean value = p.getBoolean(key, defaultValue);
        return value;
    }

    /**
     * 读取布尔值

     * @param key 键
     * @return 默认返回false
     */
    public static boolean getBoolean( String key) {
        return getBoolean( key, false);
    }

    public static void savaString( String key, String value) {
        SharedPreferences sp = get();
        sp.edit().putString(key, value).apply();
    }

    public static String getString( String key, String defaultValue) {
        SharedPreferences p = get();
        String value = p.getString(key, defaultValue);
        return value;
    }

    public static String getString( String key) {
        SharedPreferences p = get();
        String value = p.getString(key, "");
        return value;
    }

    public static void savaInteger( String key, int value) {
        SharedPreferences sp = get();
        sp.edit().putInt(key, value).apply();
    }

    public static int getInteger( String key, int defaultValue) {
        SharedPreferences p = get();
        int value = p.getInt(key, defaultValue);
        return value;
    }

    /**
     * 保存数据
     * @param key   键
     * @param value 值
     */
    public static void savaFloat( String key, float value) {
        SharedPreferences sp = get();
        sp.edit().putFloat(key, value).apply();
    }

    /**
     * 读取数据
     *
     * 
     * @param key          键
     * @param defaultValue 默认值
     */
    public static float getFloat( String key, int defaultValue) {
        SharedPreferences p = get();
        return p.getFloat(key, defaultValue);
    }

    /**
     * 读取数据
     *
     *
     * @param key 键
     */
    public static float getFloat( String key) {
        SharedPreferences p = get();
        return p.getFloat(key, 0.0f);
    }

    /**
     * 保存account
     *
     *
     * @return
     */
    public static void saveAccountId( String accountId) {
        SharedPreferences sp = get();
        sp.edit().putString(ACCOUNT, accountId).apply();
    }

    /**
     * 获取account
     *
     * @param c 上下文
     * @return
     */
    public static String getAccountId(Context c) {
        SharedPreferences p = get();
        String account = p.getString(ACCOUNT, "");
        return account;
    }

}
