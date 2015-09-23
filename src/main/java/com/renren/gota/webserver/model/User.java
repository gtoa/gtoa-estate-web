package com.renren.gota.webserver.model;

public class User {

    private String name;
    private String id;
    private String account;
    private String password; // 简单为见，直接明文存储

    /**
     * 必须保留一个无参构造方法，否则框架无法将其实例化
     */
    public User(){
    }

    public User(String account, String name, String password){
        this.account = account;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
