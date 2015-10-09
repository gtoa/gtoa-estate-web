package com.renren.gota.webserver.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.renren.gota.webserver.model.CalendarEvent;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-21 11:04.
 */
public class GmailCalendarUtils {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private HttpTransport httpTransport = new NetHttpTransport();

    private static Calendar calendarClient;

    //获取日历事件list
    public static List<CalendarEvent> getCalendarEventList(String accessToken) throws IOException, GeneralSecurityException{
        List<CalendarEvent> eventList = new ArrayList<CalendarEvent>();

        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(accessToken);
            calendarClient = new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                    credential).setApplicationName(GmailConstants.APPLICATION_NAME).build();

        String pageToken = null;
        do {

            Events events = calendarClient.events().list("primary").setPageToken(pageToken).execute();
            if( events != null) {
                List<Event> items = events.getItems();
                for (Event event : items) {
                    if (event != null)
                        eventList.add(new CalendarEvent(event));
                    System.out.println(new CalendarEvent(event));
                }
                pageToken = events.getNextPageToken();
            }
        } while (pageToken != null);

        return eventList;
    }

    //添加日历事件
    public static void addCalendarEvent(String accessToken, Event event) throws IOException, GeneralSecurityException {
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(accessToken);
        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                credential).setApplicationName(GmailConstants.APPLICATION_NAME).build();

        String calendarId = "primary";
        event = calendarClient.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
        System.out.println("新添加的event：" + new CalendarEvent(event));

    }

    //删除事件event
    public static void deleteCalendarEvent(String accessToken, String eventId) throws GeneralSecurityException, IOException {
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(accessToken);
        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                credential).setApplicationName(GmailConstants.APPLICATION_NAME).build();
        calendarClient.events().delete("primary", eventId).execute();
    }

    //获取事件event
    public static CalendarEvent getCalendarEvent(String accessToken, String eventId) throws GeneralSecurityException, IOException {
        Event event = null;
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(accessToken);
        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                credential).setApplicationName(GmailConstants.APPLICATION_NAME).build();
        event = calendarClient.events().get("primary", eventId).execute();
        return new CalendarEvent(event);
    }


    //更新事件event
    public static CalendarEvent updateCalendarEvent(String accessToken, Event event) throws GeneralSecurityException, IOException {
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(accessToken);
        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                credential).setApplicationName(GmailConstants.APPLICATION_NAME).build();

        calendarClient.events().update("primary", event.getId(), event);
        return new CalendarEvent(event);
    }

    public static Event initEvent() {
        Event event = new Event()
                .setSummary("添加CalendarEvent")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription(" 尝试添加新的event ");

        DateTime startDateTime = new DateTime("2015-09-28T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2015-09-28T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("lpage@example.com"),
                new EventAttendee().setEmail("sbrin@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        return event;
    }
    public static void main(String[] args) {
        String token = OAuth2Util.getAccessToken("ya29.-QEiNd4xlkeqvXLr734HxsplFys7XtOe1s7na2KzZp4Wp-TPW1W6-J2pwxrGUlo0lL2DHwss",
                "1/XcXPqJ7Qpcm0hhGbacmaR5Eyt3j2Z2i7SNNvg2QMhB8");
        System.out.println(token);
////        getCalendar(token);
        try {
            getCalendarEventList(token);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
//        getCalendarEventList(token);

    }

}
