package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 09.04.14
 * Time: 12:35
 */
public class TeacherDataModel {
    private String fname;
    private String lname;
    private String pname;
    private int teacherId;
    private String actnumber;

    private String startdate;
    private String finishdate;

    private int soloquantity;
    private int groupquantity;
    private int pairquantity;
    private int ensemblequantity;

    private int solorate;
    private int grouprate;
    private int pairrate;
    private int ensemblerate;


    public TeacherDataModel() {
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getActnumber() {
        return actnumber;
    }

    public void setActnumber(String actnumber) {
        this.actnumber = actnumber;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(String finishdate) {
        this.finishdate = finishdate;
    }

    public int getSoloquantity() {
        return soloquantity;
    }

    public void setSoloquantity(int soloquantity) {
        this.soloquantity = soloquantity;
    }

    public int getGroupquantity() {
        return groupquantity;
    }

    public void setGroupquantity(int groupquantity) {
        this.groupquantity = groupquantity;
    }

    public int getPairquantity() {
        return pairquantity;
    }

    public void setPairquantity(int pairquantity) {
        this.pairquantity = pairquantity;
    }

    public int getEnsemblequantity() {
        return ensemblequantity;
    }

    public void setEnsemblequantity(int ensemblequantity) {
        this.ensemblequantity = ensemblequantity;
    }

    public int getSolorate() {
        return solorate;
    }

    public void setSolorate(int solorate) {
        this.solorate = solorate;
    }

    public int getGrouprate() {
        return grouprate;
    }

    public void setGrouprate(int grouprate) {
        this.grouprate = grouprate;
    }

    public int getPairrate() {
        return pairrate;
    }

    public void setPairrate(int pairrate) {
        this.pairrate = pairrate;
    }

    public int getEnsemblerate() {
        return ensemblerate;
    }

    public void setEnsemblerate(int ensemblerate) {
        this.ensemblerate = ensemblerate;
    }

    @Override
    public String toString() {
        return "TeacherDataModel{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", pname='" + pname + '\'' +
                ", teacherId=" + teacherId +
                ", actnumber='" + actnumber + '\'' +
                ", startdate='" + startdate + '\'' +
                ", finishdate='" + finishdate + '\'' +
                ", soloquantity=" + soloquantity +
                ", groupquantity=" + groupquantity +
                ", pairquantity=" + pairquantity +
                ", ensemblequantity=" + ensemblequantity +
                ", solorate=" + solorate +
                ", grouprate=" + grouprate +
                ", pairrate=" + pairrate +
                ", ensemblerate=" + ensemblerate +
                '}';
    }
}
