package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TeacherScheduleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherScheduleMapper {
    List<TeacherScheduleModel> getAllSchedules();
    List<TeacherScheduleModel> getTeacherSchedule(@Param("teacherId") int teacherId);
    List<TeacherScheduleModel> getTeacherScheduleByRoom(
        @Param("teacherId") int teacherId,
        @Param("roomId") int roomId);
    List<Integer> getTeacherWorkingDays(@Param("teacherId") int teacherId);
    void insertTeacherScheduleItem(@Param("item") TeacherScheduleModel item);
    void insertContractSchedule(@Param("items") List<TeacherScheduleModel> items);
    void updateContractScheduleItem(@Param("item") TeacherScheduleModel item);
    void deleteContractScheduleItem(@Param("id") int id);
}
