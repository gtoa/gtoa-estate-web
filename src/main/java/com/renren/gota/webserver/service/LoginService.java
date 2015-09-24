package com.renren.gota.webserver.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录服务
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年9月23日 下午3:28:58
 */
public interface LoginService {

    boolean loginByAccount(String account, String password, HttpServletResponse response);

    boolean loginByName(String name, String password, HttpServletResponse response);

}
