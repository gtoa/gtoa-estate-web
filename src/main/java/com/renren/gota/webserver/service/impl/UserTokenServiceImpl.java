package com.renren.gota.webserver.service.impl;

import com.renren.gota.webserver.dao.UserTokenDAO;
import com.renren.gota.webserver.model.UserToken;
import com.renren.gota.webserver.service.UserTokenService;
import com.renren.gota.webserver.util.GmailContactsUtils;
import com.renren.gota.webserver.util.OAuth2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 21:02.
 */
@Service
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

//    @Override
//    public UserToken getUserTokenById(int userId) {
//        return userTokenDAO.getUserTokenById(userId);
//    }

    @Override
    public UserToken getUserTokenById(int userId) {
        UserToken ut = userTokenDAO.getUserTokenById(userId);
        if(ut == null)
            return null;
        String token = ut.getAccessToken();
        if(OAuth2Util.isTokenExpired(token)) {
            token = OAuth2Util.getAccessToken(ut.getAccessToken(), ut.getRefreshToken());
            ut.setAccessToken(token);
            updateUserToken(ut);
        }
        return ut;
    }
}
