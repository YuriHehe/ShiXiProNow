package com.pro.ssm.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class GradeInfo {
    private int clsid; // 教学班id
    private int cid; // 课程id
    private String course; // 课程名称
    private BigDecimal credit; // 学分
    private String teacher; // 教师姓名
    private BigDecimal usual_grade; // 平时成绩(可能为空,即未录入)
    private BigDecimal final_grade; // 期末成绩(可能为空,即未录入)
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

    public BigDecimal getUsual_grade() {
        return usual_grade;
    }

    public void setUsual_grade(BigDecimal usual_grade) {
        this.usual_grade = usual_grade;
    }

    public BigDecimal getFinal_grade() {
        return final_grade;
    }

    public void setFinal_grade(BigDecimal final_grade) {
        this.final_grade = final_grade;
    }

    public BigDecimal getGrade() {
        return usual_grade != null && final_grade != null ? (usual_grade.add(final_grade)).divide(new BigDecimal(2)) : null;
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
