package com.pro.ssm.model;

import java.math.BigDecimal;
import java.util.Date;

public class StuCls extends StuClsKey {
    private BigDecimal finalGrade;

    private BigDecimal usualGrade;

    private Date createTime;

    public BigDecimal getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(BigDecimal finalGrade) {
        this.finalGrade = finalGrade;
    }

    public BigDecimal getUsualGrade() {
        return usualGrade;
    }

    public void setUsualGrade(BigDecimal usualGrade) {
        this.usualGrade = usualGrade;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}