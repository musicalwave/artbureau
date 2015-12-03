package com.itsoft.ab.persistence;

import com.itsoft.ab.model.LessonModel;
import com.itsoft.ab.model.LessonWeb;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 28.02.14
 * Time: 0:16
 */
public interface LessonsMapper {
    List<LessonModel> getContractLessons(int contractId);

    LessonModel getLesson(int id);
    void updateStatus(LessonModel lesson);

    void insertLesson(LessonModel lesson);

    void deleteLesson(LessonModel lesson);

    List<LessonModel> getContractLessonsBetweenDates(@Param("id")int id, @Param("startdate")Date startdate, @Param("finishdate")Date finishdate);

    List<LessonWeb> getLastLessons(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate);
}
