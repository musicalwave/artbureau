package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.ContractScheduleModel;
import com.itsoft.ab.model.LessonModel;
import com.itsoft.ab.model.LessonWeb;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.EventsMapper;
import com.itsoft.ab.persistence.LessonsMapper;
import com.itsoft.ab.sys.Dates;
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

    @Autowired
    private ContractScheduleMaster contractScheduleMaster;

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
        lessonsMapper.deleteLesson(lesson.getId());

        lessons = lessonsMapper.getContractLessons(lesson.getContractId());

        contractMaster.updateTransferById(lesson.getContractId());

        return lessons.get(lessons.size()-1);
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

    public LessonModel createLesson(
        int contractId,
        Date date,
        String fromTime,
        String toTime,
        int roomId,
        int statusId
    ) {
        LessonModel lesson = new LessonModel();
        lesson.setContractId(contractId);
        lesson.setDate(date);
        lesson.setFromTime(fromTime);
        lesson.setToTime(toTime);
        lesson.setRoomId(roomId);
        lesson.setStatusId(statusId);
        return lesson;
    }

    public void shiftLesson(LessonModel lesson) {
          ContractModel contract =  lessonsMapper.getLessonContract(lesson.getId());
          lesson.setContractId(contract.getId());
          lesson.setStatusId(1);
          if(lesson.getTemporary() == 1) {
             lessonsMapper.updateLesson(lesson);
          } else
          if(lesson.getTempShift()) {
              lesson.setTemporary(1);
              int oldLessonId = lesson.getId();
              lessonsMapper.insertLesson(lesson);
              lessonsMapper.updateCancelled(oldLessonId, 1);
              lessonsMapper.updateShiftedTo(oldLessonId, lesson.getId());
          } else {
              ContractScheduleModel scheduleItem =
                  contractScheduleMaster.findItemByLesson(lesson.getId());
              if(scheduleItem != null) {
                  scheduleItem.setRoomId(lesson.getRoomId());
                  scheduleItem.setFromTime(lesson.getFromTime());
                  scheduleItem.setToTime(lesson.getToTime());
                  Calendar calendar = new GregorianCalendar();
                  calendar.setTime(lesson.getDate());
                  scheduleItem.setWeekday(Dates.getCalendarWeekday(calendar));
                  contractScheduleMaster.updateContractScheduleItem(scheduleItem);
                  contractMaster.replanLessons(contract);
              }
          }
    }

    public void unshiftLesson(int lessonId) {
        LessonModel lesson = lessonsMapper.getLesson(lessonId);
        if(lesson.getTemporary() == 1) {
            LessonModel originalLesson =
                lessonsMapper.getOriginalLesson(lesson.getId());
            lessonsMapper.updateCancelled(originalLesson.getId(), 0);
            lessonsMapper.updateShiftedTo(originalLesson.getId(), null);
            lessonsMapper.deleteLesson(lesson.getId());
        }
    }

}
