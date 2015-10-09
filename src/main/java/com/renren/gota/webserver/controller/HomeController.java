package com.renren.gota.webserver.controller;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.api.client.util.Lists;
import com.renren.gota.webserver.model.UserToken;
import com.renren.gota.webserver.service.UserTokenService;
import com.renren.gota.webserver.util.GmailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renren.gota.webserver.common.annotation.LoginRequired;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.UserService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @LoginRequired
    public ModelAndView getIndex(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("home");
        User user = (User) request.getAttribute("user");
        if(user == null) {
            mav.addObject("userId", 0);
            return mav;
        }
        mav.addObject("userId", user.getId());
        mav.addObject("account", user.getAccount());
        StringBuilder sb = new StringBuilder();
        sb.append("id:" + user.getId() + ", account:" + user.getAccount() + "\n");
        int userId = user.getId();

        UserToken ut = userTokenService.getUserTokenById(userId);
        if(ut != null)
            mav.addObject("hasToken", 1);
        else
            mav.addObject("hasToken", 0);

        List<String> scopeList = Lists.newArrayList();
        scopeList.add(GmailConstants.CONTACT_SCOPE_READONLY);
        scopeList.add(GmailConstants.CONTACT_SCOPE_RW);
        scopeList.add(GmailConstants.CALENDAR_SCOPE_READONLY);
        scopeList.add(GmailConstants.CALENDAR_SCOPE_RW);

        String strScope = Joiner.on(" ").join(scopeList);
        String authUrl = "https://accounts.google.com/o/oauth2/auth?scope=" +
                strScope +
                "&state=1&redirect_uri=" +
                GmailConstants.REDIRECT_URL +
                "&response_type=code&client_id=" +
                GmailConstants.CONSUMER_KEY +
                "&approval_prompt=force&access_type=offline";
        mav.addObject("authUrl", authUrl);
        return mav;
    }

}
