package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.EventMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.EventModel;
import com.itsoft.ab.persistence.EventsMapper;
import com.itsoft.ab.sys.ECode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
