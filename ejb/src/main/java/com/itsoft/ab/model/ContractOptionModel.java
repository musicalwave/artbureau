package com.itsoft.ab.model;

import com.itsoft.ab.BasicModel;

public class ContractOptionModel extends BasicModel {
    private int id;
    private String name;
    private int lessonCount;
    private int maxPaymentsCount;
    private int paymentInterval;
    private int maxShifts;
    private int arbitrary;

    public ContractOptionModel() {

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

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getMaxPaymentsCount() {
        return maxPaymentsCount;
    }

    public void setMaxPaymentsCount(int maxPaymentsCount) {
        this.maxPaymentsCount = maxPaymentsCount;
    }

    public int getPaymentInterval() {
        return paymentInterval;
    }

    public void setPaymentInterval(int paymentInterval) {
        this.paymentInterval = paymentInterval;
    }

    public int getMaxShifts() {
        return maxShifts;
    }

    public void setMaxShifts(int maxShifts) {
        this.maxShifts = maxShifts;
    }

    public int getArbitrary() {
        return arbitrary;
    }

    public void setArbitrary(int arbitrary) {
        this.arbitrary = arbitrary;
    }
}
