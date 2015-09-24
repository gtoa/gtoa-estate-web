package com.renren.gota.webserver.model;

import java.util.Date;

public class Session {

    private String id;
    private int userId;
    private Date createTime;
    private Date invalidTime;

    /**
     * 必须保留一个无参数的构造方法，否则mybatis无法使用
     */
    public Session(){
    }

    public Session(String id, int userId, Date createTime, Date invalidTime){
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        this.invalidTime = invalidTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

}
