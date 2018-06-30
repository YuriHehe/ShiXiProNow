package com.pro.ssm.model.custom;

public class ClsInfo extends CourseInfo {
    private int clsid;// 教学班id
    private String term; // 学期
    private int capacity;
    private int chose_num;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getChose_num() {
        return chose_num;
    }

    public void setChose_num(int chose_num) {
        this.chose_num = chose_num;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    public int getClsid() {
        return clsid;
    }

    public int getClass_id() {
        return clsid;
    }

    public void setClsid(int clsid) {
        this.clsid = clsid;
    }
}
