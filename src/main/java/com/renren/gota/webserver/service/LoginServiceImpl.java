package com.renren.gota.webserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.gota.webserver.dao.UserDAO;
import com.renren.gota.webserver.model.User;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean loginByAccount(String account, String password) {

        User user = userDAO.getUserByAccount(account);
        if (null == user) {
            return false;
        }

        String realPassword = user.getPassword();
        if (!realPassword.equals(password)) {
            return false;
        }

        return login(user);
    }

    @Override
    public boolean loginByName(String name, String password) {
        User user = userDAO.getUserByName(name);
        if (null == user) {
            return false;
        }

        String realPassword = user.getPassword();
        if (!realPassword.equals(password)) {
            return false;
        }

        return login(user);
    }

    private boolean login(User user) {
        return true;
    }

}
