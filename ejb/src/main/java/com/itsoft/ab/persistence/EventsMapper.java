package com.itsoft.ab.persistence;

import com.itsoft.ab.model.EventModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 01.03.14
 * Time: 22:23
 */
public interface EventsMapper {
    EventModel getEventById(int eventId);

    List<EventModel> getTeacherEvents(int teacherId);

    void insertEvent(EventModel event);

    void updateEvent(EventModel event);

    void deleteEvent(EventModel event);
}
