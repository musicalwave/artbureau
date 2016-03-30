package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.LessonModel;
import com.itsoft.ab.model.LessonWeb;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.EventsMapper;
import com.itsoft.ab.persistence.LessonsMapper;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.02.14
 * Time: 13:04
 */
@Service
public class LessonMaster {
    Logger LOG = Logger.getLogger(LessonMaster.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private ContractMaster contractMaster;

    //Проведение урока
    //clients.moneyR - price
    //contracts.money - price
    public int doLesson(LessonModel lesson){
        //Присвоение статуса "Проведен"
        lesson.setStatusId(2);
        lessonsMapper.updateStatus(lesson);

        contractMaster.doLesson(lesson);
        return 200;
    }

    //Проведение урока
    //clients.moneyR - price
    //contracts.money - price
    public int burnLesson(LessonModel lesson){
        //Присвоение статуса "Сгорел"
        lesson.setStatusId(3);
        lessonsMapper.updateStatus(lesson);

        //Обновление счетов
        contractMaster.doLesson(lesson);
        return 200;
    }

    public int restoreLesson(LessonModel lesson){
        //Присвоение статуса "Запланирован"
        lesson.setStatusId(1);
        lessonsMapper.updateStatus(lesson);
        // Контракт становится активным
        contractMaster.updateStatus(lesson.getContractId(), 1);
        return 200;
    }

    public LessonModel saveFromWeb(LessonModel lesson) {
        try {
            lesson.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(lesson.getDateS()));
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }

        return lesson;
    }

    public LessonModel transferLesson(LessonModel lesson){
        ContractModel contract = contractsMapper.selectContract(lesson.getContractId());

        //Получение всех занятий контракта (сортированных)
        List<LessonModel> lessons = lessonsMapper.getContractLessons(contract.getId());

        //Задаем начальную дату для планирования занятия после контракта
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lessons.get(lessons.size()-1).getDate());
        calendar.add(Calendar.DATE, 1);

        //Задаем начальные данные для планирования одного занятия после контракта
        contract.setDate(calendar.getTime());
        contract.setCountLessons(1);

        //Создание занятия в конце контракта
        contractMaster.planLessons(contract);

        //Удаление переносимого занятия
        lessonsMapper.deleteLesson(lesson);

        lessons = lessonsMapper.getContractLessons(lesson.getContractId());

        contractMaster.updateTransferById(lesson.getContractId());

        return lessons.get(lessons.size()-1);
    }

    public void freezeContract(ContractModel c) throws ParseException {
        Date startdate = new SimpleDateFormat("dd-MM-yyyy").parse(c.getFreezeDateS());
        Date finishdate = new SimpleDateFormat("dd-MM-yyyy").parse(c.getFreezeFinishDateS());

        List<LessonModel> lessons = lessonsMapper.getContractLessonsBetweenDates(c.getId(), startdate, finishdate);

        //Получение всех занятий контракта (сортированных)
        List<LessonModel> ls = lessonsMapper.getContractLessons(c.getId());

        //Задаем начальную дату для планирования занятия после контракта
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ls.get(ls.size()-1).getDate());
        calendar.add(Calendar.DATE, 1);
        Date lastlesson = calendar.getTime();

        //Удаление существующих уроков
        for(LessonModel lesson : lessons){
            lessonsMapper.deleteLesson(lesson);
        }

        c = contractsMapper.getContractById(c.getId());

        //Планирование
        if(finishdate.after(lastlesson)){
            c.setDate(finishdate);
        }else{
            c.setDate(lastlesson);
        }
        c.setCountLessons(lessons.size());

        //Создание занятий после finishdate
        contractMaster.planLessons(c);

    }

    public LessonModel prepareLesson(LessonModel lesson){
        //Преобразование временных констант
        try{
            lesson.setDateS(new SimpleDateFormat("dd MMMM yyyy").format(lesson.getDate()));
        } catch (NullPointerException e){
            throw new ApplicationException(ECode.ERROR415);
        }

        return lesson;
    }


    public List<LessonModel> prepareLessons(List<LessonModel> lessons){
        List<LessonModel> l = new ArrayList<LessonModel>();
        for(LessonModel lesson : lessons){
            l.add(prepareLesson(lesson));
        }
        return l;
    }

    public Object getDateLastLessons(Date fromDate, Date toDate) {
        List<LessonWeb> lessons = lessonsMapper.getLastLessons(fromDate, toDate);
        //return prepareLessons(lessons);
        return lessons;
    }

    public LessonModel createLesson(int contractId, int eventId, Date date, int statusId) {
        LessonModel lesson = new LessonModel();
        lesson.setContractId(contractId);
        lesson.setEventId(eventId);
        lesson.setDate(date);
        lesson.setStatusId(statusId);
        return lesson;
    }

}
