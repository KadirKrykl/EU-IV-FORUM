package com.example.eu_iv_forum.Forum;

import java.util.Date;

public class ForumReply {
    public String reply,user_id;
    public Date time_stamp;

    public ForumReply(){}

    public ForumReply(String reply, String user_id, Date time_stamp) {
        this.reply = reply;
        this.user_id = user_id;
        this.time_stamp = time_stamp;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }


    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
