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

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private ContractMaster contractMaster;

    @Autowired
    private CallsStatusMapper callsStatusMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentMaster paymentMaster;

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

    @RequestMapping(value = "/do/lessons/restore", method = RequestMethod.POST)
    public @ResponseBody
    int restoreLesson(@RequestParam(value = "id") int id) {
        LessonModel lesson = lessonsMapper.getLesson(id);
        if(lesson != null) {
            lessonMaster.restoreLesson(lesson);
            return lesson.getStatusId();
        }

        return 0;
    }

    @RequestMapping(value = "/do/lesson/update", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateLesson(@RequestParam(value = "lessonId") int lessonId,
                      @RequestParam(value = "date") String date,
                      @RequestParam(value = "eventId") int eventId) {
        lessonsMapper.updateLesson(lessonId, date, eventId);
    }

    @RequestMapping(value = "/do/contract/option", method = RequestMethod.GET)
    public
    @ResponseBody
    ContractOptionModel getContractOption(@RequestParam(value = "id") int id) {
        return contractsMapper.getContractOptionById(id);
    }

    @RequestMapping(value = "/do/new-contract-status", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean isNewContractStatus(@RequestParam(value = "id") int id) {
        return callsStatusMapper.redirectToNewContract(id);
    }

    @RequestMapping(value = "/do/client", method = RequestMethod.GET)
    public
    @ResponseBody
    ClientModel getClient(@RequestParam(value = "id") int id) {
        ClientModel client = clientsMapper.getClientWithContractDataById(id);
        client.setBalance(clientsMapper.getDonePaymentsTotal(client.getId()) - client.getTotal());
        return client;
    }

    @RequestMapping(value = "/do/client", method = RequestMethod.POST)
    public
    @ResponseBody
    ClientModel updateClient(@RequestParam(value = "id") int id,
                             @RequestParam(value = "fname") String fname,
                             @RequestParam(value = "lname") String lname,
                             @RequestParam(value = "pname") String pname,
                             @RequestParam(value = "phone1") String phone1,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "bdate") String bdate) {
        ClientModel client = clientsMapper.getClientById(id);
        client.setFname(fname);
        client.setLname(lname);
        client.setPname(pname);
        client.setPhone1(phone1);
        client.setEmail(email);
        client.setBdate(bdate);
        clientsMapper.updateClient(client);
        return getClient(id);
    }

    @RequestMapping(value = "/do/client/contracts", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ContractModel> getClientContracts(@RequestParam(value = "clientId") int clientId) {
        List<ContractModel> contracts = contractsMapper.getClientContractsWithBalance(clientId);
        for(ContractModel contract : contracts) {
            contract.setAvailableLessons(contractsMapper.getCountPlannedLessons(contract.getId()));
            contract.setLessons(lessonsMapper.getContractLessons(contract.getId()));
            contract.setPayments(paymentMapper.getContractPayments(contract.getId()));
            contract.setContractOptionModel(
                    contractsMapper.getContractOptionById(contract.getContractOptionId()));
            contract.setTeacherEvents(eventMaster.getEmptyEvents(contract.getTeacherId()));
            contract.setSchedule(scheduleMapper.getContractSchedule(contract.getId()));
        }
        return contracts;
    }

    @RequestMapping(value = "/do/contract/lessons", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LessonModel> getContractLessons(@RequestParam(value = "contractId") int contractId) {
        return lessonsMapper.getContractLessons(contractId);
    }

    @RequestMapping(value = "/do/contract/payments", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PaymentModel> getContractPayments(@RequestParam(value = "contractId") int contractId) {
        return paymentMapper.getContractPayments(contractId);
    }

    @RequestMapping(value = "/do/contract/freeze", method = RequestMethod.POST)
    public
    @ResponseBody
    void freezeContract(@RequestParam(value = "contractId") int contractId,
                        @RequestParam(value = "lockFrom") String lockFromDate,
                        @RequestParam(value = "lockTo") String lockToDate) {
        ContractModel contract = contractsMapper.getContractById(contractId);
        contract.setFreezeDateS(lockFromDate);
        contract.setFreezeFinishDateS(lockToDate);
        contract.setFreezed(1);
        try {
            lessonMaster.freezeContract(contract);
            contractsMapper.freezeContract(contract);
        }
        catch (ParseException pe) {
            throw new ApplicationException(ECode.ERROR1102);
        }
    }

    @RequestMapping(value = "/do/contract/unfreeze", method = RequestMethod.POST)
    public
    @ResponseBody
    void unfreezeContract(@RequestParam(value = "contractId") int contractId) {
        ContractModel contract = contractsMapper.getContractById(contractId);
        contract.setFreezed(0);
        contractsMapper.freezeContract(contract);
    }

    @RequestMapping(value = "/do/contract/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteContract(@RequestParam(value = "contractId") int contractId) {
        //contractsMapper.deleteContract(contractId);
        contractsMapper.updateStatus(contractId, 3);
    }

    @RequestMapping(value = "/do/contract/restore", method = RequestMethod.POST)
    public
    @ResponseBody
    void restoreContract(@RequestParam(value = "contractId") int contractId) {
        //contractsMapper.deleteContract(contractId);
        if(contractsMapper.getCountPlannedLessons(contractId) == 0)
            contractsMapper.updateStatus(contractId, 2); // finished
        else
            contractsMapper.updateStatus(contractId, 1); // active
    }

    @RequestMapping(value = "/do/contract/schedule/insert", method = RequestMethod.POST)
    public
    @ResponseBody
    void insertContractScheduleEvent(@RequestParam("contractId") int contractId,
                                     @RequestParam("eventId") int eventId) {
        scheduleMapper.insertContractScheduleEvent(contractId, eventId);
        contractMaster.replanLessons(contractsMapper.getContractById(contractId));
    }

    @RequestMapping(value = "/do/contract/schedule/update", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateContractScheduleEvent(@RequestParam("contractId") int contractId,
                                     @RequestParam("contractScheduleId") int contractScheduleId,
                                     @RequestParam("eventId") int eventId) {
        scheduleMapper.updateContractScheduleEvent(contractScheduleId, eventId);
        contractMaster.replanLessons(contractsMapper.getContractById(contractId));
    }

    @RequestMapping(value = "/do/contract/schedule/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteContractScheduleEvent(@RequestParam("contractId") int contractId,
                                     @RequestParam("contractScheduleId") int contractScheduleId) {
        scheduleMapper.deleteContractScheduleEvent(contractScheduleId);
        contractMaster.replanLessons(contractsMapper.getContractById(contractId));
    }

    @RequestMapping(value = "/do/payment/commit", method = RequestMethod.POST)
    public
    @ResponseBody
    void commitPayment(@RequestParam(value = "paymentId") int paymentId) {
        paymentMaster.commitPayment(paymentId);
    }

    @RequestMapping(value = "/do/payment/restore", method = RequestMethod.POST)
    public
    @ResponseBody
    void restorePayment(@RequestParam(value = "paymentId") int paymentId) {
        paymentMaster.restorePayment(paymentId);
    }

    @RequestMapping(value = "/do/payment/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    void deletePayment(@RequestParam(value = "paymentId") int paymentId) {
        paymentMapper.deletePayment(paymentId);
    }

    @RequestMapping(value = "/do/payment/insert", method = RequestMethod.POST)
    public
    @ResponseBody
    void insertPayment(@RequestParam(value = "contractId") int contractId,
                       @RequestParam(value = "date") long date,
                       @RequestParam(value = "value") int value,
                       @RequestParam(value = "planned") int planned,
                       @RequestParam(value = "done") int done) {
        PaymentModel payment = new PaymentModel();
        payment.setContractId(contractId);
        payment.setDate(date);
        payment.setValue(value);
        payment.setPlanned(planned);
        payment.setDone(done);
        paymentMapper.insertPayment(payment);
    }

    @RequestMapping(value = "/do/payment/update", method = RequestMethod.POST)
    public
    @ResponseBody
    void updatePayment(@RequestParam(value = "paymentId") int paymentId,
                       @RequestParam(value = "date") long date,
                       @RequestParam(value = "value") int value) {
        PaymentModel payment = paymentMapper.selectPayment(paymentId);
        payment.setDate(date);
        payment.setValue(value);
        paymentMapper.updatePayment(payment);
    }
}
