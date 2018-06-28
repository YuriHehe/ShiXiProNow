package com.pro.ssm.model;

public class Schedule extends ScheduleKey {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}