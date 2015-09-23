package com.renren.gota.webserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.gota.webserver.dao.UserDAO;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.RegistryService;

@Service
public class RegistryServiceImpl implements RegistryService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean registry(String account, String name, String password) {

        if (null == account || "".equals(account)) {
            return false;
        }

        if (null == name || "".equals(name)) {
            return false;
        }

        if (null == password || "".equals(password)) {
            return false;
        }

        User user = null;
        if (null != userDAO.getUserByAccount(account)) {
            return false;
        }

        if (null != userDAO.getUserByName(name)) {
            return false;
        }

        user = new User(account, name, password);

        userDAO.insert(user);

        return true;
    }

}
