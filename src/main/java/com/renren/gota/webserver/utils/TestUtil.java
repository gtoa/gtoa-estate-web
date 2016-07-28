package com.renren.gota.webserver.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-12-30 15:11.
 */
public class TestUtil {
    final static String USER_SEARCH_QUERY = "https://www.facebook.com/public?query=%s&type=all";

    public static String emailToID(String email)
    {
        try
        {
            String html = getHTML(String.format(USER_SEARCH_QUERY, email));
            parseHtml(html);
//            if (html != null)
//            {
//                int i = html.indexOf(USER_URL_PREFIX) + USER_URL_PREFIX.length();
//                if (i > 0)
//                {
//                    StringBuilder sb = new StringBuilder();
//                    char c;
//                    while (Character.isDigit(c = html.charAt(i++)))
//                        sb.append(c);
//                    if (sb.length() > 0)
//                        return sb.toString();
//                }
//            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements codes = doc.getElementsByTag("code");
        System.out.println(codes.size());
        for(Element elem : codes) {
            Document doc1 = Jsoup.parse(elem.html().replace("<!--", "").replace("-->", ""));
            //get img
            Elements headImg = doc1.getElementsByTag("img");
            if(headImg != null && headImg.size() > 0)
                System.out.println(" headImg: " + headImg.get(0).attr("src"));

            Elements nameElem = doc1.select("div.instant_search_title a");
            if(nameElem != null && headImg.size() > 0) {
                System.out.println(" name: " + nameElem.html());
                System.out.println(" url: " + nameElem.attr("href"));
            }

            //Other
            Elements otherElem = doc1.select("span.fbProfileBylineLabel");
            if( null != otherElem && otherElem.size() > 0 ) {
                for(Element other : otherElem) {
                    String otherText = other.text();
                    if(otherText.contains("Works at")) {
                        System.out.println(otherText.replace("Works at", ""));
                    }
                }
            }

        }
    }

    private static String getHTML(String htmlUrl) throws Exception
    {
        StringBuilder response = new StringBuilder();
        URL url = new URL(htmlUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");
        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpConn.getInputStream()), 8192);
            String strLine = null;
            while ((strLine = input.readLine()) != null)
                response.append(strLine);
            input.close();
        }
        return (response.length() == 0) ? null : response.toString();
    }

    public static void main(String[] args) {
        emailToID("315560948@qq.com");
    }
}
