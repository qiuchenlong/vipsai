package com.vs.vipsai.bean;

/**
 * Author: cynid
 * Created on 3/22/18 2:39 PM
 * Description:
 */

public class TweetLike {

    private String pubDate;
    private Author author;
    private boolean liked;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

}
