package com.renren.gota.webserver.service;

import java.util.List;

import com.renren.gota.webserver.model.User;

/**
 * 用户服务接口
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年9月23日 下午3:23:09
 */
public interface UserService {

    List<User> selectAllUser();

    User selectById(int id);

    User selectByAccount(String account);
}
