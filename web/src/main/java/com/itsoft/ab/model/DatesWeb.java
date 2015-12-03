package com.itsoft.ab.model;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 22.02.14
 * Time: 22:18
 */
public class DatesWeb {
    private String startdate;
    private String finishdate;

    public DatesWeb() {
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

    @Override
    public String toString() {
        return "DatesWeb{" +
                "startdate='" + startdate + '\'' +
                ", finishdate='" + finishdate + '\'' +
                '}';
    }
}
