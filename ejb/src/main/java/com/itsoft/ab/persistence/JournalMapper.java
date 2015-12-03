package com.itsoft.ab.persistence;

import com.itsoft.ab.model.JLessonTransfer;

import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 29.04.14
 * Time: 16:11
 */
public interface JournalMapper {
    List<JLessonTransfer> getDateTransfers(Date parse);

    void insertLessonTransfer(JLessonTransfer data);
}
