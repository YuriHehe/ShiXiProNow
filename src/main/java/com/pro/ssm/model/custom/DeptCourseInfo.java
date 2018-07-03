package com.pro.ssm.model.custom;

import com.pro.ssm.model.Departmet;

public class DeptCourseInfo extends Departmet {
    private int course_num;
    private int capacity;
    private int chose_num;

    public int getCourse_num() {
        return course_num;
    }

    public void setCourse_num(int course_num) {
        this.course_num = course_num;
    }

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
}
