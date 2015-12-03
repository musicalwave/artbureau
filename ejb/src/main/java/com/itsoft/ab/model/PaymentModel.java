package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 18:38
 */
public class PaymentModel extends BasicModel{
    private int id;
    private int contractId;
    private long date;
    private int active;
    private int value;
    private int direction;
    private int planned;
    private int special;
    private int approved;
    private int approvedBy;
    private long approvedDate;
    private int cash;
    private int done;
    private String comment;

    //For web page
    private String typeS;
    private String teacherS;
    private String plannedS;
    private String dateS;
    private String commentNew;
    private int contractSpecial;
    private String approvedDateS;
    private String approvedName;
    private int clientId;
    private String clientS;

    public PaymentModel() {
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientS() {
        return clientS;
    }

    public void setClientS(String clientS) {
        this.clientS = clientS;
    }

    public String getApprovedName() {

        return approvedName;
    }

    public void setApprovedName(String approvedName) {
        this.approvedName = approvedName;
    }

    public String getApprovedDateS() {
        return approvedDateS;
    }

    public void setApprovedDateS(String approvedDateS) {
        this.approvedDateS = approvedDateS;
    }

    public int getContractSpecial() {
        return contractSpecial;
    }

    public void setContractSpecial(int contractSpecial) {
        this.contractSpecial = contractSpecial;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentNew() {
        return commentNew;
    }

    public void setCommentNew(String commentNew) {
        this.commentNew = commentNew;
    }

    public String getPlannedS() {
        return plannedS;
    }

    public void setPlannedS(String plannedS) {
        this.plannedS = plannedS;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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

    public int getPlanned() {
        return planned;
    }

    public void setPlanned(int planned) {
        this.planned = planned;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public long getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(long approvedDate) {
        this.approvedDate = approvedDate;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public String getTeacherS() {
        return teacherS;
    }

    public void setTeacherS(String teacherS) {
        this.teacherS = teacherS;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    @Override
    public String toString() {
        return "PaymentModel{" +
                "id=" + id +
                ", contractId=" + contractId +
                ", date=" + date +
                ", active=" + active +
                ", value=" + value +
                ", direction=" + direction +
                ", planned=" + planned +
                ", special=" + special +
                ", approved=" + approved +
                ", approvedBy=" + approvedBy +
                ", approvedDate=" + approvedDate +
                ", cash=" + cash +
                ", done=" + done +
                ", comment='" + comment + '\'' +
                ", typeS='" + typeS + '\'' +
                ", teacherS='" + teacherS + '\'' +
                ", plannedS='" + plannedS + '\'' +
                ", dateS='" + dateS + '\'' +
                ", commentNew='" + commentNew + '\'' +
                ", contractSpecial=" + contractSpecial +
                ", approvedDateS='" + approvedDateS + '\'' +
                ", approvedName='" + approvedName + '\'' +
                ", clientId=" + clientId +
                ", clientS='" + clientS + '\'' +
                '}';
    }
}
