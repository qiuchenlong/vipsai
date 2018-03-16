package com.vs.vipsai.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Author: cynid
 * Created on 3/13/18 6:19 PM
 * Description:
 *
 * 实体基类：实现序列化
 */

@SuppressWarnings("serial")
public abstract class Base implements Serializable {

    @XStreamAlias("notice")
    protected Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
