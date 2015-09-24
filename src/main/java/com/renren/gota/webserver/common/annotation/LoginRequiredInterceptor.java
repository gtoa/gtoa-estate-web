package com.renren.gota.webserver.common.annotation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.renren.gota.webserver.constant.Constants;
import com.renren.gota.webserver.dao.UserDAO;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.CheckLoginService;
import com.renren.gota.webserver.utils.CookieUtils;
import com.renren.gota.webserver.utils.HandlerMethodUtils;

public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private CheckLoginService checkLoginService;

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handleMethod = HandlerMethodUtils.getHandleMethod(handler);

        // method loginRequired annotation first
        LoginRequired loginRequired = handleMethod.getMethodAnnotation(LoginRequired.class);
        if (loginRequired == null) {
            loginRequired = handleMethod.getBeanType().getAnnotation(LoginRequired.class);
        }

        if (loginRequired == null) {
            return true;
        } else {
            return checkWebTicket(request, response);
        }
    }

    private boolean checkWebTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        String userIdString = CookieUtils.getCookie(request, Constants.CookieContsants.USER_ID);
        String sessionId = CookieUtils.getCookie(request, Constants.CookieContsants.SESSION_ID);

        boolean checkLoginResult = checkLoginService.checkLogin(userIdString, sessionId);

        if (checkLoginResult) {
            int id = Integer.parseInt(userIdString);
            User user = userDAO.getUserById(id);
            request.setAttribute("user", user);
            return true;
        } else {

            // clear userId
            CookieUtils.saveCookie(response, Constants.CookieContsants.USER_ID, "");
            CookieUtils.clearCookie(response, Constants.CookieContsants.USER_ID);

            // clear session id
            CookieUtils.saveCookie(response, Constants.CookieContsants.SESSION_ID, "");
            CookieUtils.clearCookie(response, Constants.CookieContsants.SESSION_ID);

            response.sendRedirect(Constants.WebURLConstants.LOGIN_URL_WEB);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
        throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
        throws Exception {

    }

}
