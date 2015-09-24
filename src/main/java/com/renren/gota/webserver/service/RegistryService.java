package com.renren.gota.webserver.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 注册接口
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年9月23日 下午3:22:52
 */
public interface RegistryService {

    boolean registry(String account, String password, HttpServletResponse response);

}
