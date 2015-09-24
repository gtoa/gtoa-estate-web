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
@RequestMapping(value = "gmail")
public class GmailContactController {


    @RequestMapping(value = "contacts", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("contacts");
        mav.addObject("item", "aa");
        return mav;
    }

    @RequestMapping(value = "contacts")

}
