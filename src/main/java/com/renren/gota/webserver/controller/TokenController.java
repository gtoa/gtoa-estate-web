package com.renren.gota.webserver.controller;

import com.renren.gota.webserver.interceptor.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 20:01.
 */
@Controller
@RequestMapping(value = "token")
public class TokenController {

    @LoginRequired
    @RequestMapping(value = "")
    public ModelAndView getTokenPage() {
        ModelAndView mav = new ModelAndView("tokenPage");
        return mav;
    }


}
