package com.itsoft.ab.beans;

import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.model.LessonWeb;
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
}
