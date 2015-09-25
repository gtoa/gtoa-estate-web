package com.renren.gota.webserver.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
    @RequestMapping(value = "detail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getEventDetail(HttpServletResponse response,
                                     HttpServletRequest request,
                                 @RequestParam("eventId") String eventId) {
        JSONObject result = new JSONObject();
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        if(ut == null) {
            ServiceResultUtil.addResultCodeAndMsg(result, 1, "没有token数据， 请授权");
            return result.toString();
        }
        try {
            CalendarEvent event = GmailCalendarUtils.getCalendarEvent(ut.getAccessToken(), eventId);
            ServiceResultUtil.addResultCodeAndMsg(result, 0, "success");
            result.put("event", event);
        } catch (IOException e) {
            ServiceResultUtil.addResultCodeAndMsg(result, 2, e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            ServiceResultUtil.addResultCodeAndMsg(result, 3, e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result.toString();
    }

    @LoginRequired
    @RequestMapping(value = "delete", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delEvent(HttpServletResponse response,
                                     HttpServletRequest request,
                           @RequestParam("eventId") String eventId) {
        JSONObject result = new JSONObject();
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        if(ut == null) {
            ServiceResultUtil.addResultCodeAndMsg(result, 1, "没有token数据， 请授权");
            return result.toString();
        }
        try {
            GmailCalendarUtils.deleteCalendarEvent(ut.getAccessToken(), eventId);
            ServiceResultUtil.addResultCodeAndMsg(result, 0, "success");
        } catch (IOException e) {
            ServiceResultUtil.addResultCodeAndMsg(result, 2, e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            ServiceResultUtil.addResultCodeAndMsg(result, 3, e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result.toString();
    }



}
