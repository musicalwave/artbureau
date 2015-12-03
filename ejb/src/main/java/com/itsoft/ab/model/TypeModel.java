package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: itertychnyi
 * Date: 13.10.13
 * Time: 19:03
 */
public class TypeModel extends BasicModel{

    private int id;
    private String name;
    private int active;

    private int pPrice;
    private int dPrice;
    private int gPrice;
    private int aPrice;
    private int pPay;
    private int dPay;
    private int gPay;
    private int aPay;

    public TypeModel() {
    }

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public int getdPrice() {
        return dPrice;
    }

    public void setdPrice(int dPrice) {
        this.dPrice = dPrice;
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

    public int getpPay() {
        return pPay;
    }

    public void setpPay(int pPay) {
        this.pPay = pPay;
    }

    public int getdPay() {
        return dPay;
    }

    public void setdPay(int dPay) {
        this.dPay = dPay;
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "TypeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", pPrice=" + pPrice +
                ", dPrice=" + dPrice +
                ", gPrice=" + gPrice +
                ", aPrice=" + aPrice +
                ", pPay=" + pPay +
                ", dPay=" + dPay +
                ", gPay=" + gPay +
                ", aPay=" + aPay +
                '}';
    }

    @Override
    public boolean equals(Object o){
        boolean result = false;

        if (o instanceof TypeModel){
            TypeModel tmp = (TypeModel) o;
            result = tmp.getId() == this.id && tmp.getName().equals(this.name) && tmp.getActive() == this.active;
        }

        return result;
    }

}
