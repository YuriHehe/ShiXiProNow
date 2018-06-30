package com.pro.ssm.model.custom.extra;

public class Schedule {
    private int start_week;
    private int end_week;
    private int day;
    private int start_session;
    private int end_session;
    private String address;


    public void setStart_week(int start_week) {
        this.start_week = start_week;
    }

    public void setEnd_week(int end_week) {
        this.end_week = end_week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeek_range() {
        return start_week + "-" + end_week;
    }

    public String getTime_range() {
        return start_session + "-" + end_session;
    }


    public void setStart_session(int start_session) {
        this.start_session = start_session;
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
