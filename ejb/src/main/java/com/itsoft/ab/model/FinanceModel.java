package com.itsoft.ab.model;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.03.14
 * Time: 18:22
 */
public class FinanceModel {
    private int id;
    private Timestamp date;
    private int value;
    private int direction;
    private int clientId;
    private int teacherId;
    private int type;

    public FinanceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FinanceModel{" +
                "id=" + id +
                ", date=" + date +
                ", value=" + value +
                ", direction=" + direction +
                ", clientId=" + clientId +
                ", teacherId=" + teacherId +
                ", type=" + type +
                '}';
    }
}
