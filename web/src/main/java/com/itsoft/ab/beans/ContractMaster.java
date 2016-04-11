package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.ECode;
import com.itsoft.ab.sys.MapMaster;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created withIntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.02.14
 * Time: 18:08
 */
@Service
public class ContractMaster {
    Log LOG = LogFactory.getLog(ContractMaster.class);

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private LessonMaster lessonMaster;

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private EventMaster eventMaster;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private PaymentMaster paymentMaster;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private JContractsMapper jContractsMapper;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public ContractMaster() {
    }

    public ContractModel prepareContract(ContractModel contract){

        //Преобразование временных констант
        try{
            contract.setDateS(new SimpleDateFormat("dd MMMM yyyy").format(contract.getDate()));
        } catch (NullPointerException e){
            //Nothing to do
        }

        try{
            contract.setFreezeDateS(new SimpleDateFormat("dd MMMM yyyy").format(contract.getFreezeDate()));
        } catch (NullPointerException e){
            //Nothing to do
        }

        try{
            contract.setFreezeFinishDateS(new SimpleDateFormat("dd MMMM yyyy").format(contract.getFreezeFinishDate()));
        } catch (NullPointerException e){
            //Nothing to do
        }

        contract.setMoneyNeed(contract.getPrice() - contract.getMoneyV());
        return contract;
    }

    public ContractModel saveFromWeb(ContractModel contract){

        try {
            contract.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(contract.getDateS()));
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }

        //Сохранение объекта контракта
        contract = insertContract(contract);

        if(contract.getPrev() > 0)
            insertPrev(contract.getId(), contract.getPrev());

        //Обновление баланса клиента и контракта
        PaymentModel p = new PaymentModel();
        p.setContractId(contract.getId());
        //!Цену контракта следует вычитать!
        p.setValue(-contract.getPrice());
        paymentMaster.updateVBalance(p);

