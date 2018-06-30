package com.pro.ssm.model.custom;

import java.math.BigDecimal;

public class GradeBase {
    private String stuid; // 学生id
    private String name; // 学生姓名
    private BigDecimal usual_grade; // 平时成绩(可能为空,即未录入)
    private BigDecimal final_grade;// 期末成绩(可能为空,即未录入)

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
