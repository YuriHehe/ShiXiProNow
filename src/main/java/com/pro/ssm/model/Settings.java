package com.pro.ssm.model;

public class Settings {
    private String skey;

    private String svalue;

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey == null ? null : skey.trim();
    }

    public String getSvalue() {
        return svalue;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue == null ? null : svalue.trim();
    }
}