        return contract;
    }

    public int createContract(ContractModel contract) {
        contract = insertContract(contract);
        updateSchedule(contract.getId(), contract.getDays().split(","));
        replanLessons(contract);

        for(PaymentModel payment: contract.getPayments()) {
            payment.setContractId(contract.getId());
            paymentMapper.insertPayment(payment);
        }

        return contract.getId();
    }

    private void insertPrev(int id, int prev) {
        jContractsMapper.insertPrev(id, prev);
    }

    public void planLessons(ContractModel contract) {
//        PROCESS
//        понять день недели начальной даты
//        понять дни недели эвентов
//        выстроить эвенты в логическом следовании после начальной даты
//        запустить цикл записи лессонов в эвенты в соответствии с этим порядком

        String[] dArray = contract.getDays().split(",");

        //Получаем все эвенты
        Map<Integer,Integer> daysMap = new LinkedHashMap<Integer,Integer>();
        for(String s : dArray){
            EventModel event = eventsMapper.getEventById(Integer.parseInt(s));
            //initial.add(event.getWeekday());
            daysMap.put(event.getId(),event.getWeekday());
        }

        //Сортируем по возрастанию
        daysMap = MapMaster.sortByValue(daysMap, MapMaster.ASC);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(contract.getDate());
        Integer startWeekDay = calendar.get(Calendar.DAY_OF_WEEK);

        //Переход к системе понедельние - 1, воскресенье - 7
        if(startWeekDay.equals(1)){
            startWeekDay = 7;
        }else{
            startWeekDay = startWeekDay - 1;
        }

        Map<Integer,Integer> before = new LinkedHashMap<Integer,Integer>();
        Map<Integer,Integer> after= new LinkedHashMap<Integer,Integer>();

        //
        for (Map.Entry<Integer,Integer> day : daysMap.entrySet()){
            if (day.getValue().compareTo(startWeekDay) >= 0){
                after.put(day.getKey(), day.getValue() - startWeekDay);
            }else{
                before.put(day.getKey(), 7 + day.getValue() - startWeekDay);
            }
        }


        //Получим необходимую последовательность эвентов - смещений в weekday
        after.putAll(before);


        int n = contract.getCountLessons();
        while(n > 0){

            for (Map.Entry<Integer,Integer> day : after.entrySet()){
                if(n > 0){
                    //Определить дату
                    //Записать лессон
                    calendar.add(Calendar.DATE, day.getValue());
                    //########
                    //Определили дату нового занятия

                        LessonModel lesson = new LessonModel();
                        lesson.setEventId(day.getKey());
                        //Запланирован
                        lesson.setStatusId(1);
                        lesson.setContractId(contract.getId());
                        lesson.setDate(calendar.getTime());

                        lessonsMapper.insertLesson(lesson);
                    //########
                    calendar.add(Calendar.DATE, - day.getValue());
                    n--;
                }
            }
            calendar.add(Calendar.DATE, 7);
        }
    }

    private Date getLastCompletedLessonDate(ContractModel contract) {
        List<LessonModel> lessons = lessonsMapper.getContractLessons(contract.getId());
        for(int i = lessons.size() - 1; i >= 0; i--)
            if(lessons.get(i).getStatusId() != 1)
                return lessons.get(i).getDate();

        return null;
    }

    private Calendar getDateToPlanFrom(ContractModel contract) {
        Calendar dateToPlanFrom = new GregorianCalendar();
        Date lastCompletedLessonDate = getLastCompletedLessonDate(contract);

        if(lastCompletedLessonDate == null)
            dateToPlanFrom.setTime(contract.getDate());
        else {
            dateToPlanFrom.setTime(lastCompletedLessonDate);
            dateToPlanFrom.add(Calendar.DATE, 1);
        }

        return dateToPlanFrom;
    }

    private int countLessonsToPlan(ContractModel contract) {
        int lessonCount = contract.getCountLessons();
        int completedLessonCount = contractsMapper.getCompletedLessonCount(contract.getId());
        return lessonCount - completedLessonCount;
    }

    // Calendar's week starts from sunday
    // (means that DAY_OF_WEEK for sunday is 1)
    // Need to adjust it.
    private int getWeekday(Calendar date) {
        // monday's number is 2
        return (date.get(Calendar.DAY_OF_WEEK) - 2 + 7) % 7 + 1;
    }

    private boolean dateWithinInterval(Calendar date, Calendar leftDate, Calendar rightDate, boolean inclusive) {
        return (date.after(leftDate) && date.before(rightDate)) ||
               (inclusive && (date.equals(leftDate) ||
                              date.equals(rightDate)));
    }

    private boolean isDateFrozen(ContractModel contract, Calendar date) {
        if(contract.getFreezed() == 1) {
            Calendar startFreezeDate = new GregorianCalendar();
            startFreezeDate.setTime(contract.getFreezeDate());
            Calendar finishFreezeDate = new GregorianCalendar();
            finishFreezeDate.setTime(contract.getFreezeFinishDate());
            return dateWithinInterval(date, startFreezeDate, finishFreezeDate, true);
        }
        return false;
    }

    private void createLessons(
        ContractModel contract,
        Calendar dateToPlanFrom,
        int lessonsToCreate,
        int statusId) {
            List<EventModel> schedule = scheduleMapper.getContractSchedule(contract.getId());
            if(!schedule.isEmpty()) {
                while (lessonsToCreate > 0) {
                    if(!isDateFrozen(contract, dateToPlanFrom)) {
                        List<EventModel> appropriateEvents =
                            eventMaster.filterEventsByWeekday(
                                schedule,
                                getWeekday(dateToPlanFrom)
                            );
                        for (EventModel event : appropriateEvents) {
                            if (eventsMapper.isEventFree(
                                    contract.getId(),
                                    dateToPlanFrom.getTime(),
                                    event.getId())) {
                                LessonModel lesson = lessonMaster.createLesson(
                                    contract.getId(),
                                    event.getId(),
                                    dateToPlanFrom.getTime(),
                                    statusId
                                );
                                lessonsMapper.insertLesson(lesson);
                                lessonsToCreate--;
                            }
                        }
                    }
                    dateToPlanFrom.add(Calendar.DATE, 1);
                }
            }
    }

    public void replanLessons(ContractModel contract) {
        contractsMapper.deletePlannedLessons(contract.getId());
        createLessons(
            contract,
            getDateToPlanFrom(contract),
            countLessonsToPlan(contract),
            1 // 'planned' statusId
        );
    }

    public void freezeContract(int contractId, Date freezeFrom, Date freezeTo) {
        ContractModel contract = contractsMapper.getContractById(contractId);
        contract.setFreezed(1);
        contract.setFreezeDate(freezeFrom);
        contract.setFreezeFinishDate(freezeTo);
        contractsMapper.freezeContract(contract);

        int lessonsToPlan = lessonsMapper.getLessonsWithinPeriod(
                contractId, freezeFrom, freezeTo, 1).size();

        lessonsMapper.deleteLessonsWithinPeriod(
                contractId, freezeFrom, freezeTo, 1);

        createLessons(
            contract,
            getDateToPlanFrom(contract),
            lessonsToPlan,
            1 // 'planned' statusId
        );
    }

    public ContractModel insertContract(ContractModel c) {
        contractsMapper.insertContract(c);
        return c;
    }

    public void updateTransferById(int contractId) {
        contractsMapper.updateTransferById(contractId);
    }

    public void doLesson(LessonModel lesson) {
        contractsMapper.doLesson(lesson);

        //Проверка количества оставшихся запланированных занятий
        //Если 0 - меням статус контракта на 2(finished)
        int count = getCountPlannedLessons(lesson.getContractId());
        if(0 == count){
            updateStatus(lesson.getContractId(), 2);
        }
    }

    public int getCountPlannedLessons(int contractId) {
        return contractsMapper.getPlannedLessonCount(contractId);
    }

    public void updateStatus(int contractId, int contractStatus) {
        contractsMapper.updateStatus(contractId, contractStatus);
    }

    public void updateCash(int contractId, int cash) {
        contractsMapper.updateCash(contractId, cash);
    }

    public void updateSchedule(int contractId, String[] eventIds) {
        scheduleMapper.updateContractSchedule(contractId, eventIds);
    }

    public int getContractBalance(int contractId) {
        return contractsMapper.getDonePaymentsTotal(contractId) -
               contractsMapper.getMoneySpentOnLessons(contractId) -
               contractsMapper.getWriteoffTotal(contractId) -
               contractsMapper.getCashbackTotal(contractId);
    }

    public void writeoff(int contractId) {
        contractsMapper.writeoff(contractId, getContractBalance(contractId));
        updateStatus(contractId, 2); // finished
    }

    public void cashback(int contractId) {
        int balance = getContractBalance(contractId);
        int fine = (int)(balance * 0.15);
        int cashback = balance - fine;
        contractsMapper.cashback(contractId, cashback, fine);
        updateStatus(contractId, 2); // finished
    }
}
