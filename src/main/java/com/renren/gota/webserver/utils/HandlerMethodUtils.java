package com.renren.gota.webserver.utils;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;

public final class HandlerMethodUtils {

    /**
     * 拦截器中使用,获得handler <br>
     * 2015年5月8日:下午3:22:38<br>
     * <br>
     * 
     * @param handler
     * @return
     */
    public static final HandlerMethod getHandleMethod(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod;
        }
        return null;
    }

    /**
     * 获得处理请求的控制器 <br>
     * 2015年5月8日:下午3:29:59<br>
     * <br>
     * 
     * @param handlerMethod
     * @return
     */
    public static final Method getHanderMethodExecutor(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        return method;
    }
}
