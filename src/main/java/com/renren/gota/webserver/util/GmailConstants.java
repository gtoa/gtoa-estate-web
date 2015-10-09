package com.renren.gota.webserver.util;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-21 11:09.
 */
public class GmailConstants {

    public static final String APPLICATION_NAME = "SnowTest";

    public static final String CONSUMER_KEY = "970060892836-das3hoshrhec7bbvgsvj6sha7m780uci.apps.googleusercontent.com";
    public static final String CONSUMER_SECRET = "PRH1DyMF1pTDnbMayqcDYZ6w";

    public static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static final String GET_TOKEN_URL = "https://www.googleapis.com/oauth2/v3/token";
//    public static final String REDIRECT_URL = "http://mortgages.ren/token/code";
    public static final String REDIRECT_URL = "http://ec2-52-10-77-190.us-west-2.compute.amazonaws.com/token/code";
    public static final String IS_AUTH_EXPIRED_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo";

    public static final String CONTACT_SCOPE_READONLY = "https://www.googleapis.com/auth/contacts.readonly";
    public static final String CONTACT_SCOPE_RW = "https://www.google.com/m8/feeds";
    public static final String CONTACT_FEED_URL = "https://www.google.com/m8/feeds/contacts/default/full";



    public static final String CALENDAR_SCOPE_RW = "https://www.googleapis.com/auth/calendar";
    public static final String CALENDAR_SCOPE_READONLY = "https://www.googleapis.com/auth/calendar.readonly";
    public static final String CALENDAR_FEED_URL = "https://www.googleapis.com/calendar/v3/calendars/%s/events";

}
