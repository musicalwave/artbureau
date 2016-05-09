package com.itsoft.ab.controllers.a;

import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private RoomMapper roomMapper;

    @Autowired
    private FilialsMapper filialsMapper;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private TeacherScheduleMapper teacherScheduleMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private CallsStatusMapper callsStatusMapper;

    @Autowired
    private DiscountsMapper discountsMapper;

    @Autowired
    private LessonsMapper lessonsMapper;

    @RequestMapping(value = "/do/rooms", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RoomModel> getRooms() {
        return roomMapper.selectActiveRooms();
    }

    @RequestMapping(value = "/do/filials", method = RequestMethod.GET)
    public
    @ResponseBody
    List<FilialModel> getFilials() {
        return filialsMapper.getFilials();
    }

    @RequestMapping(value = "/do/types/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TypeModel> getAllTypes(){
        return typesMapper.selectAllActive();
    }

    @RequestMapping(value = "/do/teacherTypes", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherTypeModel> getTeacherTypes() {
        return teacherTypeMapper.getAllActive();
    }

    @RequestMapping(value = "/do/teachers/type/{typeId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherTypeModel> getTypeTeachers(@PathVariable int typeId){
        return teacherTypeMapper.getAllActiveByType(typeId);
    }

    @RequestMapping(value = "/do/teachers/schedules", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TeacherScheduleModel> getTeachersSchedules() {
        return teacherScheduleMapper.getAllSchedules();
    }

    @RequestMapping(value = "/do/new-contract-status", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean isNewContractStatus(@RequestParam(value = "id") int id) {
        return callsStatusMapper.redirectToNewContract(id);
    }

    @RequestMapping(value = "/do/options", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ContractOptionModel> getContractOptions() {
        return contractsMapper.selectContractOptions();
    }

    @RequestMapping(value = "/do/discounts", method = RequestMethod.GET)
    public
    @ResponseBody
    List<SimpleModel> getDiscounts() {
        return discountsMapper.selectAllActive();
    }

    @RequestMapping(value = "/do/contractTypes", method = RequestMethod.GET)
    public
    @ResponseBody
    List<SimpleModel> getContractTypes() {
        return contractsMapper.selectContractTypes();
    }

    @RequestMapping(value = "/do/lessons/statuses", method = RequestMethod.GET)
    public
    @ResponseBody
    List<SimpleModel> getLessonStatuses() {
        return lessonsMapper.getLessonStatuses();
    }
}
