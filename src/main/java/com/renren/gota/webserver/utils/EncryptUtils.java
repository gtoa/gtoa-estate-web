package com.renren.gota.webserver.utils;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-10-19 20:06.
 */
public class EncryptUtils {

    /**
     * 生成 md5加密字符串
     * @param originData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encodeByMD5(String originData) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(originData.getBytes("utf-8")));
        return newstr;
    }


    public static String encodeHmacSHA1(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] byteHMAC = null;
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        mac.init(spec);
        byteHMAC = mac.doFinal(data.getBytes());

        String puzzle = new BASE64Encoder().encode(byteHMAC);
        return puzzle;
    }
}
