package com.itsoft.ab.sys;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 16.03.14
 * Time: 10:33
 */
public enum HTTPCode {
    UNKNOWN_ERROR(0, "Unknown error"),
    SUCCESS(200, "Success"),
    ERROR500(500, "Error 500");
    private final int id;
    private final String desc;

    HTTPCode(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String desc(){return desc;}

    public int id() {return id;}
}
