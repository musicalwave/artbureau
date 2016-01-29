package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.RoomMaster;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 13.02.14
 * Time: 14:50
 */
@Controller
public class SchedController {
    private Log LOG = LogFactory.getLog(SchedController.class);

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private RoomMaster roomMaster;

    @RequestMapping(value="/sched", method = RequestMethod.GET)
    public String showSchedule(Model m){
        m = authMaster.setModel(m);
        m = roomMaster.setRooms(m);
        return "/a/sched/new_schedule";
    }

    @RequestMapping(value="/sched/{classId}", method = RequestMethod.GET)
    public String showClassSchedule(@PathVariable String classId, Model m){
        m = authMaster.setModel(m);
        m = roomMaster.setRooms(m);
        return "/a/sched/schedule";
    }

    @RequestMapping(value="/sched/today", method = RequestMethod.GET)
    public String showTodaySchedule(Model m){
        m = authMaster.setModel(m);
        m = roomMaster.setRooms(m);
        return "/a/sched/schedule";
    }

    @RequestMapping(value="/sched/teacher", method = RequestMethod.GET)
    public String showTeacherSchedule(Model m){
        m = authMaster.setModel(m);
        m = roomMaster.setRooms(m);
        return "/a/sched/schedule";
    }
}
