package com.renren.gota.webserver.service;

/**
 * 登录服务
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年9月23日 下午3:28:58
 */
public interface LoginService {
    
    boolean loginByAccount(String account, String password);
    
    boolean loginByName(String name, String password);
    
}
