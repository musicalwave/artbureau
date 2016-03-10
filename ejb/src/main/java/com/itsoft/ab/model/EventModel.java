package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 14:54
 */
public class EventModel extends BasicModel{
    private int id;
    private int typeId;
    private int roomId;
    private int teacherId;
    private int statusId;
    private int countClients;
    private Time startTime;
    private Time finishTime;
    private int weekday;
    private int active;
    private int contractScheduleId;

    private String startTimeS;
    private String finishTimeS;
    private String teacherName;
    private String weekdayS;
    private String roomS;

    public EventModel() {
    }

    public String getStartTimeS() {
        return startTimeS;
    }

    public void setStartTimeS(String startTimeS) {
        this.startTimeS = startTimeS;
    }

    public String getFinishTimeS() {
        return finishTimeS;
    }

    public void setFinishTimeS(String finishTimeS) {
        this.finishTimeS = finishTimeS;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getWeekdayS() {
        return weekdayS;
    }

    public void setWeekdayS(String weekdayS) {
        this.weekdayS = weekdayS;
    }

    public String getRoomS() {
        return roomS;
    }

    public void setRoomS(String roomS) {
        this.roomS = roomS;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherTypeId) {
        this.teacherId = teacherTypeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getContractScheduleId() {
        return contractScheduleId;
    }

    public void setContractScheduleId(int contractScheduleId) {
        this.contractScheduleId = contractScheduleId;
    }

    public int getCountClients() {
        return countClients;
    }

    public void setCountClients(int countClients) {
        this.countClients = countClients;
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

    public void setFinishTime(Time endTime) {
        this.finishTime = endTime;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", roomId=" + roomId +
                ", teacherId=" + teacherId +
                ", statusId=" + statusId +
                ", countClients=" + countClients +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", weekday=" + weekday +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
