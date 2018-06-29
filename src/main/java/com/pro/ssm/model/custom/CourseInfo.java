package com.pro.ssm.model.custom;

import java.math.BigDecimal;

public class CourseInfo {
    private int cid; // 课程id
    private String name; // 课程名称
    private String teacher; // 教师姓名
    private String department; // 开课学院
    private BigDecimal credit; // 学分
    private BigDecimal hour;//学时

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getHour() {
        return hour;
    }

    public void setHour(BigDecimal hour) {
        this.hour = hour;
    }
}
