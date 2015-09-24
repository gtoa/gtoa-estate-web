package com.renren.gota.webserver.interceptor;

import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 20:10.
 */
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handleMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handleMethod.getMethodAnnotation(LoginRequired.class);
        Integer userId = Integer.parseInt(request.getParameter("USER_ID"));
        if(userId != null) {
            User user = userService.selectById(userId);
            if(user == null)
                return false;
            else
                hostHolder.setUserId(userId);
        }
        else
            return false;
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
