package com.renren.gota.webserver.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = Constants.RETURN_JSON_FORMAT)
    @ResponseBody
    public String login(@RequestParam("account") String account, @RequestParam("name") String name,
        @RequestParam("password") String password) {

        boolean succ = false;

        if (null != account && !"".equals(account)) {
            succ = loginService.loginByAccount(account, password);
        }

        if (!succ && null != name && !"".equals(name)) {
            succ = loginService.loginByName(name, password);
        }

        return "login:" + succ;
    }
}
