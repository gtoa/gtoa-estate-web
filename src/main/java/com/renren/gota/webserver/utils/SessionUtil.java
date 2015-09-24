package com.renren.gota.webserver.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionUtil {

    private static Log LOG = LogFactory.getLog(SessionUtil.class);

    private static final int SESSION_ID_BYTES = 16;

    public static synchronized String generateSessionId() {

        Random random = new SecureRandom();
        byte[] bytes = new byte[SESSION_ID_BYTES];
        random.nextBytes(bytes);

        MessageDigest md = getDigest();
        if (null == md) {
            return null;
        }
        bytes = md.digest(bytes);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10) {
                sb.append((char) ('0' + b1));
            } else {
                sb.append((char) ('A' + (b1 - 10)));
            }
            if (b2 < 10) {
                sb.append((char) ('0' + b2));
            } else {
                sb.append((char) ('A' + (b2 - 10)));
            }
        }
        return sb.toString();
    }

    public static MessageDigest getDigest() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md;
        } catch (NoSuchAlgorithmException e) {
            LOG.warn("Get Digest fail", e);
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(generateSessionId());
        }
    }
}
