package com.renren.gota.webserver.controller;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.renren.gota.webserver.common.annotation.LoginRequired;
import com.renren.gota.webserver.model.CalendarEvent;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.model.UserToken;
import com.renren.gota.webserver.service.UserTokenService;
import com.renren.gota.webserver.util.GmailCalendarUtils;
import com.renren.gota.webserver.util.ServiceResultUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-24 22:20.
 */
@Controller
@RequestMapping(value = "calendar")
public class GoogleCalendarController {

    private Log logger = LogFactory.getLog(GoogleCalendarController.class);

    @Autowired
    private UserTokenService userTokenService;


    @LoginRequired
    @RequestMapping(value = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView getCalendarList(HttpServletRequest request,
                                      HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("calendar");
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        if(ut == null) {
            logger.error("没有token数据， 请授权");
            return null;
        }
        try {
            List<CalendarEvent> eventList = GmailCalendarUtils.getCalendarEventList(ut.getAccessToken());
            mav.addObject("eventList", eventList);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            logger.error(e.getMessage(), e);
        }
        return mav;

    }

    @LoginRequired
    @RequestMapping(value = "detail")
    public ModelAndView getEventDetail(HttpServletResponse response,
                                     HttpServletRequest request,
                                 @RequestParam("eventId") String eventId) {
        ModelAndView mav = new ModelAndView("calendarDetail");
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        if(ut == null) {
            logger.error("没有token数据， 请授权");
        }
        try {
            CalendarEvent event = GmailCalendarUtils.getCalendarEvent(ut.getAccessToken(), eventId);
            mav.addObject("event", event);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            logger.error(e.getMessage(), e);
        }
        return mav;
    }

    @LoginRequired
    @RequestMapping(value = "delete")
    public String delEvent(HttpServletResponse response,
                                     HttpServletRequest request,
                           @RequestParam("eventId") String eventId) {
        JSONObject result = new JSONObject();
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        if(ut == null) {
            logger.error("没有token数据， 请授权");
            return null;
        }
        try {
            GmailCalendarUtils.deleteCalendarEvent(ut.getAccessToken(), eventId);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            response.sendRedirect("/calendar");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private  Event getCalendarEvent(String summary, String desc, String location,
                                    Date startTime, Date endTime) {
        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(desc);

        DateTime startDateTime = new DateTime(startTime.getTime())
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime(endTime.getTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

//        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
//        event.setRecurrence(Arrays.asList(recurrence));

//        EventAttendee[] attendees = new EventAttendee[] {
//                new EventAttendee().setEmail("lpage@example.com"),
//                new EventAttendee().setEmail("sbrin@example.com"),
//        };
//        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(20),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        return event;
    }

    @LoginRequired
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView getAddEventPage() {
        ModelAndView mav = new ModelAndView("addCalendarEvent");
        return mav;
    }

    @LoginRequired
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addEvent(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("summary") String summary,
                           @RequestParam("desc") String desc,
                           @RequestParam("location") String location,
                           @RequestParam("startTime")Date startTime,
                           @RequestParam("endTime") Date endTime){

        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        if(ut == null) {
            logger.error("没有token数据， 请授权");
            return null;
        }
        Event event = getCalendarEvent(summary, desc, location, startTime, endTime);
        try {
            GmailCalendarUtils.addCalendarEvent(ut.getAccessToken(), event);
            response.sendRedirect("/calendar");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
