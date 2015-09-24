package com.renren.gota.webserver.controller;

import java.util.List;

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

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @LoginRequired
    public String getIndex() {
         
        List<User> userList = userService.selectAllUser();
        StringBuilder sb = new StringBuilder();
        for (User user : userList) {
            sb.append("id:" + user.getId() + ", account:" + user.getAccount() + ", name:" + user.getName() + "\n");
        }
        return sb.toString();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String test() {
        return "success";
    }
}
