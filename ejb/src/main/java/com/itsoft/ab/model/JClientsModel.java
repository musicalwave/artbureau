package com.itsoft.ab.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 16.05.14
 * Time: 10:38
 */
public class JClientsModel {
    private int id;

    private Date date;
    private String dateS;

    private int userId;
    private String userName;

    private int clientId;
    private String clientName;
    private String action;

    public JClientsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "JClientsModel{" +
                "date=" + date +
                ", dateS='" + dateS + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
