package com.renren.gota.webserver.dao;

import com.renren.gota.webserver.dao.annotation.DataSourceBase;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.model.UserToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 20:23.
 */
@DataSourceBase
public interface UserTokenDAO {
    String TABLE_NAME = " user_token ";
    String FIELDS = " user_id, code, access_token, refresh_token, create_time, update_time ";

    @Select("select " + FIELDS + " from " + TABLE_NAME)
    public List<UserToken> selectAll();

    @Insert(""
            + " insert into "
            + TABLE_NAME
            + " set "
            + " user_id = #{userId}, "
            + " code = #{code}, "
            + " access_token = #{accessToken}, "
            + " refresh_token = #{refreshToken}, "
            + " create_time = now(), "
            + " update_time = now() "
            + "")
    public void addUserToken(UserToken userToken);


    @Update(""
            + " update "
            + TABLE_NAME
            + " set "
            + " code = #{code}, "
            + " access_token = #{accessToken}, "
            + " refresh_token = #{refreshToken}, "
            + " update_time = now(), "
            + " where "
            + " user_id = #{userId} "
            + "")
    public void updateUserToken(UserToken userToken);

    @Select(""
            + " select "
            + FIELDS
            + " from "
            + TABLE_NAME
            + " where "
            + " user_id = #{userId} "
        )
    public UserToken getUserTokenById(int userId);
}
