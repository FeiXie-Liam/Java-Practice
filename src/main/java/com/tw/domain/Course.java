package com.tw.domain;

public enum Course {
    Math("数学"),
    English("英语"),
    Chinese("语文"),
    Coding("编程"),;

    Course(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
