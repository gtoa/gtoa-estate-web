package com.renren.gota.webserver.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-10-20 15:54.
 */
public class OAuth1Util {

    /**
     * 获取 request_token
     * @param url
     * @param consumerKey
     * @param consumerSecret
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static JSONObject getRequestToken(String url, String consumerKey, String consumerSecret)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String oauthNonce = genNonce();
        String oauthTimeStamp = (new Date().getTime() + "").substring(0, 10);

        String params = "oauth_consumer_key=" + consumerKey + "&" +
                "oauth_nonce=" + oauthNonce + "&" +
                "oauth_signature_method=HMAC-SHA1&" +
                "oauth_timestamp=" + oauthTimeStamp + "&" +
                "oauth_version=1.0";
        String signature = genSignature("POST", url, params, consumerSecret + "&");

        String oauth = " OAuth oauth_consumer_key=\"" + consumerKey +
                "\", oauth_nonce=\"" + oauthNonce +
                "\", oauth_signature=\"" + signature +
                "\", oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_timestamp=\"" + oauthTimeStamp +
                "\", oauth_version=\"1.0\"" ;

        System.out.println(oauth);
        String httpResult = HttpUtils.postMethod(url, "", oauth);
        System.out.println(httpResult);
        JSONObject json = parseTokenData(httpResult);
        return json;
    }

    /**
     * 获取访问接口的 access_token access_token_secret
     * @param url
     * @param oauthToken
     * @param tokenSecret
     * @param oauthVerifier
     * @param consumerKey
     * @param consumerSecret
     * @return
     */
    public static JSONObject getAccessToken(String url, String oauthToken,
                                            String tokenSecret, String oauthVerifier,
                                            String consumerKey, String consumerSecret)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String oauthNonce = genNonce();
        String oauthTimeStamp = (new Date().getTime() + "").substring(0, 10);

        String params = "oauth_consumer_key=" + consumerKey + "&" +
                "oauth_nonce=" + oauthNonce + "&" +
                "oauth_signature_method=HMAC-SHA1&" +
                "oauth_timestamp=" + oauthTimeStamp + "&" +
                "oauth_token=" + oauthToken + "&" +
                "oauth_version=1.0";
            String signature = genSignature("post", url, params, consumerSecret + "&" + tokenSecret);
            params += "&oauth_signature=" + signature;

            String oauth = " OAuth oauth_consumer_key=\"" + consumerKey +
                    "\", oauth_nonce=\"" + oauthNonce +
                    "\", oauth_signature=\"" + signature +
                    "\", oauth_signature_method=\"HMAC-SHA1\", " +
                    "oauth_timestamp=\"" + oauthTimeStamp +
                    "\", oauth_token=\"" + oauthToken +
                    "\", oauth_version=\"1.0\"" ;

            System.out.println(oauth);
            String httpResult = HttpUtils.postMethod(url, "oauth_verifier=" + oauthVerifier, oauth);
            System.out.println(httpResult);
            JSONObject json = parseTokenData(httpResult);
            return json;

    }

    /**
     * 每次的nonce都要变化
     * @return
     */
    private static String genNonce() {
        return getRandomStr(32);
    }
    private static String getRandomStr(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成签名 twitter 中的签名生成过程
     * 1. 原始数据 ＝ 请求方式(POST/GET) + 请求URL(urlencode加密) ＋ 参数(key值排序, urlencode加密)
     * 2. 对原始数据加密的key ＝ oauth_consumer_key
     * 3. 生成数字签名
     * 4. 对该签名进行urlencode加密
     * @param url 连接
     * @param params 参数
     * @param key
     * @return
     *
     */
    private static String genSignature(String method, String url, String params, String key)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String result = "";
        String originData = StringUtils.upperCase(method) + "&";
        String encodeUrl = URLEncoder.encode(url);
        originData += encodeUrl + "&";
        originData += URLEncoder.encode(params);
        String hsData = EncryptUtils.encodeHmacSHA1(originData, key);
        result = URLEncoder.encode(hsData);

        return result;
    }


    /**
     *
     * @param data 格式为：oauth_token=***&oauth_token_secret&oauth_callback_confirmed=**类数据
     * @return {'oauth_token':'***', 'oauth_token_secet':'***'}
     */
    private static JSONObject parseTokenData(String data) {
        JSONObject json = new JSONObject();
        String[] arrToken = data.split("&");
        for(String item : arrToken) {
            String[] arrTokenTemp = item.split("=");
            if(arrTokenTemp.length == 2 )
                json.put(arrTokenTemp[0], arrTokenTemp[1]);

        }
        return json;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.upperCase("djfaoisf"));
    }
}
