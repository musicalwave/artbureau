package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 12.11.13
 * Time: 22:52
 */
public class CallModel extends BasicModel {
    private int id;
    private int clientId;
    private int statusId;
    private String comment;
    private Date date;
    private String typeIds;
    private List<String> typeIdsList;
    private int adId;
    private int teacherId;
    private String teacherName;
    private String clientFName;
    private String clientLName;

    //Поля, необходимые для отображения на странице

    private String dateS;
    private String typeS;
    private String adS;
    private String phone1;
    private String phone2;
    private String clientComment;
    private String statusName;
    private String commentNew;

    public CallModel() {
    }

    public String getCommentNew() {
        return commentNew;
    }

    public void setCommentNew(String commentNew) {
        this.commentNew = commentNew;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    public List<String> getTypeIdsList() {
        return typeIdsList;
    }

    public void setTypeIdsList(List<String> typeIdsList) {
        this.typeIdsList = typeIdsList;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClientFName() {
        return clientFName;
    }

    public void setClientFName(String clientName) {
        this.clientFName = clientName;
    }
    public String getClientLName() {
        return clientLName;
    }

    public void setClientLName(String clientName) {
        this.clientLName = clientName;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public String getAdS() {
        return adS;
    }

    public void setAdS(String adS) {
        this.adS = adS;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getClientComment() {
        return clientComment;
    }

    public void setClientComment(String clientcomment) {
        this.clientComment = clientcomment;
    }

    @Override
    public String toString() {
        return "CallModel{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", statusId=" + statusId +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", adId=" + adId +
                ", typeIds=" + typeIds +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", clientFName='" + clientFName + '\'' +
                ", clientLName='" + clientLName + '\'' +
                ", dateS='" + dateS + '\'' +
                ", typeS='" + typeS + '\'' +
                ", adS='" + adS + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", clientComment='" + clientComment + '\'' +
                ", statusName='" + statusName + '\'' +
                ", commentNew='" + commentNew + '\'' +
                '}';
    }
}
