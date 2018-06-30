package com.pro.ssm.model.custom.extra;

import java.util.List;

public class Classes {
    private int class_id;// 教学班id
    private String term;// 学期
    private int capacity; // 可选人数
    private int chose_num; // 实选人数
    private String teacher; // 任课教师(可能为空,为空时,教师可以选择任教此教学班)
    private List<Schedule> schedule;

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}