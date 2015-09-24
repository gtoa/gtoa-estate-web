package com.renren.gota.webserver.interceptor;

import java.lang.annotation.*;

@Inherited
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}