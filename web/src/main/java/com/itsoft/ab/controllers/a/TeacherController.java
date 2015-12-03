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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        List<TeacherTypeModel> ts = teacherTypeMapper.getTeacherTypes(teacher.getId());
        List<Integer> ids = new ArrayList<Integer>();

        for(TeacherTypeModel tt : ts){
            ids.add(tt.getTypeId());
        }

        if(null != teacher.getTypes()){
            String [] types = teacher.getTypes().split(",");
            for (String type : types){
                Integer typeId = Integer.parseInt(type);
                if(ids.contains(typeId)){
                    ids.remove(typeId);
                }else{
                    teacherTypeMaster.insertTypeTeacher(typeId.intValue(), teacher.getId());
                }
            }
        }

        if(!ids.isEmpty()){
            for(Integer i : ids){
                for(TeacherTypeModel t : ts){
                    if(t.getTypeId() == i.intValue()){
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



}
