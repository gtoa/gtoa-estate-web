package com.renren.gota.webserver.controller;

import com.renren.gota.webserver.model.UserInfo;
import com.renren.gota.webserver.utils.EncryptUtils;
import com.renren.gota.webserver.utils.HttpUtils;
import com.renren.gota.webserver.utils.OAuth1Util;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-10-14 14:07.
 */
@Controller
@RequestMapping(value = "thirdreg")
public class ThirdPartRegisterController {

    private Log logger = LogFactory.getLog(ThirdPartRegisterController.class);
    private static String CONSUMER_KEY = "DC6t76KQJk2Bwy47tkN0uOHlg";
    private static String CONSUER_SECRET = "4VGIWxnqDeAQM2drE15MhgBKwaDN1J5uSSP7kI9OxfhOQC4QVv";

    //回调方法
    @RequestMapping(value = "")
    public ModelAndView getThirdUserInfo(HttpServletRequest request, @RequestParam("thirdId") String thirdId,
                                   @RequestParam("thirdName") String thirdName,
                                   @RequestParam("thirdEmail") String thirdEmail,
                                    @RequestParam("thirdType") int thirdType) {
        ModelAndView mav = new ModelAndView("userInfo");
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(thirdEmail);
        userInfo.setThirdId(thirdId);
        userInfo.setThirdType(thirdType);
        userInfo.setUserName(thirdName);
        mav.addObject("user", userInfo);
        return mav;
    }


    //http://ec2-52-10-77-190.us-west-2.compute.amazonaws.com/thirdreg/twitter?
    // oauth_token=6NNS4QAAAAAAiJP_AAABUIMS70s&oauth_verifier=4JB5SEJRRU55YWhLSQt5NXj5rOGWYyHW

    /**
     * twitter 回调方法
     * @param request
     * @param oauthToken
     * @param oauthtokenSecret
     * @param oauthVerifier
     * @return
     */
    @RequestMapping(value = "twitter")
    @ResponseBody
    public String getTwitterUser(HttpServletRequest request,
                                 @RequestParam("oauth_token") String oauthToken,
                                 @RequestParam("oauth_token_secret") String oauthtokenSecret,
                                 @RequestParam("oauth_verifier") String oauthVerifier) {
        String postURL = "https://api.twitter.com/oauth/access_token";
//        String oauthNonce = genNonce();
//        String oauthTimeStamp = (new Date().getTime() + "").substring(0, 10);
//
//        String params = "oauth_consumer_key=DC6t76KQJk2Bwy47tkN0uOHlg&" +
//                "oauth_nonce=" + oauthNonce + "&" +
//                "oauth_signature_method=HMAC-SHA1&" +
//                "oauth_timestamp=" + oauthTimeStamp + "&" +
//                "oauth_token=" + oauthToken + "&" +
//                "oauth_version=1.0";
//        String signatureKey = CONSUER_SECRET + "&" + oauthtokenSecret;
//        try {
//            String signature = genSignature(postURL, params, signatureKey);
//            String oauthParam = " OAuth oauth_consumer_key=\"" + CONSUMER_KEY +
//                    "\", oauth_nonce=\"" + oauthNonce +
//                    "\", oauth_signature=\"" + signature +
//                    "\", oauth_signature_method=\"HMAC-SHA1\", " +
//                    "oauth_timestamp=\"" + oauthTimeStamp +
//                    "\", oauth_token=\"" + oauthToken +
//                    "\", oauth_version=\"1.0\"" ;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//     return request.toString();
        return "";
    }

    @RequestMapping(value = "twitter/token")
    @ResponseBody
    public String getTwitterToken() {
        String postURL = "https://api.twitter.com/oauth/request_token";
        try {
            JSONObject json = OAuth1Util.getRequestToken(postURL, CONSUMER_KEY, CONSUER_SECRET);
            return json.toString();
        } catch (Exception e) {
            logger.error("get token failed. ", e);
            return "{code:1, message:'" + e.getMessage() + "'}";
        }

    }

