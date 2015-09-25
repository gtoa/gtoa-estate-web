package com.renren.gota.webserver.util;

import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.model.UserToken;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-22 19:22.
 */
public class OAuth2Util {

    private static Log logger = LogFactory.getLog(OAuth2Util.class);

    /**
     *
     * @param code 调用https://accounts.google.com/o/oauth2/auth授权接口获取的code
     * @return 存储获取的token 和 refresh_token 返回token值
     */
    public static UserToken getAccessTokenByCode(String code) {
        UserToken ut = null;
        NameValuePair[] params = {
                new NameValuePair("code", code),
                new NameValuePair("client_id", GmailConstants.CONSUMER_KEY),
                new NameValuePair("client_secret", GmailConstants.CONSUMER_SECRET),
                new NameValuePair("redirect_uri", GmailConstants.REDIRECT_URL),
                new NameValuePair("grant_type", "authorization_code"),
        };
        try {
            JSONObject json = JSONObject.fromObject(HttpUtils.postMethod(GmailConstants.GET_TOKEN_URL, params));
            String accessToken = json.getString("access_token").trim();
            String refreshToken = json.getString("refresh_token").trim();
            ut = new UserToken();
            ut.setAccessToken(accessToken);
            ut.setRefreshToken(refreshToken);
            ut.setCode(code);

        } catch (IOException e) {
            logger.error("get token failed. ", e);

        }
        return ut;
    }

    /**
     *
     * @param refreshToken 重新获取token
     * @return
     */
    public static String refreshAccessToken(String refreshToken) {
        String rs = "";

        NameValuePair[] params = {
                new NameValuePair("client_id", GmailConstants.CONSUMER_KEY),
                new NameValuePair("client_secret", GmailConstants.CONSUMER_SECRET),
                new NameValuePair("refresh_token", refreshToken),
                new NameValuePair("grant_type", "refresh_token"),
        };
        try {
            JSONObject json = JSONObject.fromObject(HttpUtils.postMethod(GmailConstants.GET_TOKEN_URL, params));
            String accessToken = json.getString("access_token").trim();

            //TODO:  更新token值

            rs = accessToken;
        } catch (IOException e) {
            logger.error("get token failed. ", e);
        }
        return rs;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {

        boolean rs = true;
        NameValuePair[] params = {
                new NameValuePair("access_token", token),
        };

        try {
            JSONObject json = JSONObject.fromObject(HttpUtils.getMethod(GmailConstants.IS_AUTH_EXPIRED_URL, params));
            rs = json.containsKey("error");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }


    /**
     *  如果token过期，则根据refreshToken重新获取
     * @param token
     * @param refreshToken
     * @return
     */
    public static String getAccessToken(String token, String refreshToken) {

        if(isTokenExpired(token)) {
            token = refreshAccessToken(refreshToken);
        }
        return token;

    }

    public static void main(String[] args) {
        System.out.println(isTokenExpired("ya29.9gFUcgIsGj2GHxeYvD2BKidKFKyhERLD0sl9iyGNAIBhOqQY2eHj-h0pkCjBRZ-gMR_1fQ"));
    }


}
