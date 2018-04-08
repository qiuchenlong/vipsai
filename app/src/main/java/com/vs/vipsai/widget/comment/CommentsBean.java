package com.vs.vipsai.widget.comment;

import java.io.Serializable;

/**
 * Author: cynid
 * Created on 4/8/18 9:45 AM
 * Description:
 */

public class CommentsBean implements Serializable {

    private int commentsId;
    private String content;
    private UserBean replyUser; // 回复人信息
    private UserBean commentsUser;  // 评论人信息

    public int getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(int commentsId) {
        this.commentsId = commentsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(UserBean replyUser) {
        this.replyUser = replyUser;
    }

    public UserBean getCommentsUser() {
        return commentsUser;
    }

    public void setCommentsUser(UserBean commentsUser) {
        this.commentsUser = commentsUser;
    }

}
