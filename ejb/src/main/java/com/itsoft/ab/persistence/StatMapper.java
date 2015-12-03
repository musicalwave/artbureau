package com.itsoft.ab.persistence;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 23.08.14
 * Time: 20:58
 */
public interface StatMapper {
    int getNCalls();

    int getNClients();

    Integer getValueDo(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate);

    Integer getValuePlan(@Param("fromDate")long fromDate, @Param("toDate")long toDate);
}
