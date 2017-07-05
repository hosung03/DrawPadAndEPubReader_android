package com.hosung.drawpadandepubreader.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Hosung, Lee on 2017. 7. 4..
 */

public class EPubHighLight extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String bookId;
    private String content;
    private String contentPost;
    private String contentPre;
    private String date;
    private String highlightId;
    private Integer page = 0;
    private String type;
    private Integer currentPagerPostion = 0;
    private Integer currentWebviewScrollPos = 0;
    private String note;


    public int getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getContent() {
        return content;
    }

    public String getContentPost() {
        return contentPost;
    }

    public String getContentPre() {
        return contentPre;
    }

    public String getDate() {
        return date;
    }

    public String getHighlightId() {
        return highlightId;
    }

    public Integer getPage() {
        return page;
    }

    public String getType() {
        return type;
    }

    public Integer getCurrentPagerPostion() {
        return currentPagerPostion;
    }

    public Integer getCurrentWebviewScrollPos() {
        return currentWebviewScrollPos;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public void setContentPre(String contentPre) {
        this.contentPre = contentPre;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHighlightId(String highlightId) {
        this.highlightId = highlightId;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrentPagerPostion(Integer currentPagerPostion) {
        this.currentPagerPostion = currentPagerPostion;
    }

    public void setCurrentWebviewScrollPos(Integer currentWebviewScrollPos) {
        this.currentWebviewScrollPos = currentWebviewScrollPos;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
