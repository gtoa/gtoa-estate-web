package com.renren.gota.webserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renren.gota.webserver.common.annotation.LoginRequired;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @LoginRequired
    public String getIndex(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        StringBuilder sb = new StringBuilder();
        sb.append("id:" + user.getId() + ", account:" + user.getAccount() + "\n");
        return sb.toString();
    }

}
