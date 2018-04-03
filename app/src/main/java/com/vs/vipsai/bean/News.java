package com.vs.vipsai.bean;

/**
 * Author: cynid
 * Created on 3/13/18 3:29 PM
 * Description:
 */

public class News extends PrimaryBean {

    public static final int TYPE_HREF = 0;
    public static final int TYPE_SOFTWARE = 1;
    public static final int TYPE_QUESTION = 2;
    public static final int TYPE_ATTENTION = 3; // 关注
    public static final int TYPE_TRANSLATE = 4;
    public static final int TYPE_EVENT = 5;
    public static final int TYPE_NEWS = 6;
    public static final int TYPE_FIND_PERSON = 11;


    public static final int TYPE_POPULAR = 20; // 热门

    // competition tab
    public static final int TYPE_OPEN = 31;
    public static final int TYPE_QUALIFYING = 32;

    // vote tab
    public static final int TYPE_KNOCKOUT = 52;


    // past tab
    public static final int TYPE_PAST_WONDERFUL = 41;
    public static final int TYPE_CHAMPIONWORK = 42;


    // me tab
    public static final int TYPE_NEWEST = 21;
    public static final int TYPE_WINNER = 22;
    public static final int TYPE_GAME = 23;
    public static final int TYPE_ALL = 24;

    // detail
    public static final int TYPE_MESSAGE = 31; //消息
    public static final int TYPE_PRIVATE_MESSAGE = 32; //私信



    // account balance
    public static final int TYPE_ACCOUNT_BALANCE = 91;
    public static final int TYPE_VASH_COIN = 92;



    //样板比赛创建
    public static final int TYPE_PUBLISH_SUBJECT = 33; //选择主题

    protected int commentCount;
    protected int type;
    protected boolean recommend;
    protected String title;
    protected String body;
    protected String author;
    protected String href;
    protected String pubDate;
    protected int viewCount;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

}
