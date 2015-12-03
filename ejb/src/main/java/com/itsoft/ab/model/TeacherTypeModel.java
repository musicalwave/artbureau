package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 06.03.14
 * Time: 20:10
 */
public class TeacherTypeModel {
    private int id;
    private int active;
    private int teacherId;
    private int typeId;
    private int pPrice;
    private int gPrice;
    private int aPrice;
    private int dPrice;
    private int pPay;
    private int gPay;
    private int aPay;
    private int dPay;

    private String teacherName;
    private String typeName;

    public TeacherTypeModel() {
    }

    public TeacherTypeModel(TypeModel type) {

        this.setTypeId(type.getId());

        this.setpPrice(type.getpPrice());
        this.setgPrice(type.getgPrice());
        this.setaPrice(type.getaPrice());
        this.setdPrice(type.getdPrice());
        this.setpPay(type.getpPay());
        this.setgPay(type.getgPay());
        this.setaPay(type.getaPay());
        this.setdPay(type.getdPay());

    }

    public int getpPay() {
        return pPay;
    }

    public void setpPay(int pPay) {
        this.pPay = pPay;
    }

    public int getgPay() {
        return gPay;
    }

    public void setgPay(int gPay) {
        this.gPay = gPay;
    }

    public int getaPay() {
        return aPay;
    }

    public void setaPay(int aPay) {
        this.aPay = aPay;
    }

    public int getdPay() {
        return dPay;
    }

    public void setdPay(int dPay) {
        this.dPay = dPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public int getgPrice() {
        return gPrice;
    }

    public void setgPrice(int gPrice) {
        this.gPrice = gPrice;
    }

    public int getaPrice() {
        return aPrice;
    }

    public void setaPrice(int aPrice) {
        this.aPrice = aPrice;
    }

    public int getdPrice() {
        return dPrice;
    }

    public void setdPrice(int dPrice) {
        this.dPrice = dPrice;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TeacherTypeModel{" +
                "id=" + id +
                ", active=" + active +
                ", teacherId=" + teacherId +
                ", typeId=" + typeId +
                ", pPrice=" + pPrice +
                ", gPrice=" + gPrice +
                ", aPrice=" + aPrice +
                ", dPrice=" + dPrice +
                ", teacherName='" + teacherName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
