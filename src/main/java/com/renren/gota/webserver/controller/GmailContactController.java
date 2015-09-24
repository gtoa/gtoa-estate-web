package com.renren.gota.webserver.controller;

import com.renren.gota.webserver.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 19:59.
 */
@Controller
public class GmailContactController {


    @RequestMapping(value = "contact", method = RequestMethod.GET)
    @ResponseBody
    public String getIndex() {
        return "contacts";
    }


}
