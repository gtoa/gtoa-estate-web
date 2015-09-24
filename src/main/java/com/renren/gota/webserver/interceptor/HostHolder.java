package com.renren.gota.webserver.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 21:08.
 */
@Component
public class HostHolder {
    // md5 'waltz user id'
    private static final String USER_ID_PARAM_KEY = "42EE37A0976578325BB0F3265C9CE3DC";
    private static final Log LOGGER = LogFactory.getLog(HostHolder.class);

    @Autowired
    private HttpServletRequest request;

    public int getUserId() {
        int userId = -1;
        try {
            userId = (Integer) request.getAttribute(USER_ID_PARAM_KEY);
        } catch (Exception e) {
            userId = -1;
            LOGGER.error("get userid failed:",e);
        }
        return userId;
    }

    public void setUserId(int userId) {
        request.setAttribute(USER_ID_PARAM_KEY, userId);
    }
}