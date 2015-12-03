package com.itsoft.ab.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 26.02.14
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleInput implements Serializable {

    private static final long serialVersionUID = 1L;

    private String startdate;
    private String finishdate;

    public String getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(String finishdate) {
        this.finishdate = finishdate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
}