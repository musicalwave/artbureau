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

    public List<EventModel> getEmptyEventsByDate(int teacherId, String dateS) {
        try {
            List<EventModel> events = new ArrayList<EventModel>();
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateS);
            Calendar start = Calendar.getInstance();
            start.setTime(date);
            Calendar finish = Calendar.getInstance();
            finish.setTime(date);
            finish.add(Calendar.DATE, 7);
            for (Date d = start.getTime(); start.before(finish); start.add(Calendar.DATE, 1), d = start.getTime()) {
                //events.addAll(scheduleMapper.selectEmptyEventsByDateAndTeacher(new java.sql.Date(d.getTime()),teacherId));
            }
            events.addAll(scheduleMapper.selectEmptyEventsByDateAndTeacher(new java.sql.Date(start.getTimeInMillis()),teacherId));
            return events;
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }
    }

    public EventModel getEvent(int eventId) {
        EventModel event = eventsMapper.getEventById(eventId);
        return event;
    }
}
