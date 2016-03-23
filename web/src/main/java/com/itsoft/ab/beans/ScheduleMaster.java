package com.itsoft.ab.beans;

import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.LessonsMapper;
import com.itsoft.ab.persistence.ScheduleMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 11.06.14
 * Time: 23:11
 */

@Service
public class ScheduleMaster {
    Logger LOG = Logger.getLogger(ScheduleMaster.class);

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    public LessonWeb getLesson(int lessonId) {
        LessonWeb lesson = scheduleMapper.getLesson(lessonId);
        return lesson;
    }

    public EventModel getAppropriateEvent(List<EventModel> events, int dayOfWeek) {
        for(EventModel event: events)
            if(event.getWeekday() == dayOfWeek)
                return event;

        return null;
    }

    public boolean shiftLesson(int lessonId, int eventId, String date) {
        LessonModel lesson = lessonsMapper.getLesson(lessonId);
        ContractModel contract = contractsMapper.getContractById(lesson.getContractId());
        ContractOptionModel contractOption =
                contractsMapper.getContractOptionById(contract.getContractOptionId());
        if(contract.getCountShifts() < contractOption.getMaxShifts()) {
            scheduleMapper.shiftLesson(eventId, lessonId, date);
            return true;
        } else {
            return false;
        }
    }
}
