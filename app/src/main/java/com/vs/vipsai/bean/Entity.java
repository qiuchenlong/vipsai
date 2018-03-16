package com.vs.vipsai.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Author: cynid
 * Created on 3/13/18 6:19 PM
 * Description:
 *
 * 实体类
 */

@SuppressWarnings("serial")
public abstract class Entity extends Base {

    @XStreamAlias("id")
    protected int id;

    protected String cacheKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

}
