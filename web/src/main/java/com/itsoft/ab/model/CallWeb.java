package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 29.05.14
 * Time: 15:00
 */
public class CallWeb extends BasicModel{
    //from CallModel
    private int callId;
    private Date callDate;
    private String callDateS;
    private String callComment;
    private int callAdId;
    private int callTypeId;
    private String callTeacher;
    private int callStatusId;


    //from ClientModel
    private int clientId;
    private String clientFname;
    private String clientLname;
    private String clientPname;
    private String clientPhone1;
    private String clientPhone2;
    private String clientPhone1name;
    private String clientPhone2name;
    private String clientComment;
    private String clientMail;

    public CallWeb() {
    }

    public CallWeb(ClientModel client){
        this.setClientId(client.getId());
        this.setClientFname(client.getFname());
        this.setClientLname(client.getLname());
        this.setClientPname(client.getPname());
        this.setClientPhone1(client.getPhone1());
        this.setClientPhone1name(client.getPhone1name());
        this.setClientPhone2(client.getPhone2());
        this.setClientPhone2name(client.getPhone2name());
        this.setClientMail(client.getEmail());

    }

    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getCallDateS() {
        return callDateS;
    }

    public void setCallDateS(String callDateS) {
        this.callDateS = callDateS;
    }

    public String getCallComment() {
        return callComment;
    }

    public void setCallComment(String callComment) {
        this.callComment = callComment;
    }

    public int getCallAdId() {
        return callAdId;
    }

    public void setCallAdId(int callAdId) {
        this.callAdId = callAdId;
    }

    public int getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeId(int callTypeId) {
        this.callTypeId = callTypeId;
    }

    public String getCallTeacher() {
        return callTeacher;
    }

    public void setCallTeacher(String callTeacher) {
        this.callTeacher = callTeacher;
    }

    public int getCallStatusId() {
        return callStatusId;
    }

    public void setCallStatusId(int callStatusId) {
        this.callStatusId = callStatusId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientFname() {
        return clientFname;
    }

    public void setClientFname(String clientFname) {
        this.clientFname = clientFname;
    }

    public String getClientLname() {
        return clientLname;
    }

    public void setClientLname(String clientLname) {
        this.clientLname = clientLname;
    }

    public String getClientPname() {
        return clientPname;
    }

    public void setClientPname(String clientPname) {
        this.clientPname = clientPname;
    }

    public String getClientPhone1() {
        return clientPhone1;
    }

    public void setClientPhone1(String clientPhone1) {
        this.clientPhone1 = clientPhone1;
    }

    public String getClientPhone2() {
        return clientPhone2;
    }

    public void setClientPhone2(String clientPhone2) {
        this.clientPhone2 = clientPhone2;
    }

    public String getClientPhone1name() {
        return clientPhone1name;
    }

    public void setClientPhone1name(String clientPhone1name) {
        this.clientPhone1name = clientPhone1name;
    }

    public String getClientPhone2name() {
        return clientPhone2name;
    }

    public void setClientPhone2name(String clientPhone2name) {
        this.clientPhone2name = clientPhone2name;
    }

    public String getClientComment() {
        return clientComment;
    }

    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }

    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    @Override
    public String toString() {
        return "CallWeb{" +
                "callId=" + callId +
                ", callDate=" + callDate +
                ", callDateS='" + callDateS + '\'' +
                ", callComment='" + callComment + '\'' +
                ", callAdId=" + callAdId +
                ", callTypeId=" + callTypeId +
                ", callTeacher='" + callTeacher + '\'' +
                ", callStatusId=" + callStatusId +
                ", clientId=" + clientId +
                ", clientFname='" + clientFname + '\'' +
                ", clientLname='" + clientLname + '\'' +
                ", clientPname='" + clientPname + '\'' +
                ", clientPhone1='" + clientPhone1 + '\'' +
                ", clientPhone2='" + clientPhone2 + '\'' +
                ", clientPhone1name='" + clientPhone1name + '\'' +
                ", clientPhone2name='" + clientPhone2name + '\'' +
                ", clientComment='" + clientComment + '\'' +
                ", clientMail='" + clientMail + '\'' +
                '}';
    }
}
