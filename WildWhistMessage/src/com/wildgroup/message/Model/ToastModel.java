package com.wildgroup.message.Model;

public class ToastModel {
    private ToastLevel level;
    private String msg;

    public ToastModel(ToastLevel level, String msg) {
        this.level = level;
        this.msg = msg;
    }

    public ToastLevel getLevel() {
        return level;
    }

    public String getMsg() {
        return msg;
    }
}
