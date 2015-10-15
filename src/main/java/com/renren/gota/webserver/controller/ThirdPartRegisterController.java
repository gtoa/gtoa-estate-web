package com.renren.gota.webserver.controller;

import com.renren.gota.webserver.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-10-14 14:07.
 */
@Controller
@RequestMapping(value = "thirdreg")
public class ThirdPartRegisterController {

    //回调方法
    @RequestMapping(value = "")
    public ModelAndView getThirdUserInfo(HttpServletRequest request, @RequestParam("thirdId") String thirdId,
                                   @RequestParam("thirdName") String thirdName,
                                   @RequestParam("thirdEmail") String thirdEmail,
                                    @RequestParam("thirdType") int thirdType) {
        ModelAndView mav = new ModelAndView("userInfo");
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(thirdEmail);
        userInfo.setThirdId(thirdId);
        userInfo.setThirdType(thirdType);
        userInfo.setUserName(thirdName);
        mav.addObject("user", userInfo);
        return mav;
    }

}
