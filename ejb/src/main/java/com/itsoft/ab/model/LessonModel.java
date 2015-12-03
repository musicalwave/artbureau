package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.util.Date;
import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 15:08
 */
public class LessonModel extends BasicModel{
    private int id;
    private int eventId;
    private int statusId;
    private int contractId;
    private Date date;
    private int shiftedByClient;
    private int shiftedByTeacher;

    private int roomId;
    private String roomName;
    private Time startTime;
    private Time finishTime;
    private String statusName;

    private int contractType;
    private int payment;
    private int task;

    private String dateS;

    public LessonModel() {
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public int getContractType() {
        return contractType;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShiftedByClient() {
        return shiftedByClient;
    }

    public void setShiftedByClient(int shiftedByClient) {
        this.shiftedByClient = shiftedByClient;
    }

    public int getShiftedByTeacher() {
        return shiftedByTeacher;
    }

    public void setShiftedByTeacher(int shiftedByTeacher) {
        this.shiftedByTeacher = shiftedByTeacher;
    }

    @Override
    public String toString() {
        return "LessonModel{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", statusId=" + statusId +
                ", contractId=" + contractId +
                ", date=" + date +
                ", shiftedByClient=" + shiftedByClient +
                ", shiftedByTeacher=" + shiftedByTeacher +
                '}';
    }
}
