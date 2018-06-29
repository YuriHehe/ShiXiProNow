package com.pro.ssm.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MessageInfo {
    private int id; // 留言id
    private String stu_name;
    private String content; // 留言内容
    private String reply; //管理员回复(可能为空)
    private Date reply_time; //回复时间(可能为空)
    private Date create_time; //留言时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @JsonFormat(pattern="MM-dd HH:mm",timezone = "GMT+8")
    public Date getReply_time() {
        return reply_time;
    }

    public void setReply_time(Date reply_time) {
        this.reply_time = reply_time;
    }

    @JsonFormat(pattern="MM-dd HH:mm",timezone = "GMT+8")
    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


}
