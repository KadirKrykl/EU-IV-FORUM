package com.example.eu_iv_forum.Forum;

import java.util.Date;

public class ForumTopic {
    String title, user_id,content;
    public Date time_stamp;

    public ForumTopic(){}

    public ForumTopic(String title, String user_id, String content, Date time_stamp) {
        this.title = title;
        this.user_id = user_id;
        this.content = content;
        this.time_stamp = time_stamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return time_stamp;
    }

    public void setTimestamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }
}
