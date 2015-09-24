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
import com.renren.gota.webserver.service.RegistryService;
import com.renren.gota.webserver.utils.CookieUtils;
import com.renren.gota.webserver.utils.SessionUtil;

@Service
public class RegistryServiceImpl implements RegistryService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionDAO sessionDAO;

    private static final long THIRTY_DAYS = 30 * 24 * 60 * 60 * 1000l;

    @Override
    public boolean registry(String account, String password, HttpServletResponse response) {

        if (null == account || "".equals(account)) {
            return false;
        }

        if (null == password || "".equals(password)) {
            return false;
        }

        User user = null;
        if (null != userDAO.getUserByAccount(account)) {
            return false;
        }

        user = new User(account, password);

        userDAO.insert(user);

        // 给用户生成session 并写出cookie
        // 1.随机生成session
        String sessionId = SessionUtil.generateSessionId();
        // 2.存储session
        long now = System.currentTimeMillis();
        long invalidTime = now + THIRTY_DAYS;
        Session session = new Session(sessionId, user.getId(), new Date(now), new Date(invalidTime));
        sessionDAO.insert(session);

        // 3.写cookie
        CookieUtils.saveCookie(response, Constants.CookieContsants.SESSION_ID, sessionId, (int) THIRTY_DAYS / 1000);
        CookieUtils.saveCookie(response, Constants.CookieContsants.USER_ID, String.valueOf(user.getId()),
            (int) THIRTY_DAYS / 1000);

        return true;
    }

}