    private JSONObject getTwitterAccessToken(String oauthToken, String tokenSecret,
                                          String oauthVerifier) {
        String postURL = "https://api.twitter.com/1.1/account/verify_credentials.json";
        try {
            return OAuth1Util.getAccessToken(postURL, oauthToken, tokenSecret,
                    oauthVerifier, CONSUMER_KEY, CONSUER_SECRET);
        } catch (Exception e) {
            logger.error("" + e.getMessage(), e);
            JSONObject json = new JSONObject();
            json.put("code", 1);
            json.put("message", e.getMessage());
            return json;
        }
    }







    private static String requestTokenTest() {
        String postUrl = "https://api.twitter.com/oauth/request_token";

        try {
            JSONObject json = OAuth1Util.getRequestToken(postUrl, CONSUMER_KEY, CONSUER_SECRET);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String accessTokenTest(String token, String tokenSecret) {

//        oauth_nonce="47232f9573cf41a19d68769f7cd4de19", oauth_signature="n7wkUsdMb81wu%2BfD3dbKROiH%2Fn8%3D",
//                oauth_signature_method="HMAC-SHA1", oauth_timestamp="1445326437", o
        //        sIjMEgAAAAAAiJP_AAABUIQrPUY&oauth_verifier=4UfWdbvcdgGSD0RMqJvf2pVo7Kr9HL8i
//        h_token=sIjMEgAAAAAAiJP_AAABUIQrPUY&oauth_token_secret=Sn9q8KdiURvfKckkVaDI1T215pVPhSWj&oauth_cal

//        String postUrl = "https://api.twitter.com/oauth/access_token";
//
//        String nonce = "47232f9573cf41a19d68769f7cd4de19";
//        String timestamp = "1445326437";
//        String consumer_secret = "4VGIWxnqDeAQM2drE15MhgBKwaDN1J5uSSP7kI9OxfhOQC4QVv";
//        String token_secret = tokenSecret;
//
//        String params = "oauth_consumer_key=DC6t76KQJk2Bwy47tkN0uOHlg&" +
//                "oauth_nonce=" + nonce + "&" +
//                "oauth_signature_method=HMAC-SHA1&" +
//                "oauth_timestamp="  + timestamp + "&" +
//                "oauth_token=" + token + "&" +
//                "oauth_version=1.0";
//        try {
//            String signature = genSignature(postUrl,
//                    params, consumer_secret + "&" + token_secret);
//            String auth = " OAuth oauth_consumer_key=\"DC6t76KQJk2Bwy47tkN0uOHlg\", " +
//                    "oauth_nonce=\"" + nonce + "\", " +
//                    "oauth_signature=\"" + signature + "\", " +
//                    "oauth_signature_method=\"HMAC-SHA1\", " +
//                    "oauth_timestamp=\"" + timestamp + "\", " +
//                    "oauth_token=\"" + token + "\", " +
//                    "oauth_version=\"1.0\"";
//
//            System.out.println(auth);
//            System.out.println(signature);
//            String httpResult = HttpUtils.postMethod(postUrl, "oauth_verifier=4UfWdbvcdgGSD0RMqJvf2pVo7Kr9HL8i", auth);
//            System.out.println(httpResult);
//            return httpResult;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
        return "";
    }

    public static void main(String[] args) {

//        oauth_token=3877850233-JKZsgNtjT7kh7XRBToeM5SGnCPlMSkn457dO20B
// &oauth_token_secret=iudFHyBHqTsgcPOqZOMHztgM2kgKSU6PgcPNILRf4LkQu&
// user_id=3877850233&screen_name=GtoaRenren&x_auth_expires=0

//        requestTokenTest();
//        accessTokenTest("sIjMEgAAAAAAiJP_AAABUIQrPUY", "Sn9q8KdiURvfKckkVaDI1T215pVPhSWj");
        String  aa = "login";
        try {
            System.out.println(EncryptUtils.encodeByMD5(aa));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
