package com.itsoft.ab.sys;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 16.03.14
 * Time: 10:32
 */
public class HTTPResponse {
    private int id;
    private String desc;

    public HTTPResponse(HTTPCode r) {
        this.id = r.id();
        this.desc = r.desc();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "HTTPResponse{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }
}
