package com.ibupush.molu.common.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * JDK自带的RSA加密方法
 * <br/>
 * 将byte[]使用公钥进行加密,得到带有加密信息的secret byte[];
 * <br/>
 * 将secret byte[]使用私钥进行解密，还原成加密前的byte[].
 * <br/>
 */
public class RSAUtil {
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    public static Map<String, Object> getKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        SecureRandom random = new SecureRandom();
        keyPairGenerator.initialize(1024, random);

        // 生成钥匙对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> map = new HashMap<>();
        map.put(PUBLIC_KEY, publicKey);
        map.put(PRIVATE_KEY, privateKey);

        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * <p>
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对二进制内容进行加密
     */
    public static byte[] encrypt(byte[] bytes, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        // 设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // 对数据进行加密
        byte[] result = cipher.doFinal(bytes);

        return result;
    }

    /**
     * 对二进制内容进行解密
     */
    public static byte[] decrypt(byte[] bytes, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 对数据进行解密
        byte[] result = cipher.doFinal(bytes);

        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return String
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            //将每个byte转换成16进制字符串，方便展示和记录。
            String hex = Integer.toHexString(buf[i] & 0xFF);  //如果不& 0xFF，结果会自动变为32位ffffffbe，没必要。8位就够了be。
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return byte[]
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        int index = 0;
        for (int i = 0; i < hexStr.length(); i = i + 2) {
            //该字符串之前就是1byte转成2十六进制字符的，现将2十六进制字符转回成1byte
            int hex = Integer.parseInt(hexStr.substring(i, i + 2), 16);
            result[index++] = (byte) hex;
        }
        return result;
    }

    /**
     * 根据传入的明文密码，返回加密后的字符串
     *
     * @param mingPwd  明文密码
     * @param modulus  后台返回
     * @param exponent 后台返回
     * @return
     */
    public static String getRSAPwd(String mingPwd, String modulus, String exponent) {
        try {
            RSAPublicKey publicKey = RSAUtil.getPublicKey(modulus, exponent);
            byte[] b = RSAUtil.encrypt(mingPwd.getBytes(), publicKey);
            return RSAUtil.parseByte2HexStr(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main1(String[] args) throws Exception {
        String password = "sunweikai123_!*/+=@#$%^";
        System.out.println("加密前：" + password);

        //创建密钥
        Map<String, Object> map = getKeys();

        //获取公钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get(PUBLIC_KEY);

        //获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get(PRIVATE_KEY);

        //----和js协作的测试部分(可以注销) begin------------
        privateKey = checkJS(publicKey, privateKey);
        //	    	String mi = "1b176dc0055b128243b2b79cf60a9eb0c6434e6e4cd2b16ae82a64d9a56921839befc467a54d1b7985e9f234a74eb69ff745670a79f7c2721242b60e9363c9aff096226faa0cccca0b28edb1d66547a3262714681aef0931b8fb96e9bf06f1342c23b9160f3bde3413bc61d9d3730af882b39dd7b5f4b3e5883c38219886f6c2";
        //	    	byte[] secret = parseHexStr2Byte(mi);
        //----和js协作的测试部分 end------------

        //对byte[]进行加密
        byte[] b = encrypt(password.getBytes(), publicKey);

        //----展示、记录加密后的内容，将内容再次变成加密的byte数组后进行解密(可以注销) begin----
        //	    	b = testShow(b);
        //----展示、记录加密后的内容，将内容再次变成加密的byte数组后进行解密 end----

        //对byte[]进行解密
        b = decrypt(b, privateKey);
        System.out.println("解密后：" + new String(b));
    }

    /**
     * 验证js中的加密解密是否和java一致
     */
    private static RSAPrivateKey checkJS(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        String modulusHexStr = publicKey.getModulus().toString(16);
        System.out.println(modulusHexStr);
        String modulus = publicKey.getModulus().toString();
        System.out.println(modulus);
        String publicExponent = publicKey.getPublicExponent().toString(16);
        System.out.println(publicExponent);
        publicExponent = publicKey.getPublicExponent().toString();
        System.out.println(publicExponent);
        String privateExponent = privateKey.getPrivateExponent().toString();
        System.out.println(privateExponent);

        modulus = "94044610947280646322210379219625268586683568772370355770914778775706497425679886730660671559074022776628158938654644081511272553816404014224087574499075265397885621387613980958646012649609853963854215119559709293869865525111783980280107488962736241821664925262405651092754295248138215895862076100221516278143";
        //    	publicExponent = "65537";
        //    	String publicExponentHexStr = "10001";
        privateExponent = "32127878824155307543951785714149108723122181687361336273479270976841978895334650411412816813954076261439001639952070225078289839440247028006516879098216208813836629324810605534007216584122547642712326610037440390826313935784454193753407799019189361959340243344465756948695220250215508885748430163930754340929";
        privateKey = getPrivateKey(modulus, privateExponent);
        return privateKey;
    }

    /**
     * 测试对密数组进行16进制形式的字符串输出（直接把密数组变成字符串会是乱码），和反转成密数组
     *
     * @author: 研发部-孙尉凯
     */
    private static byte[] testShow(byte[] secret) {
        //展示或者记录byte数组不方便，就将它变成字符串，但是加密后的byte数组直接变成new String(byte[])会成乱码，就把byte数组补齐变成16进制，再以16进制字符串的形式展示或者记录
        String hexStr = parseByte2HexStr(secret);
        System.out.println("加密后产生的字节数组，转换程16进制字符串如下：");
        System.out.println("大写：" + hexStr);
        System.out.println("小写：" + hexStr.toLowerCase());
        //    	System.out.println(hexStr.getBytes().length);
        byte[] b = parseHexStr2Byte(parseByte2HexStr(secret));
        return b;
    }
}