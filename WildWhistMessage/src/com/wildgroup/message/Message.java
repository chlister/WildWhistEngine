package com.wildgroup.message;
/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

import com.google.gson.Gson;

public class Message {
    private int method;
    private Object mObject;

    public Message(int method, Object mObject) {
        this.method = method;
        this.mObject = mObject;
    }

    public Message(String json){
        Message m = new Message(0, null);
        try {
            m = new Gson().fromJson(json, Message.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        this.method = m.method;
        this.mObject = m.mObject;
    }

    public String encode(){
        return new Gson().toJson(this);
    }


    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public Object getMobject() {
        return mObject;
    }

    public void setMobject(Object mObject) {
        this.mObject = mObject;
    }
}

