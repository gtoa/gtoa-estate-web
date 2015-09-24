package com.renren.gota.webserver.service;

public interface CheckLoginService {

    boolean checkLogin(String uidStr, String sessionId);

}
