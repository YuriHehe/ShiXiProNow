package com.pro.ssm.enumcase;

public enum CodeType {
    SUCCESS(200),ERROR(1000),UNLOGIN(100),UNFINISHED(99999);

    private int value;
    private CodeType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
