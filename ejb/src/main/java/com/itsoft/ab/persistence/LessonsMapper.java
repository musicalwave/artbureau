package com.itsoft.ab.persistence;

import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.LessonModel;
import com.itsoft.ab.model.LessonWeb;
import com.itsoft.ab.model.SimpleModel;
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
    ContractModel getLessonContract(int id);
    LessonModel getLesson(int id);
    LessonModel getOriginalLesson(int id);
    void updateStatus(LessonModel lesson);
    void insertLesson(LessonModel lesson);
    List<LessonWeb> getLastLessons(
        @Param("fromDate") Date fromDate,
        @Param("toDate") Date toDate);
    void updateLesson(@Param("lesson") LessonModel lesson);
    void updateShiftedTo(
        @Param("lessonId") int lessonId,
        @Param("shiftedTo") Integer shiftedTo);
    void updateCancelled(
        @Param("lessonId") int lessonId,
        @Param("cancelled") int cancelled);
    List<LessonModel> getLessonsWithinPeriod(
        @Param("contractId") int contractId,
        @Param("dateFrom") Date dateFrom,
        @Param("dateTo") Date dateTo,
        @Param("statusId") int statusId);
    List<SimpleModel> getLessonStatuses();
    void deleteLesson(int id);
    void deleteLessonsWithinPeriod(
        @Param("contractId") int contractId,
        @Param("dateFrom") Date dateFrom,
        @Param("dateTo") Date dateTo,
        @Param("statusId") int statusId);
}
