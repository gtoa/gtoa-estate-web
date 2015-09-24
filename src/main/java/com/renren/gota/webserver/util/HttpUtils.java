package com.renren.gota.webserver.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class HttpUtils {

    public static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String getMethod(String methodName, NameValuePair[] parameters) throws HttpException, IOException {
        HttpClient httpClient = null;
        GetMethod method = null;
        InputStream inputStream = null;
        BufferedReader br = null;
        try {
            httpClient = new HttpClient();
            // 设置 Http 连接超时为5秒
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            /* 2 生成 GetMethod 对象并设置参数 */
            method = new GetMethod(methodName);
            method.setQueryString(parameters);
            /* 3 执行 HTTP GET 请求 */
            int statusCode = httpClient.executeMethod(method);
            /* 4 判断访问的状态码 */
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Request URL:" + methodName + "|params:" + parameters.toString() + "|statusLine:"
                             + method.getStatusLine());
            }
            /* 5 处理 HTTP 响应内容 */
            inputStream = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } finally {
            method.releaseConnection();
            ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != br) {
                br.close();
            }
        }
    }

    public static String postMethod(String methodName, NameValuePair[] parameters) throws HttpException, IOException {
        HttpClient httpClient = null;
        PostMethod method = null;
        InputStream inputStream = null;
        BufferedReader br = null;
        try {
            httpClient = new HttpClient();
            // 设置 Http 连接超时为5秒
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            /* 2 生成 PostMethod 对象并设置参数 */
            method = new PostMethod(methodName);
            method.setQueryString(parameters);
            /* 3 执行 HTTP post 请求 */
            int statusCode = httpClient.executeMethod(method);
            /* 4 判断访问的状态码 */
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("Request URL:" + methodName + "|params:" + parameters + "|statusLine:" + method.getStatusLine());
            }
            /* 5 处理 HTTP 响应内容 */
            inputStream = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } finally {
            method.releaseConnection();
            ((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != br) {
                br.close();
            }
        }
    }


}
