package com.itsoft.ab.persistence;

import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.model.LessonModel;
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

    List<EventModel> selectAllEvents();

    int countLessons(@Param("eventId") int eventId, @Param("date") Date date);
    int countLessonsByTeacherAndTime(@Param("teacherTypeId") int teacherTypeId, @Param("startTime") int startTime, @Param("date") java.util.Date date);
    void shiftLesson(@Param("eventId") int eventId,
                     @Param("lessonId") int lessonId,
                     @Param("newDate") String newDate);

    List<EventModel> selectEmptyEventsByTeacher(@Param("teacherId")int teacherId);

    List<LessonModel> getLessonsByRoomAndInterval(
        @Param("roomId") int roomId,
        @Param("fromDate") String fromDate,
        @Param("toDate") String toDate);

    EventModel getEmptyEvent(@Param("teacherId") int teacherId,
                             @Param("roomId") int roomId,
                             @Param("weekday") int weekday,
                             @Param("startTime") String startTime,
                             @Param("finishTime") String finishTime,
                             @Param("date") String date);

    List<EventModel> getEmptyEvents(@Param("teacherId") int teacherId,
                                    @Param("roomId") int roomId,
                                    @Param("start") String start,
                                    @Param("end") String end);

    void updateContractSchedule(@Param("contractId") int contractId, @Param("eventIds") String[] eventIds);

    List<EventModel> getContractSchedule(@Param("contractId") int contractId);

    void insertContractScheduleEvent(@Param("contractId") int contractId,
                                     @Param("eventId") int eventId);

    void updateContractScheduleEvent(@Param("contractScheduleId") int contractScheduleId,
                                     @Param("eventId") int eventId);

    void deleteContractScheduleEvent(@Param("contractScheduleId") int contractScheduleId);
}
