package com.itsoft.ab.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 31.05.14
 * Time: 0:57
 */
public class JCallsModel {
    private int callId;
    private int userId;
    private Date date;
    private String action;

    public JCallsModel() {
    }

    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "JCallsModel{" +
                "callId=" + callId +
                ", userId=" + userId +
                ", date=" + date +
                ", action='" + action + '\'' +
                '}';
    }
}
