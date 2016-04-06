package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.EventMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.model.LessonWeb;
import com.itsoft.ab.persistence.EventsMapper;
import com.itsoft.ab.persistence.ScheduleMapper;
import com.itsoft.ab.sys.ECode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 26.03.14
 * Time: 12:08
 */
@Controller
public class EventController {

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private EventMaster eventMaster;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @RequestMapping(value="/event/edit", method = RequestMethod.POST)
    public String editEvent(@ModelAttribute EventModel event, Model m){
        if (event != null){
            if (event.getId() != 0){
                eventsMapper.updateEvent(eventMaster.prepareEventFromWeb(event));
            }
            return "redirect:/teacher/" + event.getTeacherId() + "/events";
        }
        throw new ApplicationException(ECode.ERROR415);
    }

    @RequestMapping(value="/event/new", method = RequestMethod.POST)
    public String newEvent(@ModelAttribute EventModel event, Model m){
        if (event != null){

            eventsMapper.insertEvent(eventMaster.prepareEventFromWeb(event));

            return "redirect:/teacher/" + event.getTeacherId() + "/events";
        }
        throw new ApplicationException(ECode.ERROR415);
    }

    @RequestMapping(value="/tm/event/{eventId}/delete", method = RequestMethod.POST)
    public String deleteEvent(@PathVariable String eventId, Model m){
        int id = Integer.parseInt(eventId);
        EventModel event = eventMaster.getEvent(id);

        if (event != null){

            eventsMapper.deleteEvent(event);

            return "redirect:/teacher/" + event.getTeacherId() + "/events";
        }
        throw new ApplicationException(ECode.ERROR415);
    }

    // Ajax

    @RequestMapping(value = "/do/events/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<EventModel> getAllEvents(){
        return scheduleMapper.selectAllEvents();
    }

    @RequestMapping(value = "/do/events/{classId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LessonWeb> getClassEvents(@PathVariable int classId){
        return scheduleMapper.selectAllByClass(classId);
    }

    @RequestMapping(value = "/do/events/empty", method = RequestMethod.GET)
    public
    @ResponseBody
    List<EventModel> getEmptyEvents(@RequestParam(value = "teacherId") int teacherId,
                                    @RequestParam(value = "roomId") int roomId,
                                    @RequestParam(value = "start") String start,
                                    @RequestParam(value = "end") String end) {
        return scheduleMapper.getEmptyEvents(teacherId, roomId, start, end);
    }
}
