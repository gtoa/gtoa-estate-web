package com.renren.gota.webserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.gota.webserver.dao.UserDAO;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> selectAllUser() {
        return userDAO.selectAll();
    }

    @Override
    public User selectById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User selectByName(String name) {
        return userDAO.getUserByName(name);
    }

    @Override
    public User selectByAccount(String account) {
        return userDAO.getUserByAccount(account);
    }

}
