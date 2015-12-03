package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.LessonMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.model.JLessonTransfer;
import com.itsoft.ab.model.LessonModel;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.EventsMapper;
import com.itsoft.ab.persistence.JournalMapper;
import com.itsoft.ab.persistence.LessonsMapper;
import com.itsoft.ab.sys.ECode;
import com.itsoft.ab.sys.HTTPCode;
import com.itsoft.ab.sys.HTTPResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 03.03.14
 * Time: 23:38
 */
@Controller
public class LessonController {
    Logger LOG = Logger.getLogger(LessonController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private ContractsMapper contractMapper;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private LessonMaster lessonMaster;

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private JournalMapper journalMapper;

    @RequestMapping(value="/contract/{contractId}/lesson", method = RequestMethod.GET)
    public String lesson(@PathVariable int contractId, Model m){
        m = authMaster.setModel(m);
        LessonModel lesson = new LessonModel();
        ContractModel contract = contractMapper.selectContract(contractId);

        m.addAttribute("lesson", lesson);
        m.addAttribute("contract", contract);
        m.addAttribute("step",1);
        m.addAttribute("error", 0);
        return "a/lessons/new";
    }

    @RequestMapping(value="/contract/{contractId}/lesson", method = RequestMethod.POST)
    public String lesson1(@ModelAttribute LessonModel lesson, @PathVariable int contractId, @RequestParam("s") int step, Model m){
        m = authMaster.setModel(m);
        ContractModel contract = contractMapper.selectContract(contractId);

        if(1 == step){

            Date date;

            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(lesson.getDateS());
            } catch (ParseException e) {
                throw new ApplicationException(ECode.ERROR415);
            }

            Calendar cc = Calendar.getInstance();
            cc.setTime(date);

            // Set 1 == Monday
            int weekday = cc.get(Calendar.DAY_OF_WEEK) - 1;
            if (0 == weekday)
                weekday = 7;

            List<EventModel> events = new ArrayList<EventModel>();

            String[] es = contract.getDays().split(",");
            for(String id : es){
                EventModel e = eventsMapper.getEventById(Integer.parseInt(id));
                if(e.getWeekday() == weekday){
                    events.add(e);
                }
            }

            if(events.isEmpty()){
                m.addAttribute("step",1);
                m.addAttribute("error", 1);
            }else{
                m.addAttribute("events", events);
                m.addAttribute("step",2);
            }
        }

        m.addAttribute("lesson", lesson);
        m.addAttribute("contract", contract);

        return "a/lessons/new";
    }

    @RequestMapping(value="/contract/{contractId}/lesson/save", method = RequestMethod.POST)
    public String saveLesson(@ModelAttribute LessonModel lesson, @PathVariable int contractId, Model m){
        lesson = lessonMaster.saveFromWeb(lesson);
        lesson.setStatusId(1);
        lesson.setShiftedByClient(0);
        lesson.setShiftedByTeacher(0);
        lesson.setPayment(0);
        lesson.setTask(0);
        lesson.setContractId(contractId);

        lessonsMapper.insertLesson(lesson);

        return "redirect:/contract/" + contractId;
    }

    @RequestMapping(value="/lesson/do", method = RequestMethod.POST)
    public String doLesson(@ModelAttribute LessonModel lesson, Model m){
        lesson = lessonsMapper.getLesson(lesson.getId());

        lessonMaster.doLesson(lesson);
        return "redirect:/contract/" + lesson.getContractId();
    }

    @RequestMapping(value="/lesson/burn", method = RequestMethod.POST)
    public String burnLesson(@ModelAttribute LessonModel lesson, Model m){
        lesson = lessonsMapper.getLesson(lesson.getId());

        lessonMaster.burnLesson(lesson);
        return "redirect:/contract/" + lesson.getContractId();
    }
//
//    @RequestMapping(value="/lesson/transfer", method = RequestMethod.POST)
//    public String transferLesson(@ModelAttribute LessonModel lesson, Model m){
//        LessonModel from = lessonsMapper.getLesson(lesson.getId());
//
//        //Проводим перенос
//        LessonModel to = lessonMaster.transferLesson(from);
//
//        //Подготавливаем логгирование
//        JLessonTransfer data = new JLessonTransfer();
//        data.setReason(reason);
//        data.setComment("");
//        data.setLessonId(lessonId);
//        data.setUserId(authMaster.getLoggedUserId());
//        data.setFromDate(from.getDate());
//        data.setToDate(to.getDate());
//
//        //Логгируем перенос
//        journalMapper.insertLessonTransfer(data);
//
//        return new HTTPResponse(HTTPCode.SUCCESS);
//    }

    //AjaxMethods
    @RequestMapping(value="do/lesson/{lessonId}/do", method = RequestMethod.POST)
    public
    @ResponseBody
    HTTPResponse doLessonAjax(@PathVariable int lessonId, Model m){
        LessonModel lesson = lessonsMapper.getLesson(lessonId);

        lessonMaster.doLesson(lesson);
        return new HTTPResponse(HTTPCode.SUCCESS);
    }

    @RequestMapping(value="do/lesson/{lessonId}/burn", method = RequestMethod.POST)
    public
    @ResponseBody
    HTTPResponse burnLessonAjax(@PathVariable int lessonId, Model m){
        LessonModel lesson = lessonsMapper.getLesson(lessonId);

        lessonMaster.burnLesson(lesson);
        return new HTTPResponse(HTTPCode.SUCCESS);
    }

    @RequestMapping(value="do/lesson/{lessonId}/transfer", method = RequestMethod.POST)
    public
    @ResponseBody
    HTTPResponse transferLessonAjax(@PathVariable int lessonId, @RequestParam("reason")int reason, Model m){
        LessonModel from = lessonsMapper.getLesson(lessonId);

        //Проводим перенос
        LessonModel to = lessonMaster.transferLesson(from);

        //Подготавливаем логгирование
        JLessonTransfer data = new JLessonTransfer();
        data.setReason(reason);
        data.setComment("");
        data.setLessonId(lessonId);
        data.setUserId(authMaster.getLoggedUserId());
        data.setFromDate(from.getDate());
        data.setToDate(to.getDate());

        //Логгируем перенос
        journalMapper.insertLessonTransfer(data);

        return new HTTPResponse(HTTPCode.SUCCESS);
    }
}
