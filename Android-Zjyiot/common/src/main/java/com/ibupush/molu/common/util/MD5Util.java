package com.ibupush.molu.common.util;

import android.support.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shyky on 2017/7/10.
 */
public final class MD5Util {
    private MD5Util() {

    }

    public static String encrypt(@NonNull String str) {
        // MD5加密，32位
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * MD5加密
     *
     * @param str
     * @return
     * @throws Exception
     * @author: 研发部-孙尉凯
     */
    public static byte[] digest(String str) {
        //获取加密方式为md5的算法对象
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            return digest;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * MD5加密后的内容转换位十六进制内容字符串形式
     *
     * @param str
     * @return
     * @throws Exception
     * @author: 研发部-孙尉凯
     */
    public static String md5Encrypt(String str) {
        if (TextUtil.isEmpty(str)) {
            throw new RuntimeException("The string to be encrypted cannot be empty.");
        }
        byte[] bytes = digest(str);

        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String hexStr = Integer.toHexString((b & 0xff));
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            builder.append(hexStr.toLowerCase());
        }
        return builder.toString();
    }
}