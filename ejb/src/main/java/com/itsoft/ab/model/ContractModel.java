package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 13:49
 */
public class ContractModel extends BasicModel{
    private int id;
    private int clientId;
    private int teacherTypeId;
    private int countLessons;
    private int statusId;
    private Date date;
    private int availableLessons;
    private int countShifts;
    private int special;
    private int freezed;
    private long freezeDate;
    private long freezeFinishDate;
    private int cash;

    private int price;
    private int moneyR;
    private int moneyV;
    private int discount;

    private int contractType;
    private String days;
    private int trial;

    private int prev;

    //Для отображения на веб страницах
    private String teacherS;
    private int teacherId;
    private int typeId;
    private String typeS;
    private String statusS;
    private String clientFS;
    private String clientLS;
    private String dateS;
    private String freezeDateS;
    private String freezeFinishDateS;
    private String comment;


    private int moneyNeed;//Сколько еще денег нужно положить на контракт

    private List<PaymentModel> payments = new ArrayList<>();
    private int contractOptionId;

    public ContractModel() {
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTrial() {
        return trial;
    }

    public void setTrial(int trial) {
        this.trial = trial;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getContractType() {
        return contractType;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getFreezeFinishDate() {
        return freezeFinishDate;
    }

    public void setFreezeFinishDate(long freezeFinishDate) {
        this.freezeFinishDate = freezeFinishDate;
    }

    public String getFreezeDateS() {
        return freezeDateS;
    }

    public void setFreezeDateS(String freezeDateS) {
        this.freezeDateS = freezeDateS;
    }

    public String getFreezeFinishDateS() {
        return freezeFinishDateS;
    }

    public void setFreezeFinishDateS(String freezeFinishDateS) {
        this.freezeFinishDateS = freezeFinishDateS;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMoneyNeed() {
        return moneyNeed;
    }

    public void setMoneyNeed(int moneyNeed) {
        this.moneyNeed = moneyNeed;
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

    public int getTeacherTypeId() {
        return teacherTypeId;
    }

    public void setTeacherTypeId(int teacherTypeId) {
        this.teacherTypeId = teacherTypeId;
    }

    public int getCountLessons() {
        return countLessons;
    }

    public void setCountLessons(int countLessons) {
        this.countLessons = countLessons;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAvailableLessons() {
        return availableLessons;
    }

    public void setAvailableLessons(int availableLessons) {
        this.availableLessons = availableLessons;
    }

    public int getCountShifts() {
        return countShifts;
    }

    public void setCountShifts(int countShifts) {
        this.countShifts = countShifts;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public int getFreezed() {
        return freezed;
    }

    public void setFreezed(int freezed) {
        this.freezed = freezed;
    }

    public long getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(long freezeDate) {
        this.freezeDate = freezeDate;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMoneyR() {
        return moneyR;
    }

    public void setMoneyR(int moneyR) {
        this.moneyR = moneyR;
    }

    public int getMoneyV() {
        return moneyV;
    }

    public void setMoneyV(int moneyV) {
        this.moneyV = moneyV;
    }

    public String getTeacherS() {
        return teacherS;
    }

    public void setTeacherS(String teacherS) {
        this.teacherS = teacherS;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public String getStatusS() {
        return statusS;
    }

    public void setStatusS(String statusS) {
        this.statusS = statusS;
    }

    public String getClientFS() {
        return clientFS;
    }

    public void setClientFS(String clientFS) {
        this.clientFS = clientFS;
    }

    public String getClientLS() {
        return clientLS;
    }

    public void setClientLS(String clientLS) {
        this.clientLS = clientLS;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getContractOptionId() {
        return contractOptionId;
    }

    public void setContractOptionId(int contractOptionId) {
        this.contractOptionId = contractOptionId;
    }

    public List<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentModel> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "ContractModel{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", teacherTypeId=" + teacherTypeId +
                ", countLessons=" + countLessons +
                ", statusId=" + statusId +
                ", date=" + date +
                ", availableLessons=" + availableLessons +
                ", countShifts=" + countShifts +
                ", special=" + special +
                ", freezed=" + freezed +
                ", freezeDate=" + freezeDate +
                ", cash=" + cash +
                ", price=" + price +
                ", money=" + moneyR +
                ", moneyV=" + moneyV +
                ", teacherS='" + teacherS + '\'' +
                ", typeS='" + typeS + '\'' +
                ", statusS='" + statusS + '\'' +
                ", clientFS='" + clientFS + '\'' +
                ", clientLS='" + clientLS + '\'' +
                ", dateS='" + dateS + '\'' +
                '}';
    }



}
