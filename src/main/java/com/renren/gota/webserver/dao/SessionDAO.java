package com.renren.gota.webserver.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.renren.gota.webserver.dao.annotation.DataSourceBase;
import com.renren.gota.webserver.model.Session;

@DataSourceBase
public interface SessionDAO {

    String TABLE_NAME = "session";
    String FIELDS = "id, user_id, create_time, invalid_time";

    @Select("select " + FIELDS + " from " + TABLE_NAME + " where user_id=#{userId}")
    public Session selectByUserId(int userId);

    @Select("select " + FIELDS + " from " + TABLE_NAME + " where id=#{sessionId}")
    public Session selectBySessionId(String sessionId);

    @Insert("insert into " + TABLE_NAME
            + " set id=#{id}, user_id=#{userId}, create_time=#{createTime}, invalid_time=#{invalidTime}")
    public void insert(Session session);

    @Delete("delete from " + TABLE_NAME + " where user_id=#{userId}")
    public void deleteByUserId(int userId);

    @Delete("delete from " + TABLE_NAME + " where id=#{sessionId}")
    public void deleteBySessionId(String sessionId);
}
