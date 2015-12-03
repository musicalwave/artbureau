package com.itsoft.ab.persistence;

import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.model.LessonWeb;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 02.02.14
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public interface ScheduleMapper {
    LessonWeb getEvent(int id);
    LessonWeb getLesson(int id);
    //event+lesson
    List<LessonWeb> selectEventsByDate(java.sql.Date date);
    List<LessonWeb> selectEventsByDateAndClass(@Param("date") java.util.Date date, @Param("roomId") int roomId);
    List<LessonWeb> selectEventsByDateAndTeacher(@Param("date") java.util.Date date, @Param("teacherId") int teacherId);
    //events
    List<LessonWeb> selectAllByClass(int roomId);
    List<LessonWeb> selectAllByTeacher(int teacherId);
    List<LessonWeb> selectAllActive();

    int countLessons(@Param("eventId") int eventId, @Param("date") Date date);
    int countLessonsByTeacherAndTime(@Param("teacherTypeId") int teacherTypeId, @Param("startTime") int startTime, @Param("date") java.util.Date date);
    void shiftLesson(@Param("eventId") int eventId, @Param("lessonId") int lessonId, @Param("newDate") Date newDate);

    List<EventModel> selectEmptyEventsByDateAndTeacher(@Param("date")Date date, @Param("teacherId")int teacherId);
}
