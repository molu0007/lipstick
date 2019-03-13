package com.ibupush.molu.common.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过正则表达式对常用的数据进行验证
 *
 * @author Administrator
 */
public final class ValidateUtil {
    private ValidateUtil() {

    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean IsValidMobileNo(String mobielNo) {
        Pattern p = Pattern.compile("^(1[3-9])\\d{9}$");
        Matcher m = p.matcher(mobielNo);
        return !m.matches();
    }

    /**
     * 验证是否是正确的密码
     *
     * @param psw
     * @return true 正确
     */
    public static boolean isCorrectPsw(String psw) {
        if (!TextUtils.isEmpty(psw)) {
            if (psw.length() >= 6 && psw.length() <= 15) {
                return !psw.contains(" ") && !isContainChinese(psw);
            }
        }
        return false;
    }

    /**
     * 验证固定电话
     *
     * @param phoneNo
     * @return
     */
    public static boolean IsValidPhoneNo(String phoneNo) {
        Pattern p = Pattern.compile("(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$");
        Matcher m = p.matcher(phoneNo);
        return !m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean IsValidEmail(String email) {
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                        "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return !m.matches();
    }

    /**
     * 验证IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean IsValidIPAddress(String ipAddress) {
        Pattern p = Pattern
                .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
                        "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
                        "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
                        "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = p.matcher(ipAddress);
        return !m.matches();
    }

    /**
     * 验证日期（）
     *
     * @param date
     * @return
     */
    public static boolean IsValidDate(String date) {
        Pattern p = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|" +
                        "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +
                        "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|" +
                        "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))" +
                        "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" +
                        "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" +
                        "(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|" +
                        "([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = p.matcher(date);
        return !m.matches();
    }

    /**
     * 验证身份证号码
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        Pattern p = Pattern.compile("[1-9]\\d{13,16}[a-zA-Z0-9]{1}");
        Matcher m = p.matcher(idCard);
        return !m.matches();
    }

    /**
     * 验证正整数
     *
     * @param digit
     * @return
     */
    public static boolean IsValidDigit(String digit) {
        Pattern p = Pattern.compile("^\\d*[1-9]\\d*$");
        Matcher m = p.matcher(digit);
        return !m.matches();
    }

    /**
     * 验证正浮点数
     *
     * @param decimals
     * @return
     */
    public static boolean IsValidDecimals(String decimals) {
        Pattern p = Pattern
                .compile("^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$");
        Matcher m = p.matcher(decimals);
        return !m.matches();
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean IsValidBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return !Pattern.matches(regex, blankSpace);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean IsValidChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return !Pattern.matches(regex, chinese);
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean IsValidBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return !Pattern.matches(regex, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
     *            http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean IsValidURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??" +
                "(" +
                ".+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean IsValidPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return !Pattern.matches(regex, postcode);
    }

    /**
     * 验证是否是手机号码
     *
     * @param phone
     * @return
     */
    public static boolean IsValidMobileNoNew(String phone) {
        String[] strArray = new String[]{ // 移动
                "139", "138", "137", "136", "135", "134", "147", "150", "151", "152", "157",
                "158", "159", "182", "183", "184", "187", "188", "178", "179", "177",
                // 联通
                "130", "131", "132", "155", "156", "185", "186", "145", "167", "168", "169",
                "197", "198", "199",
                // 电信
                "133", "153", "180", "181", "189"};
        StringBuilder valid = new StringBuilder("");
        for (int i = 0; i < strArray.length; i++) {
            valid.append("|").append(strArray[i]);
        }
        Pattern p = Pattern.compile("^(" + valid.substring(1) + ")\\d{8}$");
        return p.matcher(phone).matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean IsValidEmailNew(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\" +
                    ".)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判断字符串是否中文
     *
     * @param str 字符串
     * @return true 含
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断字符是否是中文汉字
     *
     * @param c 要检查的字符
     * @return 是返回true，不是返回false
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是中文汉字
     *
     * @param str 要检查的字符串
     * @return 是返回true，不是返回false
     */
    public static boolean isChinese(@NonNull String str) {
        boolean result = true;
        char[] temp = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (!isChinese(temp[i])) {
                result = false;
                break;
            }
        }
        return result;
    }
}