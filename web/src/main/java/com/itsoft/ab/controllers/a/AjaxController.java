package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.*;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 25.02.14
 * Time: 12:00
 */
@Controller
public class AjaxController {
    private final static Logger LOG = Logger.getLogger(AjaxController.class);

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private ScheduleMaster scheduleMaster;

    @Autowired
    private LessonMaster lessonMaster;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private TeacherTypeMaster teacherTypeMaster;

    @Autowired
    private EventMaster eventMaster;

    @RequestMapping(value = "/do/teacher/find", method = RequestMethod.POST)
    public
    @ResponseBody
    List<TeacherModel> findTeacher(@RequestBody final  TeacherModel teacher) {
        return teacherMapper.findTeacher("%" + teacher.getName() + "%",null,null,0);
    }

    @RequestMapping(value = "/do/client/find", method = RequestMethod.POST)
    public
    @ResponseBody
    List<ClientModel> findClient(@RequestBody final  ClientModel client) {
        return clientsMapper.findClients(null, "%" + client.getLname() + "%", null, null, null, null);
    }

    @RequestMapping(value = "/do/rooms/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RoomModel> getRooms() {
        //Филиал 1
        return roomMapper.selectActiveFilialRooms(1);
    }

    @RequestMapping(value = "/do/lesson/{lessonId}", method = RequestMethod.GET)
    public @ResponseBody LessonWeb getLesson(@PathVariable int lessonId){
        LessonWeb lesson = scheduleMaster.getLesson(lessonId);
        return lesson;
    }

    @RequestMapping(value = "/do/lessons/all", method = RequestMethod.GET)
    public @ResponseBody Map<String, List<LessonWeb>> getAllLessons(@RequestParam("sd") String sdate, @RequestParam("fd") String fdate){
        List<LessonWeb> lessons = new ArrayList<LessonWeb>();
        Date startDate, finishDate;

        try {
            startDate = new SimpleDateFormat("dd-MM-yy").parse(sdate);
            finishDate = new SimpleDateFormat("dd-MM-yy").parse(fdate);
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(finishDate);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            lessons.addAll(scheduleMapper.selectEventsByDate(new java.sql.Date(date.getTime())));
        }

        return lessonMaster.sortLessonsByClass(lessons);
    }

    @RequestMapping(value = "/do/lessons/{classId}", method = RequestMethod.GET)
    public @ResponseBody List<LessonWeb> getClassLessons(@PathVariable int classId, @RequestParam("sd") String sdate, @RequestParam("fd") String fdate){
        List<LessonWeb> lessons = new ArrayList<LessonWeb>();
        Date startDate, finishDate;

        try {
            startDate = new SimpleDateFormat("dd-MM-yy").parse(sdate);
            finishDate = new SimpleDateFormat("dd-MM-yy").parse(fdate);
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(finishDate);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            lessons.addAll(scheduleMapper.selectEventsByDateAndClass(new java.sql.Date(date.getTime()), classId));
        }
        return lessons;
    }

    @RequestMapping(value = "/do/lessons/teacher/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody List<LessonWeb> getTeacherLessons(@PathVariable int teacherId, @RequestParam("sd") String sdate, @RequestParam("fd") String fdate){
        List<LessonWeb> lessons = new ArrayList<LessonWeb>();
        Date startDate, finishDate;

        try {
            startDate = new SimpleDateFormat("dd-MM-yy").parse(sdate);
            finishDate = new SimpleDateFormat("dd-MM-yy").parse(fdate);
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(finishDate);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            lessons.addAll(scheduleMapper.selectEventsByDateAndTeacher(new java.sql.Date(date.getTime()), teacherId));
        }
        return lessons;
    }

    @RequestMapping(value = "/do/event/{eventId}", method = RequestMethod.GET)
    public @ResponseBody LessonWeb getEvent(@PathVariable int eventId){
        LessonWeb event = scheduleMapper.getEvent(eventId);
        return event;
    }

    @RequestMapping(value = "/do/events/all", method = RequestMethod.GET)
    public @ResponseBody List<LessonWeb> getAllEvents(){
        List<LessonWeb> lessons = scheduleMapper.selectAllActive();
        return lessons;
    }

    @RequestMapping(value = "/do/events/{classId}", method = RequestMethod.GET)
    public @ResponseBody List<LessonWeb> getClassEvents(@PathVariable int classId){
        List<LessonWeb> lessons = scheduleMapper.selectAllByClass(classId);
        return lessons;
    }

    @RequestMapping(value = "/do/events/teacher/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody List<LessonWeb> getTeacherEvents(@PathVariable int teacherId){
        List<LessonWeb> lessons = scheduleMapper.selectAllByTeacher(teacherId);
        return lessons;
    }

