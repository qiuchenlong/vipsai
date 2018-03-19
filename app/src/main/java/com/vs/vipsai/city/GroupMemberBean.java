package com.vs.vipsai.city;

/**
 * Author: cynid
 * Created on 3/19/18 11:05 AM
 * Description:
 */

public class GroupMemberBean {

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母
    private String imageurl;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
