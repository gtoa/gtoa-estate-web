package com.renren.gota.webserver.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

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


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String postMethod(String url, String param, String auth) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.addRequestProperty("accept", "*/*");
            conn.addRequestProperty("connection", "Keep-Alive");
            conn.addRequestProperty("Authorization", auth);

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


}
