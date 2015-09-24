package com.renren.gota.webserver.service;

import com.renren.gota.webserver.model.UserToken;

import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 21:00.
 */
public interface UserTokenService {

    List<UserToken> getAll();

    void updateUserToken(UserToken userToken);

    void addUserToken(UserToken userToken);

    UserToken getUserTokenById(int userId);
}
