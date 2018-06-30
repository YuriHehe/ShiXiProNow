package com.pro.ssm.model.custom;

import com.pro.ssm.model.custom.extra.Classes;

import java.math.BigDecimal;
import java.util.List;

public class CourseDetailInfo {


    private int cid;// 课程id
    private String name;// 课程名称
    private String department; // 开课学院
    private BigDecimal credit; // 学分
    private BigDecimal hour;//学时
    private List<Classes> classes;

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

    public List<Classes> getClasses() {
        return classes;
    }

    public void setClasses(List<Classes> classes) {
        this.classes = classes;
    }
}
