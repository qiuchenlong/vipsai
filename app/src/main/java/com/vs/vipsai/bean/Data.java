package com.vs.vipsai.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: cynid
 * Created on 4/8/18 3:07 PM
 * Description:
 */

public class Data implements Serializable {

    private List<popularData> data;

    public List<popularData> getData() {
        return data;
    }

    public void setData(List<popularData> data) {
        this.data = data;
    }
}
