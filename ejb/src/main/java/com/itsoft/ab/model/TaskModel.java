package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 01.01.14
 * Time: 21:57
 */
public class TaskModel {
    private int id;
    private long date;
    private int fromId;
    private int toId;
    private String message;
    private int clientId;
    private String clientName;
    private int done;
    private long hash;

    private int active;
    private int important;
    private int info;
    private String comment;
    private long time;
    private int groupId;
    private String teacherName;
    private int teacherId;
    private long startDate;


    //В базе нет, нужны для отображения
    private String dateS;
    private String toIds;
    private String fromName;
    private String toName;
    private String timeS;
    private String startDateS;
    private String groupName;
    private String hashS;
    private String commentNew;


    public TaskModel() {
    }

    public String getCommentNew() {
        return commentNew;
    }

    public void setCommentNew(String commentNew) {
        this.commentNew = commentNew;
    }

    public String getHashS() {
        return hashS;
    }

    public void setHashS(String hashS) {
        this.hashS = hashS;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStartDateS() {
        return startDateS;
    }

    public void setStartDateS(String startDateS) {
        this.startDateS = startDateS;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getToIds() {
        return toIds;
    }

    public void setToIds(String toIds) {
        this.toIds = toIds;
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

    public String getTimeS() {
        return timeS;
    }

    public void setTimeS(String timeS) {
        this.timeS = timeS;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", date=" + date +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", message='" + message + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", done=" + done +
                ", hash=" + hash +
                ", active=" + active +
                ", important=" + important +
                ", info=" + info +
                ", comment='" + comment + '\'' +
                ", time=" + time +
                ", groupId=" + groupId +
                ", teacherName='" + teacherName + '\'' +
                ", teacherId=" + teacherId +
                ", startDate=" + startDate +
                ", dateS='" + dateS + '\'' +
                ", toIds='" + toIds + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", timeS='" + timeS + '\'' +
                ", startDateS='" + startDateS + '\'' +
                ", groupName='" + groupName + '\'' +
                ", hashS='" + hashS + '\'' +
                ", commentNew='" + commentNew + '\'' +
                '}';
    }
}
