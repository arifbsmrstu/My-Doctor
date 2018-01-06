package com.themebd.mydoctorfinal;

/**
 * Created by arif on 06-Nov-17.
 */

public class Message {
    private String msg,type;
    private long time;
    private Boolean seen;
    private String from;
    private String to;

    public Message(){

    }

    public Message(String from, String msg, Boolean seen,long time, String type,String to) {
        this.msg = msg;
        this.type = type;
        this.time = time;
        this.seen = seen;
        this.from = from;
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
