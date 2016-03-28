package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.persistence.EventsMapper;
import com.itsoft.ab.persistence.ScheduleMapper;
import com.itsoft.ab.sys.ECode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 08.03.14
 * Time: 17:46
 */
@Service
public class EventMaster {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private EventsMapper eventsMapper;

    public EventModel prepareEventFromWeb(EventModel event){

        //Преобразование строковых представлений времени в java.sql.Time
        if (event.getStartTimeS() != null && !event.getStartTimeS().equals("")){
            event.setStartTime(Time.valueOf(event.getStartTimeS() + ":00"));
        }
        if (event.getFinishTimeS() != null && !event.getFinishTimeS().equals("")){
            event.setFinishTime(Time.valueOf(event.getFinishTimeS() + ":00"));
        }
        return event;
    }

    public EventModel prepareEventToWeb(EventModel event){

        //Преобразование java.sql.Time в строковое представление времени
        if(event.getStartTime() != null){
            String t = event.getStartTime().toString();
            event.setStartTimeS(t.substring(0, t.length() - 3));
        }
        if(event.getFinishTime() != null){
            String t = event.getFinishTime().toString();
            event.setFinishTimeS(t.substring(0, t.length() - 3));
        }
        return event;
    }

    public List<EventModel> getEmptyEvents(int teacherId) {
            return scheduleMapper.selectEmptyEventsByTeacher(teacherId);
    }

    public EventModel getEvent(int eventId) {
        EventModel event = eventsMapper.getEventById(eventId);
        return event;
    }

    public List<EventModel> filterEventsByWeekday(List<EventModel> events, int weekday) {
        List<EventModel> filteredEvents = new ArrayList<>();
        for(EventModel event: events)
            if(event.getWeekday() == weekday)
               filteredEvents.add(event);

        return filteredEvents;
    }
}
