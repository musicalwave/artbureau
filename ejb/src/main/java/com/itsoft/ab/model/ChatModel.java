package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 13:28
 */
public class ChatModel extends BasicModel{
    private int id;
    private int userId;
    private String message;
    private int statusId;
    private Timestamp date;
    private int isImportant;

    public ChatModel() {
    }

    public ChatModel(int id, int userId, String message, int statusId, Timestamp date, int important) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.statusId = statusId;
        this.date = date;
        isImportant = important;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getImportant() {
        return isImportant;
    }

    public void setImportant(int important) {
        isImportant = important;
    }

    @Override
    public String toString() {
        return "ChatModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", statusId=" + statusId +
                ", date=" + date +
                ", isImportant=" + isImportant +
                '}';
    }
}
