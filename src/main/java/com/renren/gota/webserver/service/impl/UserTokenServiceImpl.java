package com.renren.gota.webserver.service.impl;

import com.renren.gota.webserver.dao.UserTokenDAO;
import com.renren.gota.webserver.model.UserToken;
import com.renren.gota.webserver.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 21:02.
 */
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenDAO userTokenDAO;

    @Override
    public List<UserToken> getAll() {
        return userTokenDAO.selectAll();
    }

    @Override
    public void updateUserToken(UserToken userToken) {
        userTokenDAO.updateUserToken(userToken);
    }

    @Override
    public void addUserToken(UserToken userToken) {
        userTokenDAO.addUserToken(userToken);
    }

    @Override
    public UserToken getUserTokenById(int userId) {
        return userTokenDAO.getUserTokenById(userId);
    }
}