package com.vs.vipsai.widget.comment;

import java.io.Serializable;

/**
 * Author: cynid
 * Created on 4/8/18 9:45 AM
 * Description:
 */

public class UserBean implements Serializable {

    private int userId;
    private String userName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
