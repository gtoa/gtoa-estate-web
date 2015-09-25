package com.renren.gota.webserver.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.util.DigestUtils;

public class SessionUtil {

    private static final int SESSION_ID_BYTES = 16;

    public static synchronized String generateSessionId() {

        Random random = new SecureRandom();
        byte[] bytes = new byte[SESSION_ID_BYTES];
        random.nextBytes(bytes);
        return new String(DigestUtils.md5DigestAsHex(bytes));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(generateSessionId());
        }
    }
}
