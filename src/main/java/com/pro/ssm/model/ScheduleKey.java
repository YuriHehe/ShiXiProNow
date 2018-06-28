package com.pro.ssm.model;

public class ScheduleKey {
    private Integer clsid;

    private Integer startWeek;

    private Integer endWeek;

    private Integer day;

    private Integer startSession;

    private Integer endSession;

    public Integer getClsid() {
        return clsid;
    }

    public void setClsid(Integer clsid) {
        this.clsid = clsid;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartSession() {
        return startSession;
    }

    public void setStartSession(Integer startSession) {
        this.startSession = startSession;
    }

    public Integer getEndSession() {
        return endSession;
    }

    public void setEndSession(Integer endSession) {
        this.endSession = endSession;
    }
}