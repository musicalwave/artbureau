package com.itsoft.ab.persistence;

import com.itsoft.ab.model.JClientsModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 16.05.14
 * Time: 10:37
 */
public interface JClientsMapper {
    public int insert(JClientsModel m);

    List<JClientsModel> getDateClients(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate);
}
