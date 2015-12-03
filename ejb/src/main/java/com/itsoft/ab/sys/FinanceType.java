package com.itsoft.ab.sys;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.03.14
 * Time: 18:30
 */
public enum FinanceType {
    CONTRACTPAYMENT(1, 1),
    CLIENTREPAYMENT(2, 0);

    private int id;
    //1 - в компанию
    //0 - из компании
    private int direction;

    private FinanceType(int id, int direction) {
        this.id = id;
        this.direction = direction;
    }

    public int id(){ return id; }
    public int direction(){ return direction; }
}
