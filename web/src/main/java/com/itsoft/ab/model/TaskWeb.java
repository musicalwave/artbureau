package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 11.01.14
 * Time: 20:40
 */
public class TaskWeb {
    private int id;
    private long dateL;
    private int fromId;
    private int toId;
    private String message;
    private int clientId;
    private String clientName;
    private int done;
    private long hash;

    private int active;
    private String important;
    private String info;
    private String mail;
    private String comment;
    private long timeL;
    private int groupId;
    private int teacherId;
    private String teacherName;
    private String startDate;

    private String date;
    private String toIds;
    private String fromName;
    private String toName;
    private String time;
    private String toGroups;




    public TaskWeb() {
    }

    public TaskWeb(TaskModel task){
        this.id = task.getId();
        this.fromId = task.getFromId();
        this.toId = task.getToId();
        this.clientId = task.getClientId();
        this.dateL = task.getDate();
        this.message = task.getMessage();
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getToIds() {
        return toIds;
    }

    public void setToIds(String toIds) {
        this.toIds = toIds;
    }

    public long getDateL() {
        return dateL;
    }

    public void setDateL(long dateL) {
        this.dateL = dateL;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
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

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeL() {
        return timeL;
    }

    public void setTimeL(long timeL) {
        this.timeL = timeL;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int tacherId) {
        this.teacherId = tacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacher) {
        this.teacherName = teacher;
    }

    public String getToGroups() {
        return toGroups;
    }

    public void setToGroups(String toGroups) {
        this.toGroups = toGroups;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "TaskWeb{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", clientId=" + clientId +
                ", dateL=" + dateL +
                ", message='" + message + '\'' +
                ", toIds='" + toIds + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", date='" + date + '\'' +
                ", clientName='" + clientName + '\'' +
                ", time='" + time + '\'' +
                ", timeL=" + timeL +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", toGroups='" + toGroups + '\'' +
                ", important=" + important +
                ", info=" + info +
                ", comment='" + comment + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
