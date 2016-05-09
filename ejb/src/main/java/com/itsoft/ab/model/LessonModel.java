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
    private String fromTime;
    private String toTime;
    private String statusName;

    private int contractType;
    private int payment;
    private int task;

    private int clientId;
    private String clientName;
    private String clientPhone;
    private int teacherId;
    private String teacherName;

    private String dateS;

    private int cancelled;
    private int temporary;
    private Boolean tempShift;
    private int shiftedTo;

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

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
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

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
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

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public int getTemporary() {
        return temporary;
    }

    public void setTemporary(int temporary) {
        this.temporary = temporary;
    }

    public Boolean getTempShift() {
        return tempShift;
    }

    public void setTempShift(Boolean tempShift) {
        this.tempShift = tempShift;
    }

    public int getShiftedTo() {
        return shiftedTo;
    }

    public void setShiftedTo(int shiftedTo) {
        this.shiftedTo = shiftedTo;
    }
}
