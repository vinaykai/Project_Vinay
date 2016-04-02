package com.vkai.project;

/**
 * Created by jianxing on 3/11/2016.
 */
public class history {
    private static int id=0;
    private String mail;
    private String startTime;
    private String time;
    public history(String mail,String startTime,String time)
    {
        this.mail=mail;
        this.startTime=startTime;
        this.time=time;
        id++;
    }

    public static int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTime() {
        return time;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "history{" +
                "mail='" + mail + '\'' +
                ", startTime='" + startTime + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
