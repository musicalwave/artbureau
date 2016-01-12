package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.LessonMaster;
import com.itsoft.ab.beans.ScheduleMaster;
import com.itsoft.ab.beans.TeacherMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private RoomMapper roomMapper;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

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

//    @RequestMapping(value = "/do/lessons/all/test", method = RequestMethod.GET)
//    public @ResponseBody Map<String, List<LessonWeb>> getAllLessonsTest(@RequestParam("sd") String sdate, @RequestParam("fd") String fdate){
//        List<LessonWeb> lessons = new ArrayList<LessonWeb>();
//        lessons.add(new LessonWeb(1,"2014-03-27","14:00","15:00", 1, 1, "Тойминцева Алена", 0,0,0,"",0, "", 0,0,0,0,0,0,0));
//        lessons.add(new LessonWeb(2,"2014-03-27","15:00","16:30", 1, 1, "Тойминцева Алена", 0,0,0,"",0, "", 0,0,0,0,0,0,0));
//        lessons.add(new LessonWeb(3,"2014-03-27","17:00","18:00", 1, 1, "Тойминцева Алена", 1,1,1,"Петров Иван",1, "Вокал", 1,3,1,0,0,0,4));
//        lessons.add(new LessonWeb(4,"2014-03-27","20:00","21:00", 1, 1, "Тойминцева Алена", 1,2,2,"Сидоров Сергей",2, "Фортепиано", 2,2,2,1,1,0,4));
//        lessons.add(new LessonWeb(5,"2014-03-27","21:00","22:00", 1, 1, "Тойминцева Алена", 1,3,3,"Морозов Игорь",3, "Ударные", 3,1,1,0,1,1,4));
//
//
//        return lessonMaster.sortLessonsByClass(lessons);
//    }

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
    List<TeacherModel> getTeachersByTypes(@RequestParam(value = "types") String[] typeIds) {

        List<TeacherModel> teachers = new ArrayList<>();
        teachers.add(TeacherMaster.getEmptyTeacher());

        if(typeIds.length == 0)
            teachers.addAll(teacherMapper.getActiveTeachers());
        else
            teachers.addAll(teacherMapper.getActiveTeachersByTypes(typeIds));

        return teachers;
    }

}
