package com.pro.ssm.model;

public class Cls {
    private Integer clsid;

    private Integer capacity;

    private Integer choseNum;

    private String tid;

    public Integer getClsid() {
        return clsid;
    }

    public void setClsid(Integer clsid) {
        this.clsid = clsid;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getChoseNum() {
        return choseNum;
    }

    public void setChoseNum(Integer choseNum) {
        this.choseNum = choseNum;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }
}