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

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginIndex() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletResponse response, @RequestParam("account") String account,
        @RequestParam("password") String password) {

        boolean succ = false;

        if (null != account && !"".equals(account)) {
            succ = loginService.loginByAccount(account, password, response);
        }

        if (succ) {
            return "login success";
        }

        return "login error";
    }
}
