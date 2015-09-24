package com.renren.gota.webserver.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.gota.webserver.constant.Constants;
import com.renren.gota.webserver.dao.SessionDAO;
import com.renren.gota.webserver.dao.UserDAO;
import com.renren.gota.webserver.model.Session;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.LoginService;
import com.renren.gota.webserver.utils.CookieUtils;
import com.renren.gota.webserver.utils.SessionUtil;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionDAO sessionDAO;

    private static final long THIRTY_DAYS = 30 * 24 * 60 * 60 * 1000l;

    @Override
    public boolean loginByAccount(String account, String password, HttpServletResponse response) {

        User user = userDAO.getUserByAccount(account);
        if (null == user) {
            return false;
        }

        String realPassword = user.getPassword();
        if (!realPassword.equals(password)) {
            return false;
        }

        return login(user, response);
    }

    @Override
    public boolean loginByName(String name, String password, HttpServletResponse response) {
        User user = userDAO.getUserByName(name);
        if (null == user) {
            return false;
        }

        String realPassword = user.getPassword();
        if (!realPassword.equals(password)) {
            return false;
        }

        return login(user, response);
    }

    private boolean login(User user, HttpServletResponse response) {

        if (null == user) {
            return false;
        }
        // 1.清楚已经存在的session
        sessionDAO.deleteByUserId(user.getId());

        // 2.随机生成session
        String sessionId = SessionUtil.generateSessionId();

        // 3.存储session
        long now = System.currentTimeMillis();
        long invalidTime = now + THIRTY_DAYS;
        Session session = new Session(sessionId, user.getId(), new Date(now), new Date(invalidTime));
        sessionDAO.insert(session);

        // 4.写cookie
        CookieUtils.saveCookie(response, Constants.CookieContsants.SESSION_ID, sessionId, (int) THIRTY_DAYS / 1000);
        CookieUtils.saveCookie(response, Constants.CookieContsants.USER_ID, String.valueOf(user.getId()),
            (int) THIRTY_DAYS / 1000);

        return true;
    }

}
