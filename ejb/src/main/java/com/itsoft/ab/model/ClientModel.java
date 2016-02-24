package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 13:38
 */
public class ClientModel extends BasicModel {
    private int id;
    private String fname;
    private String lname;
    private String pname;
    private int countContracts;
    private int countActiveContracts;
    private int countPayments;
    private int moneyR;
    private int moneyV;
    private int balance;
    private int total;
    private String phone1;
    private String phone1name;
    private String phone2;
    private String phone2name;
    private String comment;
    private int statusId;
    private int ratingId;
    private int fromSite;
    private Date date;
    private Date firstContractDate;
    private String email;
    private String bdate;
    private String jdata;
    private int hasJdata;
    private int concerts;
    private int referent;
    private String referentFName;
    private String referentLName;

    public ClientModel() {
    }

    public String getReferentFName() {
        return referentFName;
    }

    public void setReferentFName(String referentFName) {
        this.referentFName = referentFName;
    }

    public String getReferentLName() {
        return referentLName;
    }

    public void setReferentLName(String referentLName) {
        this.referentLName = referentLName;
    }

    public int getReferent() {
        return referent;
    }

    public void setReferent(int referent) {
        this.referent = referent;
    }


    public int getConcerts() {
        return concerts;
    }

    public void setConcerts(int concerts) {
        this.concerts = concerts;
    }

    public String getPhone1name() {
        return phone1name;
    }

    public void setPhone1name(String phone1name) {
        this.phone1name = phone1name;
    }

    public String getPhone2name() {
        return phone2name;
    }

    public void setPhone2name(String phone2name) {
        this.phone2name = phone2name;
    }

    public int getHasJdata() {
        return hasJdata;
    }

    public void setHasJdata(int hasJdata) {
        this.hasJdata = hasJdata;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFirstContractDate() {
        return firstContractDate;
    }

    public void setFirstContractDate(Date firstContractDate) {
        this.firstContractDate = firstContractDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getCountContracts() {
        return countContracts;
    }

    public void setCountContracts(int countContracts) {
        this.countContracts = countContracts;
    }

    public int getCountActiveContracts() {
        return countActiveContracts;
    }

    public void setCountActiveContracts(int countActiveContracts) {
        this.countActiveContracts = countActiveContracts;
    }

    public int getCountPayments() {
        return countPayments;
    }

    public void setCountPayments(int countPayments) {
        this.countPayments = countPayments;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getFromSite() {
        return fromSite;
    }

    public void setFromSite(int fromSite) {
        this.fromSite = fromSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getJdata() {
        return jdata;
    }

    public void setJdata(String jdata) {
        this.jdata = jdata;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", pname='" + pname + '\'' +
                ", countContracts=" + countContracts +
                ", countPayments=" + countPayments +
                ", moneyR=" + moneyR +
                ", moneyV=" + moneyV +
                ", phone1='" + phone1 + '\'' +
                ", phone1name='" + phone1name + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", phone2name='" + phone2name + '\'' +
                ", comment='" + comment + '\'' +
                ", statusId=" + statusId +
                ", ratingId=" + ratingId +
                ", fromSite=" + fromSite +
                ", date=" + date +
                ", email='" + email + '\'' +
                ", bdate='" + bdate + '\'' +
                ", jdata='" + jdata + '\'' +
                ", hasJdata=" + hasJdata +
                ", concerts=" + concerts +
                ", referent=" + referent +
                ", referentFName='" + referentFName + '\'' +
                ", referentLName='" + referentLName + '\'' +
                '}';
    }
}
