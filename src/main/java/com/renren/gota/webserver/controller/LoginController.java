package com.renren.gota.webserver.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renren.gota.webserver.constant.Constants;
import com.renren.gota.webserver.service.LoginService;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginIndex() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
    public String login(HttpServletResponse response, @RequestParam("account") String account,
        @RequestParam("password") String password) {

        boolean succ = false;

        if (null != account && !"".equals(account)) {
            succ = loginService.loginByAccount(account, password, response);
        }

        if (succ) {
            try {
                response.sendRedirect("/token");
                return null;
            } catch (IOException e) {
                return e.getMessage();
//                logger.error(e.getMessage(), e);
            }
//            return "login success";
        }

        return null;
    }
}
