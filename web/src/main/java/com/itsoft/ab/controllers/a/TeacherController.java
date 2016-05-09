package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.*;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.Dates;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 19.02.14
 * Time: 12:01
 */
@Controller
public class TeacherController {
    Logger LOG = Logger.getLogger(TeacherController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private TypeMaster typeMaster;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ContractsMapper contractMapper;

    @Autowired
    private TeacherMaster teacherMaster;

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private EventMaster eventMaster;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private TeacherTypeMaster teacherTypeMaster;

    @Autowired
    private TeacherScheduleMaster teacherScheduleMaster;


    @RequestMapping(value="/teacher", method = RequestMethod.GET)
    public String newTeacher(Model m){
        m = authMaster.setModel(m);
        List<TypeModel> types = typesMapper.selectAllActive();
        m.addAttribute("teacher", new TeacherModel());
        m.addAttribute("types", types);
        m.addAttribute("days", (new Dates()).getWeekDays());
        return "/a/teacher/new";
    }

    @RequestMapping(value="/teacher", method = RequestMethod.POST)
    public String editTeacher(@ModelAttribute TeacherModel teacher, Model m){
        //Проверка наличия паспортных данных
        if(null != teacher.getCredentials() && !teacher.getCredentials().equals("")){
            teacher.setHasCred(1);
        }else{
            teacher.setHasCred(0);
        }
        teacherMapper.updateTeacher(teacher);

        //Сохранение направлений
        //#######################
        List<TeacherTypeModel> teacherTypes = teacherTypeMapper.getTeacherTypes(teacher.getId());
        List<Integer> teacherTypeIds = new ArrayList<Integer>();

        for(TeacherTypeModel tt : teacherTypes){
            teacherTypeIds.add(tt.getTypeId());
        }

        if(null != teacher.getTypes()){
            String [] newTypes = teacher.getTypes().split(",");
            for (String newType : newTypes){
                Integer newTypeId = Integer.parseInt(newType);
                if(teacherTypeIds.contains(newTypeId)){
                    teacherTypeIds.remove(newTypeId);
                }else{
                    teacherTypeMaster.insertTypeTeacher(newTypeId, teacher.getId());
                }
            }
        }

        if(!teacherTypeIds.isEmpty()){
            for(Integer teacherTypeId : teacherTypeIds){
                for(TeacherTypeModel t : teacherTypes){
                    if(t.getTypeId() == teacherTypeId.intValue()){
                        teacherTypeMapper.deleteTypeTeacher(t.getId());
                    }
                }
            }
        }
        //########################
        return "redirect:/teacher/all";
    }

    @RequestMapping(value="/teacher/{teacherId}", method = RequestMethod.GET)
    public String showTeacher(@PathVariable int teacherId, Model m){
        m = authMaster.setModel(m);

        List<ContractModel> contracts = contractMapper.getActiveTeacherContracts(teacherId);

        TeacherModel teacher = teacherMaster.getTeacherById(teacherId);

        DatesWeb dates = new DatesWeb();

        List<EventModel> c = eventsMapper.getTeacherEvents(teacherId);

        List<TypeModel> types = typeMaster.getTeacherTypes(teacherId);

        List<TypeModel> allTypes = typesMapper.selectAllActive();

        for(TypeModel t:allTypes){
            if(types.contains(t)){
                t.setActive(1);
            }else{
                t.setActive(0);
            }
        }

        m.addAttribute("allTypes", allTypes);
        m.addAttribute("types", types);
        m.addAttribute("teacher", teacher);
        m.addAttribute("contracts", contracts);
        m.addAttribute("dates", dates);
        m.addAttribute("events", c);
        return "/a/teacher/show";
    }

    @RequestMapping(value="/teacher/new", method = RequestMethod.POST)
    public String postTeacher(@ModelAttribute TeacherModel t, Model m){
        if (!t.getCredentials().isEmpty()){
            t.setHasCred(1);
        }else{
            t.setHasCred(0);
        }
        t = teacherMaster.insertTeacher(t);

        if(null != t.getTypes()){
            String [] types = t.getTypes().split(",");
            for (String type : types){
                int typeId = Integer.parseInt(type);
                teacherTypeMaster.insertTypeTeacher(typeId, t.getId());
            }
        }
        return "redirect:/teacher/" + t.getId();
    }

    @RequestMapping(value="/teacher/search", method = RequestMethod.GET)
    public String search(Model m){
        m = authMaster.setModel(m);
        List<TypeModel> types = typesMapper.selectAllActive();
        TeacherModel teacher = new TeacherModel();
        List<TeacherModel> teachers = new ArrayList<TeacherModel>();

        m.addAttribute("types", types);
        m.addAttribute("t", teacher);
        m.addAttribute("teachers", teachers);
        return "/a/teacher/search";
    }

    @RequestMapping(value="/teacher/search", method = RequestMethod.POST)
    public String searchPost(@ModelAttribute TeacherModel t, Model m){
        m = authMaster.setModel(m);

        //Подготавливаем данные для поиска
        if(t.getName().isEmpty()){
            t.setName(null);
        }if(t.getBdate().isEmpty()){
            t.setBdate(null);
        }if(t.getPhone().isEmpty()){
            t.setPhone(null);
        }


        List<TeacherModel> teachers = teacherMapper.findTeacher(t.getName(), t.getBdate(), t.getPhone(), t.getType());

        List<TypeModel> types = typesMapper.selectAllActive();
        TeacherModel teacher = new TeacherModel();

        m.addAttribute("types", types);
        m.addAttribute("t", teacher);
        m.addAttribute("teachers", teachers);
        return "/a/teacher/search";
    }

    @RequestMapping(value="/teacher/{teacherId}/contracts", method = RequestMethod.GET)
    public String allContracts(@PathVariable int teacherId, Model m){
        m = authMaster.setModel(m);
        TeacherModel t = teacherMapper.getTeacherById(teacherId);
        List<ContractModel> c = contractMapper.getTeacherContracts(teacherId);

        m.addAttribute("teacher", t);
        m.addAttribute("contracts", c);
        return "/a/teacher/contracts";
    }

    @RequestMapping(value="/teacher/{teacherId}/events", method = RequestMethod.GET)
    public String allEvents(@PathVariable int teacherId, Model m){
        m = authMaster.setModel(m);
        TeacherModel t = teacherMapper.getTeacherById(teacherId);
        List<EventModel> c = eventsMapper.getTeacherEvents(teacherId);
        EventModel event = new EventModel();
        event.setId(0);
        event.setTeacherId(teacherId);

        m.addAttribute("teacher", t);
        m.addAttribute("events", c);
        m.addAttribute("event", event);
        m.addAttribute("rooms", roomMapper.selectActiveFilialRooms(1));
        m.addAttribute("days", (new Dates()).getWeekDays());
        return "/a/teacher/events";
    }

    @RequestMapping(value="/teacher/{teacherId}/events/{eventId}", method = RequestMethod.GET)
    public String allEventsPost(@PathVariable int teacherId, @PathVariable int eventId, Model m){
        m = authMaster.setModel(m);
        TeacherModel t = teacherMapper.getTeacherById(teacherId);
        List<EventModel> c = eventsMapper.getTeacherEvents(teacherId);
        EventModel event = eventsMapper.getEventById(eventId);

        m.addAttribute("teacher", t);
        m.addAttribute("events", c);
        m.addAttribute("event", eventMaster.prepareEventToWeb(event));
        m.addAttribute("rooms", roomMapper.selectActiveFilialRooms(1));
        m.addAttribute("days", (new Dates()).getWeekDays());
        return "/a/teacher/events";
    }

    @RequestMapping(value="/teacher/{teacherId}/check", method = RequestMethod.POST)
    public String checkEvents(@PathVariable int teacherId, Model m) {
        m = authMaster.setModel(m);
        TeacherModel teacher = teacherMapper.getTeacherById(teacherId);
        List<EventModel> events = teacherMaster.checkTeacherDays(teacher);

        m.addAttribute("teacher", teacher);
        m.addAttribute("events", events);
        return "/a/teacher/events";

    }

    @RequestMapping(value="/teacher/all", method = RequestMethod.GET)
    public String all(Model m){
        m = authMaster.setModel(m);

        List<TeacherModel> teachers = teacherMapper.getActiveTeachers();


        m.addAttribute("teachers", teachers);
        return "/a/teacher/all";
    }

    ////ТопМенеджеры
    @RequestMapping(value="/tm/cred/{teacherId}", method = RequestMethod.GET)
    public String setCred(@PathVariable int teacherId, Model m){
        m = authMaster.setModel(m);
        TeacherModel teacher = teacherMapper.getTeacherCredById(teacherId);
        if(teacher == null){
            throw new ApplicationException(ECode.ERROR415);
        }
        m.addAttribute("teacher", teacher);
        return "/tm/teacher/cred";
    }

    @RequestMapping(value="/tm/cred", method = RequestMethod.POST)
    public String postCred(@ModelAttribute TeacherModel teacher, Model m){
        teacherMapper.updateCred(teacher);
        return "redirect:/teacher/" + teacher.getId();
    }

    // Ajax

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
        return teacherMaster.getTeacherWorkingDays(teacherTypeModel.getTeacherId());
    }

    @RequestMapping(
        value="/do/teachers/schedule",
        method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherScheduleModel> getTeacherSchedule(
        @RequestParam(value = "teacherTypeId") int teacherTypeId) {
            TeacherTypeModel teacherTypeModel = teacherTypeMapper.getById(teacherTypeId);
            return teacherScheduleMaster.getTeacherSchedule(teacherTypeModel.getTeacherId());
    }

    @RequestMapping(
        value="/do/teachers/schedule/room",
        method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherScheduleModel> getTeacherScheduleByRoom(
        @RequestParam(value = "teacherId") int teacherId,
        @RequestParam(value = "roomId") int roomId) {
            return teacherScheduleMaster.getTeacherScheduleByRoom(teacherId, roomId);
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

}
