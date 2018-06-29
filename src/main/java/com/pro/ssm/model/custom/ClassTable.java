package com.pro.ssm.model.custom;

public class ClassTable {
    private int cid;
    private String course; // 课程名称
    private String teacher;// 教师姓名
    private int day;
    private int start_session; // 开始节数
    private int end_session; // 结束节数
    private String address; //上课地点

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getStart_session() {
        return start_session;
    }

    public void setStart_session(int start_session) {
        this.start_session = start_session;
    }

    public int getEnd_session() {
        return end_session;
    }

    public void setEnd_session(int end_session) {
        this.end_session = end_session;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
