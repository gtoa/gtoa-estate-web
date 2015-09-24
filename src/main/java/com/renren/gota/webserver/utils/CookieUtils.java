package com.renren.gota.webserver.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key)) return cookies[i].getValue();
        }
        return null;
    }

    public static void saveCookie(HttpServletResponse response, String key, String value) {
        CookieUtils.saveCookie(response, key, value, -1);
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int second) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(second);
        response.addCookie(cookie);
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int maxAge, String path,
        String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response, String key, String domain) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
}
