package com.vs.vipsai.messages.models;

import com.vipsai.imui.commons.models.IUser;

/**
 * Author: cynid
 * Created on 4/10/18 5:10 PM
 * Description:
 */

public class DefaultUser implements IUser {

    private String id;
    private String displayName;
    private String avatar;

    public DefaultUser(String id, String displayName, String avatar) {
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getAvatarFilePath() {
        return avatar;
    }

}