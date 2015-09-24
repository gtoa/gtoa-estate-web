package com.renren.gota.webserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.gota.webserver.dao.SessionDAO;
import com.renren.gota.webserver.model.Session;
import com.renren.gota.webserver.service.CheckLoginService;

@Service
public class CheckLoginServiceImpl implements CheckLoginService {

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public boolean checkLogin(String uidStr, String sessionId) {
        Session session = sessionDAO.selectBySessionId(sessionId);
        if (null == session) {
            return false;
        }

        long now = System.currentTimeMillis();
        long invalidTime = session.getInvalidTime().getTime();
        if (now >= invalidTime) {
            sessionDAO.deleteBySessionId(sessionId);
            return false;
        }

        String realUidStr = String.valueOf(session.getUserId());
        if (!realUidStr.equals(uidStr)) {
            return false;
        }

        return true;
    }

}
