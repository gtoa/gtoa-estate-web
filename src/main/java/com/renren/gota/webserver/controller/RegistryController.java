package com.renren.gota.webserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renren.gota.webserver.service.RegistryService;
import com.renren.gota.webserver.service.UserService;

/**
 * 用户注册请求
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年9月22日 下午8:14:19
 */
@Controller
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "registry", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String registry(@RequestParam("account") String account, @RequestParam("name") String name,
        @RequestParam("password") String password) {
        boolean succ = registryService.registry(account, name, password);

        return "isSucc:" + succ;

    }
}
