package com.renren.gota.webserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.renren.gota.webserver.dao.annotation.DataSourceBase;
import com.renren.gota.webserver.model.User;

@DataSourceBase
public interface UserDAO {

    String TABLE_NAME = "user";
    String FIELDS = "id, name, account, password";

    @Select("select " + FIELDS + " from " + TABLE_NAME)
    public List<User> selectAll();

    @Select("select " + FIELDS + " from " + TABLE_NAME + " where id=#{id}")
    public User getUserById(int id);

    @Select("select " + FIELDS + " from " + TABLE_NAME + " where account=#{account}")
    public User getUserByAccount(String account);

    @Select("select " + FIELDS + " from " + TABLE_NAME + " where name=#{name}")
    public User getUserByName(String name);

    @Insert("insert into " + TABLE_NAME + " set name=#{name}, account=#{account}, password=#{password}")
    public void insert(User user);
}
