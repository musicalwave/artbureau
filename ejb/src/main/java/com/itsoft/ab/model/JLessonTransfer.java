package com.itsoft.ab.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 29.04.14
 * Time: 16:03
 */
public class JLessonTransfer {
    private int id;
    private int lessonId;
    private Date fromDate;
    private Date toDate;
    private Date actDate;
    private int reason;
    private int userId;
    private String comment;

    private String userS;
    private String fromDateS;
    private String toDateS;
    private String actDateS;
    private String teacherName;
    private String teacherId;
    private String contractId;
    private String clientId;
    private String clientName;

    public JLessonTransfer() {
    }

    public String getActDateS() {
        return actDateS;
    }

    public void setActDateS(String actDateS) {
        this.actDateS = actDateS;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserS() {
        return userS;
    }

    public void setUserS(String userS) {
        this.userS = userS;
    }

    public String getFromDateS() {
        return fromDateS;
    }

    public void setFromDateS(String fromDateS) {
        this.fromDateS = fromDateS;
    }

    public String getToDateS() {
        return toDateS;
    }

    public void setToDateS(String toDateS) {
        this.toDateS = toDateS;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "JLessonTransfer{" +
                "id=" + id +
                ", lessonId=" + lessonId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", actDate=" + actDate +
                ", reason=" + reason +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                ", userS='" + userS + '\'' +
                ", fromDateS='" + fromDateS + '\'' +
                ", toDateS='" + toDateS + '\'' +
                ", actDateS='" + actDateS + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
