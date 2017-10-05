package com.example.gayanlakshitha.reclec;

/**
 * Created by Gayan Lakshitha on 9/15/2017.
 */

public class Subject {
    private String subject;
    private int tot_att;
    private int tot_days;

    public Subject(String subject, int tot_att, int tot_days) {
        this.subject = subject;
        this.tot_att = tot_att;
        this.tot_days = tot_days;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTot_att() {
        return tot_att;
    }

    public void setTot_att(int tot_att) {
        this.tot_att = tot_att;
    }

    public int getTot_days() {
        return tot_days;
    }

    public void setTot_days(int tot_days) {
        this.tot_days = tot_days;
    }
}
