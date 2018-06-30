package com.pro.ssm.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class GradeInfo extends GradeBase{
    private int clsid; // 教学班id
    private int cid; // 课程id
    private String course; // 课程名称
    private BigDecimal credit; // 学分
    private String teacher; // 教师姓名
    private BigDecimal grade; // 最终成绩
    private Date grade_time; // 成绩录入时间

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public int getClsid() {
        return clsid;
    }

    public void setClsid(int clsid) {
        this.clsid = clsid;
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



    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @JsonFormat(pattern="MM-dd HH:mm",timezone = "GMT+8")
    public Date getGrade_time() {
        return grade_time;
    }

    public void setGrade_time(Date grade_time) {
        this.grade_time = grade_time;
    }


}
