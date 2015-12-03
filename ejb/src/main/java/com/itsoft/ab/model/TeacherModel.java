package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 18:55
 */
public class TeacherModel extends BasicModel{
    private int id;
    private String name;
    private String phone;
    private String comment;
    private int scheduleId;
    private int hasCred;
    private String credentials;
    private String bdate;
    private String startDate;
    private int active;
    private String weekDays;

    private int type;
    private String types;

    public TeacherModel() {
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getHasCred() {
        return hasCred;
    }

    public void setHasCred(int hasCred) {
        this.hasCred = hasCred;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }


    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "TeacherModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", scheduleId=" + scheduleId +
                ", hasCred=" + hasCred +
                ", credentials='" + credentials + '\'' +
                ", bdate='" + bdate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", active=" + active +
                ", weekDays='" + weekDays + '\'' +
                ", type=" + type +
                ", types='" + types + '\'' +
                '}';
    }
}
