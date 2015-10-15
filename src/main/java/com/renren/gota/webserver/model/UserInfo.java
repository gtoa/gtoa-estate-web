package com.renren.gota.webserver.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-10-14 16:20.
 */
public class UserInfo {

    private int userId;
    private String thirdId; //第三方注册ID
    private String email;
    private String userName;
    private int thirdType;

    public static enum ThirdType {
        /**
         * facebook:1
         */
        FaceBook(1),
        /**
         * Google:2
         */
        Google(2),
        /**
         * Twitter:3
         */
        Twitter(3);


        private final int value;
        private static Map<Integer, ThirdType> map;

        static {
            map = new HashMap<Integer, ThirdType>();
            for (ThirdType item : ThirdType.values()) {
                map.put(item.toIntValue(), item);
            }
        }

        private ThirdType(
        int value) {
            this.value = value;
        }

        public int toIntValue() {
            return value;
        }

        public static ThirdType fromIntValue(
                int status) {
            return map.get(status);
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getThirdType() {
        return thirdType;
    }

    public void setThirdType(int thirdType) {
        this.thirdType = thirdType;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", thirdId='" + thirdId + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", thirdType='" + thirdType + '\'' +
                '}';
    }
}
