package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 27.02.14
 * Time: 14:01
 */

/*Объект расписания:
eventId - id Ячейки в сетке расписания
startTime - время начала
finishTime - время окончания
weekday - день недели (1 - понедельник … 7 - воскресенье)
roomId - id класса
teacherId - id преподавателя
teacherName - имя преподавателя
hasLesson - (0 - ячейка свободна [дальнейшие параметры игнорируются], 1 - ячейка занята [дальнейшие параметры используются])

lessonId - id урока
date - дата урока
clientId - id клиента (игнорируется, если занятие групповое или ансамбль)
clientName - имя клиента (игнорируется, если занятие групповое или ансамбль)
contractId - id контракта занятия
typeId - id типа занятия
typeName - тип занятия (пример: Вокал)
clientStatus - 1-индивидуальное, 2-групповое, 3-ансамбль
doneStatus - 1-активное, 2-проведенное, 3-сгоревшее
contractStatus - 1-абонемент, 2-пробный
needSetStatus - флаг необходимости принития решения о doneStatus (1-надо, 0-не надо)
hasTask - флаг наличия задания (1-есть задание, 0-нет задания)
hasPayment - флаг необходимости принятия платежа (1-есть платеж, 0-нет платежа)*/

public class LessonWeb {

    private int eventId;
    private String startTime;
    private String finishTime;
    private int weekday;
    private int roomId;
    private String roomName; // нужно?
    private int teacherId;
    private String teacherName;
    private int hasLesson;

    private int lessonId;
    private String date;
    private int clientId;
    private String clientName;
    private String clientPhone;
    private int contractId;
    private int typeId;
    private String typeName;
    private int clientStatus;
    private int doneStatus;
    private int contractStatus;
    private int needSetStatus;
    private int hasTask;
    private int hasPayment;


    public LessonWeb() {
    }

    public LessonWeb(int eventId, String date, String startTime, String finishTime, int roomId, int teacherId, String teaherName, int hasLesson, int lessonId, int contractId, String clientName, int typeId, String typeName, int clientStatus, int doneStatus, int contractStatus, int needSetStatus, int hasTask, int hasPayment, int weekday) {

        this.eventId = eventId;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.roomId = roomId;
        this.teacherId = teacherId;
        this.teacherName = teaherName;
        this.hasLesson = hasLesson;
        this.lessonId = lessonId;
        this.contractId = contractId;
        this.clientName = clientName;
        this.typeId = typeId;
        this.typeName = typeName;
        this.clientStatus = clientStatus;
        this.doneStatus = doneStatus;
        this.contractStatus = contractStatus;
        this.needSetStatus = needSetStatus;
        this.hasTask = hasTask;
        this.hasPayment = hasPayment;
        this.weekday = weekday;

    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
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

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getHasLesson() {
        return hasLesson;
    }

    public void setHasLesson(int hasLesson) {
        this.hasLesson = hasLesson;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(int clientStatus) {
        this.clientStatus = clientStatus;
    }

    public int getDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(int doneStatus) {
        this.doneStatus = doneStatus;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public int getNeedSetStatus() {
        return needSetStatus;
    }

    public void setNeedSetStatus(int needSetStatus) {
        this.needSetStatus = needSetStatus;
    }

    public int getHasTask() {
        return hasTask;
    }

    public void setHasTask(int hasTask) {
        this.hasTask = hasTask;
    }

    public int getHasPayment() {
        return hasPayment;
    }

    public void setHasPayment(int hasPayment) {
        this.hasPayment = hasPayment;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    @Override
    public String toString() {
        return "LessonWeb{" +
                "eventId=" + eventId +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", roomId=" + roomId +
                ", teacherId=" + teacherId +
                ", teaherName='" + teacherName + '\'' +
                ", hasLesson=" + hasLesson +
                ", lessonId=" + lessonId +
                ", clientId=" + clientId +
                ", clientName=" + clientName +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", clientStatus=" + clientStatus +
                ", doneStatus=" + doneStatus +
                ", contractStatus=" + contractStatus +
                ", needSetStatus=" + needSetStatus +
                ", hasTask=" + hasTask +
                ", hasPayment=" + hasPayment +
                '}';
    }
}
