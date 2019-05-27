package com.ex.memoapp.vo;

import java.io.Serializable;

public class MemoVO implements Serializable {
    private long id;
    private String title;
    private String content;
    private String date;

    public MemoVO(){
        id = 0;
    }

    public MemoVO(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public MemoVO(long id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MemoVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
