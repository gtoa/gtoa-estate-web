package com.renren.gota.webserver.controller;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.api.client.util.Lists;
import com.renren.gota.webserver.common.annotation.LoginRequired;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.model.UserToken;
import com.renren.gota.webserver.service.UserTokenService;
import com.renren.gota.webserver.util.GmailConstants;
import com.renren.gota.webserver.util.OAuth2Util;
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
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 20:01.
 */
@Controller
@RequestMapping(value = "token")
public class TokenController {

    private Log logger = LogFactory.getLog(TokenController.class);

    @Autowired
    private UserTokenService userTokenService;

    @LoginRequired
    @RequestMapping(value = "")
    public ModelAndView getTokenPage(HttpServletRequest request) {
        List<String> scopeList = Lists.newArrayList();
        scopeList.add(GmailConstants.CONTACT_SCOPE_READONLY);
        scopeList.add(GmailConstants.CONTACT_SCOPE_RW);
        scopeList.add(GmailConstants.CALENDAR_SCOPE_READONLY);
        scopeList.add(GmailConstants.CALENDAR_SCOPE_RW);

        String strScope = Joiner.on(" ").join(scopeList);
        ModelAndView mav = new ModelAndView("tokenPage");
        String authUrl = "https://accounts.google.com/o/oauth2/auth?scope=" +
                strScope +
                "&state=1&redirect_uri=" +
                GmailConstants.REDIRECT_URL +
                "&response_type=code&client_id=" +
                GmailConstants.CONSUMER_KEY +
                "&approval_prompt=force&access_type=offline";
        mav.addObject("authUrl", authUrl);
        User user = (User) request.getAttribute("user");
        int userId = user.getId();
        UserToken ut = userTokenService.getUserTokenById(userId);
        if(ut != null)
            mav.addObject("hasToken", 1);
        else
            mav.addObject("hasToken", 0);
        return mav;
    }

    @LoginRequired
    @RequestMapping(value = "code")
    public String getTokenCode(HttpServletResponse response,
                               HttpServletRequest request,
                               @RequestParam("code") String code) {

        User user = (User) request.getAttribute("user");
        UserToken ut = OAuth2Util.getAccessTokenByCode(code);
        logger.info("userToken DATA: " + ut.toString());
        ut.setUserId(user.getId());
        UserToken oldUT = userTokenService.getUserTokenById(user.getId());
        if(oldUT != null)
            userTokenService.updateUserToken(ut);
        else
            userTokenService.addUserToken(ut);

        try {
            response.sendRedirect("/token");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    public static void main(String[] args) {
        List<String> scopeList = Lists.newArrayList();
        scopeList.add(GmailConstants.CONTACT_SCOPE_READONLY);
        scopeList.add(GmailConstants.CONTACT_SCOPE_RW);
        scopeList.add(GmailConstants.CALENDAR_SCOPE_READONLY);
        scopeList.add(GmailConstants.CALENDAR_SCOPE_RW);

        String strScope = Joiner.on(" ").join(scopeList);
        System.out.println(strScope);
    }


}