    @RequestMapping(value = "/do/types/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TypeModel> getAllTypes(){
        List<TypeModel> types = typesMapper.selectAllActive();
        return types;
    }

    @RequestMapping(value = "/do/teachers/type/{typeId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherTypeModel> getTypeTeachers(@PathVariable int typeId){
        List<TeacherTypeModel> teachers = teacherTypeMapper.getAllActiveByType(typeId);
        return teachers;
    }

    @RequestMapping(value = "/do/teachers", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherModel> getTeachersByTypes(@RequestParam(value = "types") String[] typeIds,
                                          @RequestParam(value = "needEmpty", required = false, defaultValue = "true") boolean needEmpty) {

        List<TeacherModel> teachers = new ArrayList<>();

        if(needEmpty)
            teachers.add(TeacherMaster.getEmptyTeacher());

        if(typeIds.length == 0)
            teachers.addAll(teacherMapper.getActiveTeachers());
        else
            teachers.addAll(teacherMapper.getActiveTeachersByTypes(typeIds));

        return teachers;
    }

    @RequestMapping(value="/do/teachers/working-days", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Integer> getTeacherWorkingDays(@RequestParam(value = "teacherTypeId") int teacherTypeId) {
        TeacherTypeModel teacherTypeModel = teacherTypeMapper.getById(teacherTypeId);
        return teacherMapper.getWorkingDays(teacherTypeModel.getTeacherId());
    }

    @RequestMapping(value="/do/teachers/schedule", method = RequestMethod.GET)
    public
    @ResponseBody
    List<EventModel> getTeacherSchedule(@RequestParam(value = "teacherTypeId") int teacherTypeId) {
        TeacherTypeModel teacherTypeModel = teacherTypeMapper.getById(teacherTypeId);
        return eventMaster.getEmptyEvents(teacherTypeModel.getTeacherId());
    }

    @RequestMapping(value="/do/teachers/price", method = RequestMethod.GET)
    public
    @ResponseBody
    int getPrice(@RequestParam(value = "teacherTypeId") int teacherTypeId,
                 @RequestParam(value = "typeId") int typeId,
                 @RequestParam(value = "contractType") int contractType) {

        TeacherTypeModel teacherTypeModel = teacherTypeMapper.getById(teacherTypeId);
        return teacherTypeMaster.getPrice(teacherTypeModel.getTeacherId(), typeId, contractType);
    }

    @RequestMapping(value="/do/lessons/room", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LessonWeb> getLessonsByRoom(@RequestParam(value = "roomId") int roomId,
                                     @RequestParam(value = "leftDate") String leftDate,
                                     @RequestParam(value = "rightDate") String rightDate) {
        return  scheduleMapper.getLessonsByRoom(roomId, leftDate, rightDate);
    }

    @RequestMapping(value="/do/lessons/update", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean updateLessonDate(@RequestParam(value = "id") int id,
                             @RequestParam(value = "teacherId") int teacherId,
                             @RequestParam(value = "roomId") int roomId,
                             @RequestParam(value = "date") String date,
                             @RequestParam(value = "weekday") int weekday,
                             @RequestParam(value = "startTime") String startTime,
                             @RequestParam(value = "finishTime") String finishTime) {

        EventModel emptyEvent =
                scheduleMapper.getEmptyEvent(
                    teacherId,
                    roomId,
                    weekday,
                    startTime,
                    finishTime,
                    date);

        if(emptyEvent != null) {
            scheduleMapper.shiftLesson(emptyEvent.getId(), id, date);
            return true;
        }

        return false;
    }

    @RequestMapping(value = "/do/events", method = RequestMethod.GET)
    public
    @ResponseBody
    List<EventModel> getEmptyEventsByTeacherAndRoom(@RequestParam(value = "teacherId") int teacherId,
                                               @RequestParam(value = "roomId") int roomId) {
        return scheduleMapper.getEmptyEventsByTeacherAndRoom(teacherId, roomId);
    }

    @RequestMapping(value = "/do/lessons/conduct", method = RequestMethod.POST)
    public
    @ResponseBody
    int conductLesson(@RequestParam(value = "id") int id) {
        LessonModel lesson = lessonsMapper.getLesson(id);
        if(lesson != null) {
            lessonMaster.doLesson(lesson);
            return  lesson.getStatusId();
        }

        return 0;
    }

    @RequestMapping(value = "/do/lessons/burn", method = RequestMethod.POST)
    public
    @ResponseBody
    int burnLesson(@RequestParam(value = "id") int id) {
        LessonModel lesson = lessonsMapper.getLesson(id);
        if(lesson != null) {
            lessonMaster.burnLesson(lesson);
            return lesson.getStatusId();
        }

        return 0;
    }
}
